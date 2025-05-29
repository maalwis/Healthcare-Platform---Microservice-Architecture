package com.healthcareplatform.PharmacyService.medication;

import com.healthcareplatform.PharmacyService.dto.MedicationRequest;
import com.healthcareplatform.PharmacyService.dto.MedicationResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing medication master data and stock levels.
 */
@RestController
@RequestMapping("/api/v1/pharmacy/medications")
public class MedicationController {

    @Autowired
    private final MedicationService medicationService;

    public MedicationController(MedicationService medicationService) {

        this.medicationService = medicationService;
    }

    /**
     * Retrieve all medications.
     */
    @GetMapping
    public ResponseEntity<List<MedicationResponse>> getAll() {
        return ResponseEntity.ok(medicationService.getAllMedications());
    }

    /**
     * Retrieve a specific medication by ID.
     */
    @GetMapping("/{medicationId}")
    public ResponseEntity<MedicationResponse> getById(@PathVariable Long medicationId) {
        return ResponseEntity.ok(medicationService.getMedicationById(medicationId));
    }

    /**
     * Create a new medication master record.
     */
    @PostMapping
    public ResponseEntity<MedicationResponse> create(@Valid @RequestBody MedicationRequest dto) {
        MedicationResponse created = medicationService.createMedication(dto);
        return ResponseEntity.status(201).body(created);
    }

    /**
     * Update an existing medication record.
     */
    @PutMapping("/{medicationId}")
    public ResponseEntity<MedicationResponse> update(
            @PathVariable Long medicationId,
            @Valid @RequestBody MedicationRequest dto) {
        MedicationResponse updated = medicationService.updateMedication(medicationId, dto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Delete a medication from master data.
     */
    @DeleteMapping("/{medicationId}")
    public ResponseEntity<Void> delete(@PathVariable Long medicationId) {
        medicationService.deleteMedication(medicationId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Increase the stock level for a medication.
     *
     * @param medicationId the ID of the medication to restock
     * @param amount       the quantity to add
     */
    @PostMapping("/{medicationId}/re-stock")
    public ResponseEntity<Void> restock(
            @PathVariable Long medicationId,
            @RequestParam int amount) {
        medicationService.restockMedication(medicationId, amount);
        return ResponseEntity.noContent().build();
    }

    /**
     * Decrease the stock level for a medication.
     *
     * @param medicationId the ID of the medication to de-stock
     * @param amount       the quantity to remove
     */
    @PostMapping("/{medicationId}/de-stock")
    public ResponseEntity<Void> deStock(
            @PathVariable Long medicationId,
            @RequestParam int amount) {
        medicationService.deStockMedication(medicationId, amount);
        return ResponseEntity.noContent().build();
    }
}
