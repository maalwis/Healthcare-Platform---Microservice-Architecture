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
public class PrescriptionDto {
    private UUID id;
    private UUID patientId;
    private UUID doctorId;
    private OffsetDateTime dateIssued;
    private String status;
    private String notes;
    private LocalDateTime createdAt;

}
