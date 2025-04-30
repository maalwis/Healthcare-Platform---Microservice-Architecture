package com.healthcareplatform.InventoryService.serviceImpl;

import com.healthcareplatform.InventoryService.authenticationClient.AuthenticationClient;
import com.healthcareplatform.InventoryService.dto.UserDTO;
import com.healthcareplatform.InventoryService.service.AuthenticationService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private AuthenticationClient authenticationClient ;

    @Override
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
