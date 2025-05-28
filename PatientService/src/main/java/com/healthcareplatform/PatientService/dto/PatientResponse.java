package com.healthcareplatform.PatientService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDateTime dateOfBirth;
    private String gender;
    private String contactInfo;
    private String metadata;
    private LocalDateTime createdAt;

}
