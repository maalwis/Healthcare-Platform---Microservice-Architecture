package com.healthcareplatform.BillingClaimsService.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceClaimRequest {
    @NotNull
    private Long invoiceId;
    private String insurer;
    private String details;
}
