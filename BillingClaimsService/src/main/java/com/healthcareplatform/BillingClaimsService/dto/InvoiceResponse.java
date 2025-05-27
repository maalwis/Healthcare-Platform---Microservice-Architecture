package com.healthcareplatform.BillingClaimsService.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceResponse {
    Long id;
    Long patientId;
    LocalDateTime dateIssued;
    BigDecimal totalAmount;
    String status;
    List<InvoiceItemResponse> item;

}