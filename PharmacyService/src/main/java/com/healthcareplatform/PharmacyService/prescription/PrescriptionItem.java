package com.healthcareplatform.PharmacyService.prescription;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Models each line item within a prescription, linking a prescription to medications.
 */

@Entity
@Table(name = "prescription_items")
@IdClass(PrescriptionItemId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionItem {

    @Id
    @Column(name = "prescription_id")
    private Long prescriptionId;

    @Id
    @Column(name = "medication_id")
    private Long medicationId;

    @Column(nullable = false)
    private Integer quantity;
}

   