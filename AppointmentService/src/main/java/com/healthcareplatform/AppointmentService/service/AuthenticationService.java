package com.healthcareplatform.AppointmentService.service;

import com.healthcareplatform.AppointmentService.dto.UserDTO;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    public ResponseEntity<UserDTO> callAuth(String jwt);
}
