package com.healthcareplatform.AuditLoggingService.service;


import com.healthcareplatform.AuditLoggingService.dto.UserDTO;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    public ResponseEntity<UserDTO> callAuth(String jwt);
}
