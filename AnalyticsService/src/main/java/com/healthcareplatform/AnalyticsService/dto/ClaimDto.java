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
public class ClaimDto {
    private UUID id;
    private UUID invoiceId;
    private String insurer;
    private String claimStatus;
    private String details;
    private LocalDateTime submittedAt;
    private LocalDateTime createdAt;

}
