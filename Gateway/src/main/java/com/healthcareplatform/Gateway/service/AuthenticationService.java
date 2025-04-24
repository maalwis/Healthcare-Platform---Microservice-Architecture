package com.healthcareplatform.Gateway.service;

import com.healthcareplatform.Gateway.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface AuthenticationService {
    /**
     * Validates the given JWT by calling the Authentication Service.
     * @param jwt the raw token (without "Bearer ")
     * @return a Mono emitting the UserDTO on success, or an error on failure
     */
    Mono<ResponseEntity<UserDTO>> validateToken(String jwt);
}
