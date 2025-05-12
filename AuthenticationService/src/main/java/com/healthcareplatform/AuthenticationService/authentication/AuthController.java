package com.healthcareplatform.AuthenticationService.authentication;

import com.healthcareplatform.AuthenticationService.dto.LoginRequest;
import com.healthcareplatform.AuthenticationService.dto.LoginResponse;
import jakarta.validation.Valid;
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

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Login endpoint for healthcare platform employee.
     *
     * @return ResponseEntity containing LoginResponse object and HTTP 200 status.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        // Delegate authentication logic to the AuthService
        LoginResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // TODO: Implement logout behavior using authService if required
        return ResponseEntity.ok("Logout successful");

    }

    @PostMapping("/token/refresh")
    public ResponseEntity<?> refreshToken() {
        // TODO:  Implement token refresh logic to the authService
        return ResponseEntity.ok("Refreshed JWT token");

    }

}