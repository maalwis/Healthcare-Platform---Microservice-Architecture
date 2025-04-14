package com.healthcareplatform.Gateway.filters;


import com.healthcareplatform.Gateway.dto.UserDTO;
import com.healthcareplatform.Gateway.security.jwt.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class AuthenticationGlobalFilter implements GlobalFilter, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationGlobalFilter.class);

    // Create a WebClient to call the Authentication Service.
    private final WebClient webClient;
    private final JwtUtils jwtUtils;

    public AuthenticationGlobalFilter(WebClient.Builder webClientBuilder, JwtUtils jwtUtils1) {
        // Base URL of your Authentication Service (e.g. http://localhost:8080)
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
        this.jwtUtils = jwtUtils1;
    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String jwt = jwtUtils.parseJwt(request);
        String path = request.getURI().getPath();

        // Only allow missing JWT for public endpoints.
        if (jwt == null) {
            if (path.startsWith("/login")) {
                // Public endpoint can be accessed without JWT.
                return chain.filter(exchange);
            } else {
                // For non-public endpoints, JWT is required.
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        }

        try {
            // URL to call in the Authentication Service.
            // Same as: "http://localhost:8080/api/v1/private/validateToken"
            String authServiceUrl = "/api/v1/private/validateToken";

            return webClient.get()
                    .uri(authServiceUrl)
                    .header("Authorization", "Bearer " + jwt)
                    .retrieve()
                    .toEntity(UserDTO.class)
                    .flatMap(responseEntity -> {
                        if (responseEntity.getStatusCode() == HttpStatus.OK) {
                            UserDTO userDTO = responseEntity.getBody();
                            assert userDTO != null;

                            // Extract authorities from the returned UserDTO.
                            List<GrantedAuthority> authorities = userDTO.getAuthorityNames().stream()
                                    .map(SimpleGrantedAuthority::new)
                                    .collect(Collectors.toList());

                            // Check if the user's authorities allow access to the request's endpoint.
                            boolean permitted = checkUserPermissions(authorities, path);
                            if (!permitted) {
                                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                                return exchange.getResponse().setComplete();
                            }

                            // If permitted, let the request proceed.
                            return chain.filter(exchange);
                        } else {
                            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                            return exchange.getResponse().setComplete();
                        }
                    })
                    .onErrorResume(e -> {
                        // Log and send UNAUTHORIZED on errors.
                        logger.error("Authentication error: ", e);
                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        return exchange.getResponse().setComplete();
                    });
        } catch (Exception e) {
            logger.error("Authentication error: ", e);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }


    private boolean checkUserPermissions(List<GrantedAuthority> authorities, String path) {
        // Public endpoints accessible without specific permissions
        if (path.equals("/login") || path.equals("/csrf-token")) {
            return true;
        }

        // AuthenticationService endpoints
        if (path.startsWith("/permissions")) {
            System.out.println(authorities.stream()
                    .anyMatch(a -> a.getAuthority().equals("MANAGE_PERMISSIONS")));
            return authorities.stream()
                    .anyMatch(a -> a.getAuthority().equals("MANAGE_PERMISSIONS"));
        }

        if (path.startsWith("/roles")) {
            List<String> roleEndpointAuthorities = Arrays.asList("MANAGE_PERMISSIONS", "ASSIGN_ROLES");

            return authorities.stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(roleEndpointAuthorities::contains);
        }

        // AppointmentSchedulingService endpoints
        if (path.startsWith("/appointments")) {
            List<String> appointmentsEndpointAuthorities = Arrays.asList("VIEW_SCHEDULING", "MANAGE_APPOINTMENTS");

            return authorities.stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(appointmentsEndpointAuthorities::contains);
        }

        // PrescriptionService endpoints
        if (path.startsWith("/prescriptions")) {
            List<String> prescriptionEndpointAuthorities = Arrays.asList("VIEW_PATIENT_RECORDS", "PROCESS_MEDICAL_ORDERS");

            return authorities.stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(prescriptionEndpointAuthorities::contains);
        }

        // PatientManagementService endpoints
        if (path.startsWith("/patients")) {
            List<String> patientEndpointAuthorities = Arrays.asList("VIEW_PATIENT_RECORDS", "CREATE_PATIENT_RECORDS",
                    "EDIT_PATIENT_RECORDS", "DELETE_PATIENT_RECORDS");

            return authorities.stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(patientEndpointAuthorities::contains);
        }

        // Deny access by default for unrecognized paths
        return false;
    }


    @Override
    public int getOrder() {
        // A lower order value means this filter is applied earlier.
        return -1;
    }
}
