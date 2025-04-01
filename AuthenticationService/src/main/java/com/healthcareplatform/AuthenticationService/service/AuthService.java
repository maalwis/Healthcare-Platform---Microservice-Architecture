package com.healthcareplatform.AuthenticationService.service;

public interface AuthService {

    public String login();

    public void logout(String token);

    public String refreshToken();
}
