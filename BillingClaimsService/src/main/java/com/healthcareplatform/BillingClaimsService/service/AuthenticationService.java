package com.healthcareplatform.BillingClaimsService.service;

import com.healthcareplatform.BillingClaimsService.dto.UserDTO;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    public ResponseEntity<UserDTO> callAuth(String jwt);
}
