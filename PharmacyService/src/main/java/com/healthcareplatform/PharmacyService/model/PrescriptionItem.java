package com.healthcareplatform.PharmacyService.model;

import jakarta.persistence.*;

import java.util.UUID;

/**
 * Models each line item within a prescription, linking a prescription to medications.
 *
 * <p>As a composite-key entity (“prescription_items”):
 * <ul>
 *   <li><b>prescriptionId</b> identifies the parent prescription.</li>
 *   <li><b>medicationId</b> references the prescribed drug’s UUID.</li>
 *   <li><b>quantity</b> specifies the number of units prescribed.</li>
 * </ul>
 *
 * <p>This structure supports many-to-many relationships between prescriptions and medications
 * without duplicating prescription metadata.
 */

@Entity
@Table(name = "prescription_items")
@IdClass(PrescriptionItemId.class)
public class PrescriptionItem {

    @Id
    @Column(name = "prescription_id")
    private UUID prescriptionId;

    @Id
    @Column(name = "medication_id")
    private UUID medicationId;

    @Column(nullable = false)
    private Integer quantity;
}

   