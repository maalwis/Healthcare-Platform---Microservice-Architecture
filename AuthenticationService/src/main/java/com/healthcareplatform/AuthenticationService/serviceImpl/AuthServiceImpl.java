package com.healthcareplatform.AuthenticationService.serviceImpl;


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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public String login(LoginRequest loginRequest) {
        return "";
    }

    @Override
    public void logout(String token) {

    }

    @Override
    public String refreshToken() {
        return "";
    }
}
