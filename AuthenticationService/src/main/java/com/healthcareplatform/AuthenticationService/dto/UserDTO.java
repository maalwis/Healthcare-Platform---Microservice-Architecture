package com.healthcareplatform.AuthenticationService.dto;

import java.util.List;

public class UserDTO {
    private String username;
    private List<String> authorityNames;
    private boolean enabled;
    private boolean accountNonExpired;
    private boolean credentialsNonExpired;
    private boolean accountNonLocked;

    // Getters and setters
    public String getUsername() { return username; }
    public List<String> getAuthorityNames() { return authorityNames; }
    public boolean isEnabled() { return enabled; }
    public boolean isAccountNonExpired() { return accountNonExpired; }
    public boolean isCredentialsNonExpired() { return credentialsNonExpired; }
    public boolean isAccountNonLocked() { return accountNonLocked; }
    // Setters omitted for brevity


    public void setUsername(String username) {
        this.username = username;
    }

    public void setAuthorityNames(List<String> authorityNames) {
        this.authorityNames = authorityNames;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }
}