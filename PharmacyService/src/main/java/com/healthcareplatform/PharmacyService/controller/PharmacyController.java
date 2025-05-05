package com.healthcareplatform.PharmacyService.controller;

import com.healthcareplatform.PharmacyService.dto.PrescriptionDto;
import com.healthcareplatform.PharmacyService.service.PharmacyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for managing prescriptions and medication dispensing.
 */
@RestController
@RequestMapping("/api/v1/pharmacy/prescriptions")
public class PharmacyController {

    @Autowired
    private final PharmacyService pharmacyService;

    public PharmacyController(PharmacyService pharmacyService) {
        this.pharmacyService = pharmacyService;
    }

    /**
     * Retrieve a list of all prescriptions.
     * <p>
     * TODO: Delegate to PharmacyService to retrieve all prescriptions
     *
     * @return ResponseEntity containing a list of PrescriptionDto objects and HTTP 200 status.
     */
    @GetMapping
    public ResponseEntity<List<PrescriptionDto>> getAllPrescriptions() {
        List<PrescriptionDto> prescriptions = pharmacyService.getAllPrescriptions();
        return ResponseEntity.ok(prescriptions);
    }

    /**
     * Retrieve details for a specific prescription by ID.
     * <p>
     * TODO: Delegate to PharmacyService to fetch prescription by ID
     *
     * @param prescriptionId Unique identifier of the prescription (path variable)
     * @return ResponseEntity containing PrescriptionDto and HTTP 200 status if found;
     * otherwise exception is propagated (e.g., 404 Not Found).
     */
    @GetMapping("/{prescriptionId}")
    public ResponseEntity<PrescriptionDto> getPrescriptionById(@PathVariable UUID prescriptionId) {
        PrescriptionDto prescription = pharmacyService.getPrescriptionById(prescriptionId);
        return ResponseEntity.ok(prescription);
    }

    /**
     * Create a new prescription record.
     * <p>
     * TODO: Delegate to PharmacyService to create a new prescription
     *
     * @param prescription Payload containing prescription data (validated request body)
     * @return ResponseEntity containing created PrescriptionDto and HTTP 201 status.
     */
    @PostMapping
    public ResponseEntity<PrescriptionDto> createPrescription(@Valid @RequestBody PrescriptionDto prescription) {
        PrescriptionDto created = pharmacyService.createPrescription(prescription);
        return ResponseEntity.status(201).body(created);
    }

    /**
     * Mark a prescription as filled.
     * <p>
     * TODO: Delegate to PharmacyService to fill prescription
     *
     * @param prescriptionId Unique identifier of the prescription (path variable)
     * @return ResponseEntity with HTTP 204 No Content on successful fill.
     */
    @PostMapping("/{id}/fill")
    public ResponseEntity<Void> fillPrescription(@PathVariable UUID prescriptionId) {
        pharmacyService.fillPrescription(prescriptionId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Mark a prescription as dispense.
     * <p>
     * TODO: Delegate to PharmacyService to fill prescription
     *
     * @param prescriptionId Unique identifier of the prescription (path variable)
     * @return ResponseEntity with HTTP 204 No Content on successful fill.
     */
    @PostMapping("/{id}/dispense")
    public ResponseEntity<Void> dispensePrescription(@PathVariable UUID prescriptionId) {
        pharmacyService.dispensePrescription(prescriptionId);
        return ResponseEntity.noContent().build();
    }
}