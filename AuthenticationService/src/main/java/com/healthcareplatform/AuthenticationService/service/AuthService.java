package com.healthcareplatform.AuthenticationService.service;

import com.healthcareplatform.AuthenticationService.dto.LoginRequest;
import com.healthcareplatform.AuthenticationService.dto.LoginResponse;

public interface AuthService {

    public LoginResponse login(LoginRequest loginRequest);

    public void logout(String token);

    public String refreshToken();
}
