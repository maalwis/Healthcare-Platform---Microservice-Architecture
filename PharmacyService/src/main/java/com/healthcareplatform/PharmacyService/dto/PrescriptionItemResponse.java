package com.healthcareplatform.PharmacyService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO for each item in a prescription when returning.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionItemResponse {

    private Long medicationId;
    private Integer quantity;
}