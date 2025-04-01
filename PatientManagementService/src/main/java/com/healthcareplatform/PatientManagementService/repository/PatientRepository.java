package com.healthcareplatform.PatientManagementService.repository;

import com.healthcareplatform.PatientManagementService.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
}