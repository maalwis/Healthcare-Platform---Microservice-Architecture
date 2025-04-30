package com.healthcareplatform.PharmacyService.service;

import com.healthcareplatform.PharmacyService.dto.UserDTO;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    public ResponseEntity<UserDTO> callAuth(String jwt);
}
