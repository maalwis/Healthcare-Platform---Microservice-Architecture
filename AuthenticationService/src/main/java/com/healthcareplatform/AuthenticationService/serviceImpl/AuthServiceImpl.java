package com.healthcareplatform.AuthenticationService.serviceImpl;

import com.healthcareplatform.AuthenticationService.dto.LoginRequest;
import com.healthcareplatform.AuthenticationService.dto.LoginResponse;
import com.healthcareplatform.AuthenticationService.security.jwt.JwtUtils;
import com.healthcareplatform.AuthenticationService.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    // Constructor injection of dependencies
    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        try {
            // Attempt authentication with provided credentials
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            // Store authentication in SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Retrieve user details from the authentication object
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Generate the JWT token using the username (or user details as needed)
            String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);

            // Extract user authorities from the generated token
            List<GrantedAuthority> authorities = jwtUtils.getAuthoritiesFromJwtToken(jwtToken);

            // Build and return the response including username, authorities, and JWT token
            return new LoginResponse(userDetails.getUsername(), authorities, jwtToken);
        } catch (AuthenticationException ex) {
            // It is a good practice to throw custom exceptions rather than returning hardcoded maps from service code.
            // You could create a custom exception (e.g., InvalidCredentialsException) if needed.
            throw new RuntimeException("Bad credentials");
        }
    }

    @Override
    public void logout(String token) {
        // Implement logout logic if applicable (e.g., token invalidation)
    }

    @Override
    public String refreshToken() {
        // Implement JWT token refresh logic
        return "";
    }
}
