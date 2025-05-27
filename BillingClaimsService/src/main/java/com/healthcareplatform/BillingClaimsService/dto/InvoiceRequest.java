package com.healthcareplatform.BillingClaimsService.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceRequest {

    @NotNull
    private Long patientId;

    @NotNull
    private LocalDateTime dateIssued;

    private List<InvoiceItemResponse> items;

    private String status;
}
