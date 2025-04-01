package com.healthcareplatform.PatientManagementService.service;

import com.healthcareplatform.PatientManagementService.dto.PatientResponse;
import java.util.List;

public interface PatientService {

    PatientResponse createPatient();

    PatientResponse getPatientById(Long id);

    List<PatientResponse> getAllPatients();

    PatientResponse updatePatient(Long id);

    void deletePatient(Long id);
}