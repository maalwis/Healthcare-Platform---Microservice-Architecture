package com.healthcareplatform.AnalyticsService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicationDispensedDto {
    private UUID dispenseId;
    private UUID prescriptionId;
    private UUID patientId;
    private String medicationName;
    private Integer quantityDispensed;
    private LocalDateTime createdAt;

}
