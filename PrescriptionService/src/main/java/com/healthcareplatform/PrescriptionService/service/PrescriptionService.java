package com.healthcareplatform.PrescriptionService.service;

import com.healthcareplatform.PrescriptionService.dto.PrescriptionResponse;
import java.util.List;

public interface PrescriptionService {

    PrescriptionResponse createPrescription();

    PrescriptionResponse getPrescriptionById(Long id);

    List<PrescriptionResponse> getAllPrescriptions();

    PrescriptionResponse updatePrescription(Long id);

    void deletePrescription(Long id);
}