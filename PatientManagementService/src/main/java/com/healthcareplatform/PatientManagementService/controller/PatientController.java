package com.healthcareplatform.PatientManagementService.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {

    @PostMapping
    public ResponseEntity<?> createPatient() {
        try {
            // Delegate to PatientService to create a patient
            return ResponseEntity.status(HttpStatus.CREATED).body("Patient created successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create patient.");
        }
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<?> getPatientById(@PathVariable Long patientId) {
        try {
            // Delegate to PatientService to retrieve patient details
            return ResponseEntity.ok("Patient details for id " + patientId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found.");
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllPatients() {
        try {
            // Delegate to PatientService to retrieve all patients
            return ResponseEntity.ok("List of all patients.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve patients.");
        }
    }

    @PutMapping("/{patientId}")
    public ResponseEntity<?> updatePatient(@PathVariable Long patientId) {
        try {
            // Delegate to PatientService to update the patient
            return ResponseEntity.ok("Patient updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Patient update failed.");
        }
    }

    @DeleteMapping("/{patientId}")
    public ResponseEntity<?> deletePatient(@PathVariable Long patientId) {
        try {
            // Delegate to PatientService to delete the patient
            return ResponseEntity.ok("Patient deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Patient deletion failed.");
        }
    }
}