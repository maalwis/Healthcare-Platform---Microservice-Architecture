package com.healthcareplatform.AuthenticationService.controller;
import com.healthcareplatform.AuthenticationService.dto.LoginRequest;
import com.healthcareplatform.AuthenticationService.dto.LoginResponse;
import com.healthcareplatform.AuthenticationService.security.jwt.JwtUtils;
import com.healthcareplatform.AuthenticationService.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/auth") // API versioning via URL prefix
public class AuthController {

    JwtUtils jwtUtils;
    AuthenticationManager authenticationManager;

    public AuthController(JwtUtils jwtUtils, AuthenticationManager authenticationManager) {
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (AuthenticationException exception) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
        }

//      set the authentication
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);

        List<GrantedAuthority> permissions = jwtUtils.getAuthoritiesFromJwtToken(jwtToken);

        // Prepare the response body, now including the JWT token directly in the body
        LoginResponse response = new LoginResponse(userDetails.getUsername(), permissions, jwtToken);

        // Return the response entity with the JWT token included in the response body
        return ResponseEntity.ok(response);
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





