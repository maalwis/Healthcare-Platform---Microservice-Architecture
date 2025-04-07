package com.healthcareplatform.AuthenticationService.dto;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

// LoginResponse.java
public class LoginResponse {
    private String jwtToken;

    private String username;
    private List<GrantedAuthority> permissions;

    public LoginResponse(String username, List<GrantedAuthority> permissions, String jwtToken) {
        this.username = username;
        this.permissions = permissions;
        this.jwtToken = jwtToken;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<GrantedAuthority> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<GrantedAuthority> permissions) {
        this.permissions = permissions;
    }
}