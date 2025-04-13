package com.healthcareplatform.AuthenticationService.service;

import com.healthcareplatform.AuthenticationService.dto.UserDTO;

public interface ValidateTokenService {
    public UserDTO validateDownStreamToken(String authHeader);
}
