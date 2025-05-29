package com.healthcareplatform.PharmacyService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * DTO for returning Prescription details, including items.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionResponse {

    private Long id;
    private Long patientId;
    private String patientFirstName;
    private String patientSecondName;
    private LocalDateTime patientDateOfBirth;
    private String patientGender;
    private String patientContactInfo;

    private Long doctorId;
    private String doctorFirstName;
    private String doctorLastName;
    private String doctorRole;

    private LocalDateTime dateIssued;
    private String status;
    private String notes;

    private List<PrescriptionItemResponse> items;

    private OffsetDateTime createdAt;
}