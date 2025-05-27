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
public class ClaimDto {
    private Long id;
    private Long invoiceId;
    private String insurer;
    private String claimStatus;
    private String details;
    private LocalDateTime submittedAt;
    private LocalDateTime createdAt;

}
