package com.healthcareplatform.Gateway.filters;


import com.healthcareplatform.Gateway.dto.UserDTO;
import com.healthcareplatform.Gateway.security.jwt.JwtUtils;
import com.healthcareplatform.Gateway.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class AuthenticationGlobalFilter implements GlobalFilter, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationGlobalFilter.class);

    @Autowired
    private final JwtUtils jwtUtils;

    @Autowired
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationGlobalFilter(JwtUtils jwtUtils, AuthenticationService authenticationService) {
        this.jwtUtils = jwtUtils;
        this.authenticationService = authenticationService;
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

            return authenticationService.validateToken(jwt)
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
        if (path.equals("/login")) {
            return true;
        }

        // Deny access by default for unrecognized paths
        return false;
    }


    @Override
    public int getOrder() {
        // A lower order value means this filter is applied earlier.
        return -100; // Lower than RateLimitGlobalFilter's current value
    }
}
