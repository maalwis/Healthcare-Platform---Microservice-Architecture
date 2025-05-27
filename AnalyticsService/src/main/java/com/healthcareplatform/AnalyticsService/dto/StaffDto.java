package com.healthcareplatform.AnalyticsService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StaffDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String role;
    private String department;
    private String specialties;
    private String contactInfo;
    private LocalDateTime createdAt;
}
