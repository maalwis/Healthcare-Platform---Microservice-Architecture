package com.healthcareplatform.AuthenticationService.serviceImpl;


import com.healthcareplatform.AuthenticationService.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {


    @Override
    public String login() {
        return "";
    }

    @Override
    public void logout(String token) {

    }

    @Override
    public String refreshToken() {
        return "";
    }
}
