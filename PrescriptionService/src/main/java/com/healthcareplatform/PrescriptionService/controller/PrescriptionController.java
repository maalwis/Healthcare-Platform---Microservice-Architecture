package com.healthcareplatform.PrescriptionService.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/prescriptions")
public class PrescriptionController {

    @PostMapping
    public ResponseEntity<?> createPrescription() {
        try {
            // Delegate to PrescriptionService to create a prescription
            return ResponseEntity.status(HttpStatus.CREATED).body("Prescription created successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create prescription.");
        }
    }

    @GetMapping("/{prescriptionId}")
    public ResponseEntity<?> getPrescriptionById(@PathVariable Long prescriptionId) {
        try {
            // Delegate to PrescriptionService to retrieve prescription details
            return ResponseEntity.ok("Prescription details for id " + prescriptionId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Prescription not found.");
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllPrescriptions() {
        try {
            // Delegate to PrescriptionService to retrieve all prescriptions
            return ResponseEntity.ok("List of all prescriptions.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve prescriptions.");
        }
    }

    @PutMapping("/{prescriptionId}")
    public ResponseEntity<?> updatePrescription(@PathVariable Long prescriptionId) {
        try {
            // Delegate to PrescriptionService to update the prescription
            return ResponseEntity.ok("Prescription updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Prescription update failed.");
        }
    }

    @DeleteMapping("/{prescriptionId}")
    public ResponseEntity<?> deletePrescription(@PathVariable Long prescriptionId) {
        try {
            // Delegate to PrescriptionService to delete the prescription
            return ResponseEntity.ok("Prescription deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Prescription deletion failed.");
        }
    }
}