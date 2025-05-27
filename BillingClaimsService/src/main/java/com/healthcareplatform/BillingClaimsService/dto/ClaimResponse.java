package com.healthcareplatform.BillingClaimsService.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClaimResponse {
    private Long id;
    private Long invoiceId;
    private String insurer;
    private String claimStatus;
    private String details;
    private LocalDateTime submittedAt;
    private LocalDateTime createdAt;
}
