package com.healthcareplatform.AuthenticationService.controller;

import com.healthcareplatform.AuthenticationService.dto.LoginRequest;
import com.healthcareplatform.AuthenticationService.dto.LoginResponse;
import com.healthcareplatform.AuthenticationService.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private final AuthService authService;

    // Constructor injection for AuthService
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/public/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Delegate authentication logic to the AuthService
            LoginResponse response = authService.login(loginRequest);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", ex.getMessage());
            errorResponse.put("status", false);
            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        try {
            // Implement logout behavior using authService if required
            return ResponseEntity.ok("Logout successful");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Logout failed");
        }
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<?> refreshToken() {
        try {
            // Delegate token refresh logic to the authService
            return ResponseEntity.ok("Refreshed JWT token");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Token refresh failed");
        }
    }

}