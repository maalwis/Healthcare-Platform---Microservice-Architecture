package com.healthcareplatform.AuthenticationService.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth") // API versioning via URL prefix
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<?> login() {
        try {
            // Delegating to the AuthService to authenticate the user.
            return ResponseEntity.ok("Login successful.");
        } catch (Exception e) {
            // Return 401 Unauthorized if authentication fails.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid credentials");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        try {
            // Delegating to the AuthService to perform logout.
            return ResponseEntity.ok("Logout successful");
        } catch (Exception e) {
            // Return 500 Internal Server Error if logout fails.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Logout failed");
        }
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<?> refreshToken() {
        try {
            // Delegating to the AuthService to refresh the JWT token.
            return ResponseEntity.ok("Refreshed JWT token");
        } catch (Exception e) {
            // Return 401 Unauthorized if token refresh fails.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Token refresh failed");
        }
    }


    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword() {
        try {
            // Delegating to the AuthService to change the password of an authenticated user.
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body("Successfully changed the password");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Failed");
        }
    }
}





