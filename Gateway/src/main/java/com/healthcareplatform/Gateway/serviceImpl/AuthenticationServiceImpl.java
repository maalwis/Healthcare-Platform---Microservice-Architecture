package com.healthcareplatform.Gateway.serviceImpl;

import com.healthcareplatform.Gateway.dto.UserDTO;
import com.healthcareplatform.Gateway.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final WebClient webClient;
    String authServiceUrl = "/api/v1/private/validateToken";

    public AuthenticationServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("lb://AuthenticationService")
                .build();
    }


    @Override
    public Mono<ResponseEntity<UserDTO>> validateToken(String jwt) {
        return webClient.get()
                .uri(authServiceUrl)
                .header("Authorization", "Bearer " + jwt)
                .retrieve()
                .toEntity(UserDTO.class);
    }
}
