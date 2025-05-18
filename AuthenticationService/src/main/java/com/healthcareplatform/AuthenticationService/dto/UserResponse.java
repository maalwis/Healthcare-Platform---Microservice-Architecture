package com.healthcareplatform.AuthenticationService.dto;


import com.healthcareplatform.AuthenticationService.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long userId;

    private String employeeId;

    private String fullName;

    private String username;

    private String email;

    private LocalDateTime hireDate;

    private LocalDate credentialsExpiryDate;

    private LocalDate accountExpiryDate;

    private Set<UserRole> userRoles;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

}
