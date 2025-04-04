package com.healthcareplatform.AuthenticationService.service;

import com.healthcareplatform.AuthenticationService.dto.LoginRequest;

public interface AuthService {

    public String login(LoginRequest loginRequest);

    public void logout(String token);

    public String refreshToken();
}
