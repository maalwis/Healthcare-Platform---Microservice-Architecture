package com.healthcareplatform.PharmacyService.service;

import com.healthcareplatform.PharmacyService.dto.PrescriptionDto;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface PharmacyService {
    PrescriptionDto getPrescriptionById(UUID prescriptionId);

    PrescriptionDto createPrescription(@Valid PrescriptionDto prescription);

    void fillPrescription(UUID prescriptionId);

    List<PrescriptionDto> getAllPrescriptions();

}
