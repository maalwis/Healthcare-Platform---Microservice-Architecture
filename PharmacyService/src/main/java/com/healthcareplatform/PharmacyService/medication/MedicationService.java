package com.healthcareplatform.PharmacyService.medication;

import com.healthcareplatform.PharmacyService.dto.MedicationRequest;
import com.healthcareplatform.PharmacyService.dto.MedicationResponse;
import com.healthcareplatform.PharmacyService.exception.MedicationNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Service for managing Medication master data and stock levels.
 */
@Service
public class MedicationService {

    @Autowired
    private final MedicationRepository medicationRepository;

    public MedicationService(MedicationRepository medicationRepository) {
        this.medicationRepository = medicationRepository;
    }

    /**
     * Retrieve all medications.
     */
    @Transactional(readOnly = true)
    public List<MedicationResponse> getAllMedications() {
        return medicationRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieve one medication by ID.
     */
    @Transactional(readOnly = true)
    public MedicationResponse getMedicationById(Long id) {
        Medication med = loadMedication(id);
        return mapToDto(med);
    }

    /**
     * Create a new medication record.
     */
    @Transactional
    public MedicationResponse createMedication(MedicationRequest dto) {
        Objects.requireNonNull(dto, "MedicationRequest must not be null");
        Medication med = mapToEntity(dto);
        Medication saved = medicationRepository.save(med);
        return mapToDto(saved);
    }

    /**
     * Update an existing medication record.
     */
    @Transactional
    public MedicationResponse updateMedication(Long id, MedicationRequest dto) {
        Medication med = loadMedication(id);
        med.setName(dto.getName());
        med.setDescription(dto.getDescription());
        med.setManufacturer(dto.getManufacturer());
        med.setCost(dto.getCost());
        med.setInfo(dto.getInfo());
        Medication saved = medicationRepository.save(med);
        return mapToDto(saved);
    }

    /**
     * Delete a medication.
     */
    @Transactional
    public void deleteMedication(Long id) {
        Medication med = loadMedication(id);
        medicationRepository.delete(med);
    }

    /**
     * Increase stock level for medication.
     * Assumes Medication has a 'stock' field.
     */
    @Transactional
    public void restockMedication(Long id, int amount) {
        Medication med = loadMedication(id);
        med.setStock(med.getStock() + amount);
        medicationRepository.save(med);
    }

    /**
     * Decrease stock level for medication.
     */
    @Transactional
    public void deStockMedication(Long id, int amount) {
        Medication med = loadMedication(id);
        int current = med.getStock();
        if (current < amount) {
            throw new IllegalArgumentException("Insufficient stock to de-stock");
        }
        med.setStock(current - amount);
        medicationRepository.save(med);
    }

    /**
     * Helper to load or throw 404.
     */
    private Medication loadMedication(Long id) {
        return medicationRepository.findById(id)
                .orElseThrow(() -> new MedicationNotFoundException("Medication not found: " + id));
    }

    /**
     * Map entity to DTO.
     */
    private MedicationResponse mapToDto(Medication med) {
        return new MedicationResponse(
                med.getId(),
                med.getName(),
                med.getDescription(),
                med.getManufacturer(),
                med.getCost(),
                med.getInfo(),
                med.getStock(),
                med.getCreatedAt()
        );
    }

    /**
     * Map DTO to entity.
     */
    private Medication mapToEntity(MedicationRequest dto) {
        Medication med = new Medication();
        med.setName(dto.getName());
        med.setDescription(dto.getDescription());
        med.setManufacturer(dto.getManufacturer());
        med.setCost(dto.getCost());
        med.setInfo(dto.getInfo());
        med.setStock(dto.getInitialStock());
        return med;
    }
}
