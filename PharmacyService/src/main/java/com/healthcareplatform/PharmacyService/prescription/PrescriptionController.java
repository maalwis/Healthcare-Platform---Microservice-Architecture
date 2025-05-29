package com.healthcareplatform.PharmacyService.prescription;

import com.healthcareplatform.PharmacyService.dto.PrescriptionRequest;
import com.healthcareplatform.PharmacyService.dto.PrescriptionResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing prescriptions.
 */
@RestController
@RequestMapping("/api/v1/pharmacy/prescriptions")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    /**
     * Retrieve all prescriptions.
     */
    @GetMapping
    public ResponseEntity<List<PrescriptionResponse>> getAll() {
        return ResponseEntity.ok(prescriptionService.getAllPrescriptions());
    }

    /**
     * Retrieve a specific prescription by ID.
     */
    @GetMapping("/{prescriptionId}")
    public ResponseEntity<PrescriptionResponse> getById(@PathVariable Long prescriptionId) {
        return ResponseEntity.ok(prescriptionService.getPrescriptionById(prescriptionId));
    }

    /**
     * Create a new prescription.
     */
    @PostMapping
    public ResponseEntity<PrescriptionResponse> create(@Valid @RequestBody PrescriptionRequest dto) {
        PrescriptionResponse created = prescriptionService.createPrescription(dto);
        return ResponseEntity.status(201).body(created);
    }

    /**
     * Mark a prescription as filled.
     */
    @PostMapping("/{prescriptionId}/fill")
    public ResponseEntity<Void> fill(@PathVariable Long prescriptionId) {
        prescriptionService.fillPrescription(prescriptionId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Mark a prescription as dispensed.
     */
    @PostMapping("/{prescriptionId}/dispense")
    public ResponseEntity<Void> dispense(@PathVariable Long prescriptionId) {
        prescriptionService.dispensePrescription(prescriptionId);
        return ResponseEntity.noContent().build();
    }
}
