package com.healthcareplatform.AppointmentSchedulingService.service;

import com.healthcareplatform.AppointmentSchedulingService.dto.UserDTO;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    public ResponseEntity<UserDTO> callAuth(String jwt);
}
