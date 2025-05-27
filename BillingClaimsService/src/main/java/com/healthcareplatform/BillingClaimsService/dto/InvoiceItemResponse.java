package com.healthcareplatform.BillingClaimsService.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceItemResponse {
    @NotNull
    @Size(min = 1)
    private String description;
    @NotNull
    BigDecimal amount;
}
