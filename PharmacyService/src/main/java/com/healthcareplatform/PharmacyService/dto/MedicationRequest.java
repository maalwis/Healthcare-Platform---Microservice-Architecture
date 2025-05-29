package com.healthcareplatform.PharmacyService.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for creating or updating a Medication record.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicationRequest {

    @NotBlank(message = "Medication name must not be blank")
    private String name;

    private String description;

    private String manufacturer;

    @NotNull(message = "Cost must not be null")
    private BigDecimal cost;

    private String info;

    @NotNull(message = "Initial stock must not be null")
    @Min(value = 0, message = "Initial stock cannot be negative")
    private Integer initialStock;
}
