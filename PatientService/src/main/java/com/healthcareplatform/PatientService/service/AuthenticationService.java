package com.healthcareplatform.PatientService.service;

import com.healthcareplatform.PatientService.dto.UserDTO;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    public ResponseEntity<UserDTO> callAuth(String jwt);
}
