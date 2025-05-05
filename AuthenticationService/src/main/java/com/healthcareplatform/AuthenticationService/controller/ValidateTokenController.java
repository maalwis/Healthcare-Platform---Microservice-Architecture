package com.healthcareplatform.AuthenticationService.controller;


import com.healthcareplatform.AuthenticationService.dto.UserDTO;
import com.healthcareplatform.AuthenticationService.service.ValidateTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/private")
public class ValidateTokenController {

    @Autowired
    ValidateTokenService validateTokenService;

    public ValidateTokenController(ValidateTokenService validateTokenService) {
        this.validateTokenService = validateTokenService;
    }

    @GetMapping("/validateToken")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        try {
            UserDTO user = validateTokenService.validateDownStreamToken(authHeader);
            return ResponseEntity.ok(user);
        } catch (RuntimeException ex) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", ex.getMessage());
            errorResponse.put("status", false);
            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        }
    }

}