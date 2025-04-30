package com.healthcareplatform.PharmacyService.model;

import java.io.Serializable;
import java.util.UUID;

public class PrescriptionItemId implements Serializable {
    private UUID prescriptionId;
    private UUID medicationId;

}