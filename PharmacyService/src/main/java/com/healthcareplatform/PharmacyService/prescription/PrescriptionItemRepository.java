package com.healthcareplatform.PharmacyService.prescription;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PrescriptionItemRepository extends JpaRepository<PrescriptionItem, Long> {
    Collection<Object> findByPrescriptionId(Long id);
}
