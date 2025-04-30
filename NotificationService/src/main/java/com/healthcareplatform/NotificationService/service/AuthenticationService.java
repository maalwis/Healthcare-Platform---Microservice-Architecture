package com.healthcareplatform.NotificationService.service;

import com.healthcareplatform.NotificationService.dto.UserDTO;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    public ResponseEntity<UserDTO> callAuth(String jwt);
}
