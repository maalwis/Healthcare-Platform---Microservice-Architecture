package com.healthcareplatform.AnalyticsService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDto {
    private Long id;
    private Long patientId;
    private OffsetDateTime dateIssued;
    private BigDecimal totalAmount;
    private String status;
    private LocalDateTime createdAt;
    private List<InvoiceItemDto> lineItems;


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InvoiceItemDto {
        private String description;
        private BigDecimal amount;

    }
}
