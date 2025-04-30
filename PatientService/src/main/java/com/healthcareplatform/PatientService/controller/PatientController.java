package com.healthcareplatform.PatientService.controller;

import com.healthcareplatform.PatientService.dto.PatientDto;
import com.healthcareplatform.PatientService.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    /**
     * Retrieve a list of all patients.

     * @return ResponseEntity containing a list of PatientDto objects and HTTP 200 status.
     */
    @GetMapping
    public ResponseEntity<List<PatientDto>> getAllPatients() {
        // Delegate to PatientService to retrieve all patients
        List<PatientDto> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
    }

    /**
     * Retrieve details for a specific patient by ID.
     *
     * @param patientId Unique identifier of the patient (path variable)
     * @return ResponseEntity containing PatientDto and HTTP 200 status if found;
     *         otherwise exception is propagated (e.g., 404 Not Found).
     */
    @GetMapping("/{id}")
    public ResponseEntity<PatientDto> getPatientById(@PathVariable UUID patientId) {
        // TODO: Delegate to PatientService to fetch patient by ID
        PatientDto patient = patientService.getPatientById(patientId);
        return ResponseEntity.ok(patient);
    }

    /**
     * Create a new patient record.
     *
     * @param patient Payload containing patient data (validated request body)
     * @return ResponseEntity containing created PatientDto, HTTP 201 status.
     */
    @PostMapping
    public ResponseEntity<PatientDto> createPatient(@Valid @RequestBody PatientDto patient) {
        // Delegate to PatientService to create a new patient
        PatientDto created = patientService.createPatient(patient);

        return ResponseEntity.ok(created);
    }

    /**
     * Update an existing patient's details.
     *
     * @param patientId Unique identifier of the patient (path variable)
     * @param patientDto Payload containing updated data (validated request body)
     * @return ResponseEntity containing updated PatientDto and HTTP 200 status.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PatientDto> updatePatient(
            @PathVariable UUID patientId,
            @Valid @RequestBody PatientDto patientDto) {
        // Delegate to PatientService to update patient details
        PatientDto updated = patientService.updatePatient(patientId, patientDto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Delete a patient record by ID.

     * @param patientId Unique identifier of the patient (path variable)
     * @return ResponseEntity with HTTP 204 No Content on successful deletion.
     */
    @DeleteMapping("/{patientId}")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID patientId) {
        // Delegate to PatientService to delete patient
        patientService.deletePatient(patientId);
        return ResponseEntity.noContent().build();
    }
}