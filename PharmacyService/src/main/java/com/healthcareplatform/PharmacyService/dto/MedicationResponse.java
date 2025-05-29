package com.healthcareplatform.PharmacyService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for exposing Medication data to clients.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicationResponse {

    private Long id;
    private String name;
    private String description;
    private String manufacturer;
    private BigDecimal cost;
    private String info;
    private Integer stock;
    private LocalDateTime createdAt;
}
