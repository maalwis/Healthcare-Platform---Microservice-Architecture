package com.healthcareplatform.BillingClaimsService.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClaimRequest {
    private Long invoiceId;
    private String insurer;
    private String details;
}