package com.healthcareplatform.PatientService.service;

import com.healthcareplatform.PatientService.dto.PatientDto;

import java.util.List;
import java.util.UUID;

public interface PatientService {
    public List<PatientDto> getAllPatients();

    PatientDto getPatientById(UUID patientId);

    PatientDto createPatient(PatientDto patient);

    PatientDto updatePatient(UUID patientId, PatientDto patientDto);

    void deletePatient(UUID patientId);

}
