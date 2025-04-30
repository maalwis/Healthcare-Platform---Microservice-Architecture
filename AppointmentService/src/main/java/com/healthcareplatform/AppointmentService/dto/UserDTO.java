package com.healthcareplatform.AppointmentService.dto;

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

}