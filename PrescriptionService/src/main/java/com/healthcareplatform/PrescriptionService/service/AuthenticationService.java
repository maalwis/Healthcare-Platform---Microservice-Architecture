package com.healthcareplatform.PrescriptionService.service;


import com.healthcareplatform.PrescriptionService.dto.UserDTO;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    public ResponseEntity<UserDTO> callAuth(String jwt);
}
