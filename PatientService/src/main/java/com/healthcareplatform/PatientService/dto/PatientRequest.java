package com.healthcareplatform.PatientService.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientRequest {
    private String firstName;
    private String lastName;
    private LocalDateTime dateOfBirth;
    private String gender;
    private String contactInfo;
}
