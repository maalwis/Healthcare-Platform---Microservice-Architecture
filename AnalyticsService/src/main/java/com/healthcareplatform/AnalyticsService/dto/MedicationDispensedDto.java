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
public class MedicationDispensedDto {
    private Long dispenseId;
    private Long prescriptionId;
    private Long patientId;
    private String medicationName;
    private Integer quantityDispensed;
    private LocalDateTime createdAt;

}
