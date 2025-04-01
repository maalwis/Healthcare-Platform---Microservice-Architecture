package com.healthcareplatform.PrescriptionService.repository;

import com.healthcareplatform.PrescriptionService.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
}