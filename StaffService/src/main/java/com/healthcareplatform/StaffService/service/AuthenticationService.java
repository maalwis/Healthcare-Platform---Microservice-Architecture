package com.healthcareplatform.StaffService.service;

import com.healthcareplatform.StaffService.dto.UserDTO;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    public ResponseEntity<UserDTO> callAuth(String jwt);
}
