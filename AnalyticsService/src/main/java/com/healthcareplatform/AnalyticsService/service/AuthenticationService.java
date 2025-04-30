package com.healthcareplatform.AnalyticsService.service;

import com.healthcareplatform.AnalyticsService.dto.UserDTO;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    public ResponseEntity<UserDTO> callAuth(String jwt);
}
