package com.healthcareplatform.PharmacyService.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO for each item in a prescription when creating.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionItemRequest {

    @NotNull(message = "Medication ID must not be null")
    private Long medicationId;

    @NotNull(message = "Quantity must not be null")
    private Integer quantity;
}