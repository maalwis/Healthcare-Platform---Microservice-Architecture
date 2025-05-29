package com.healthcareplatform.PharmacyService.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * DTO for creating a Prescription, including items.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionRequest {

    @NotNull(message = "Patient ID must not be null")
    private Long patientId;

    @NotNull(message = "Doctor ID must not be null")
    private Long doctorId;

    /**
     * Optional notes or JSON payload for the prescription.
     */
    private String notes;

    @NotNull(message = "Prescription must contain at least one item")
    @Size(min = 1, message = "At least one prescription item is required")
    private List<PrescriptionItemRequest> items;
}