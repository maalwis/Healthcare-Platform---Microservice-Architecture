package com.healthcareplatform.Gateway.service;

import com.healthcareplatform.Gateway.dto.UserDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AuthenticationService{

    private final WebClient webClient;

    public AuthenticationService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("lb://AuthenticationService").build();
    }


    @CircuitBreaker(name = "authBreaker", fallbackMethod = "callAuthFallback")
    public Mono<ResponseEntity<UserDTO>> validateToken(String jwt) {
        String authServiceUrl = "/api/v1/private/validateToken";
        return webClient.get()
                .uri(authServiceUrl)
                .header("Authorization", "Bearer " + jwt)
                .retrieve()
                .toEntity(UserDTO.class);
    }

    public Mono<ResponseEntity<UserDTO>> callAuthFallback(String jwt, Throwable t) {
        return Mono.just(
                ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(null)
        );
    }
}
