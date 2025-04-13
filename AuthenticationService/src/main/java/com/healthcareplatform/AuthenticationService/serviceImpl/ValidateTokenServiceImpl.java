package com.healthcareplatform.AuthenticationService.serviceImpl;

import com.healthcareplatform.AuthenticationService.dto.UserDTO;
import com.healthcareplatform.AuthenticationService.security.jwt.JwtUtils;
import com.healthcareplatform.AuthenticationService.service.ValidateTokenService;
import com.healthcareplatform.AuthenticationService.userdetails.UserDetailsServiceImpl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ValidateTokenServiceImpl implements ValidateTokenService {
    private final JwtUtils jwtUtils;
    private final UserDetailsServiceImpl userDetailsService;

    public ValidateTokenServiceImpl(JwtUtils jwtUtils, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public UserDTO validateDownStreamToken(String authHeader) {
        String token = authHeader.substring(7); // Remove "Bearer "
        if (jwtUtils.validateJwtToken(token)) {
            String username = jwtUtils.getUserNameFromJwtToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(userDetails.getUsername());
            userDTO.setAuthorityNames(
                    userDetails.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.toList())
            );
            userDTO.setEnabled(userDetails.isEnabled());
            userDTO.setAccountNonExpired(userDetails.isAccountNonExpired());
            userDTO.setCredentialsNonExpired(userDetails.isCredentialsNonExpired());
            userDTO.setAccountNonLocked(userDetails.isAccountNonLocked());

            return userDTO;
        } else {
            // You can throw a custom exception here to indicate token invalidity
            throw new RuntimeException("Invalid token");
        }
    }
}
