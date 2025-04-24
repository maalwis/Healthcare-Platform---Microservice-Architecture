package com.healthcareplatform.PatientManagementService.service;

import com.healthcareplatform.PatientManagementService.dto.UserDTO;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    public ResponseEntity<UserDTO> callAuth(String jwt);
}
