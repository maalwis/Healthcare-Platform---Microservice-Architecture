package com.healthcareplatform.InventoryService.authenticationClient;

import com.healthcareplatform.InventoryService.dto.UserDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService{

    @Autowired
    private AuthenticationClient authenticationClient ;

    @CircuitBreaker(name = "authBreaker", fallbackMethod = "callAuthFallback")
    public ResponseEntity<UserDTO> callAuth(String jwt) {
        return authenticationClient.getUserDto("Bearer " + jwt);
    }

    private ResponseEntity<UserDTO> callAuthFallback(String jwt, Throwable t) {
        // Return a default response
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(null);
    }
}
