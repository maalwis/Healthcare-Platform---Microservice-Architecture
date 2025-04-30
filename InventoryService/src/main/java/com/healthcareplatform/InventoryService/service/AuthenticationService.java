package com.healthcareplatform.InventoryService.service;

import com.healthcareplatform.InventoryService.dto.UserDTO;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    public ResponseEntity<UserDTO> callAuth(String jwt);
}
