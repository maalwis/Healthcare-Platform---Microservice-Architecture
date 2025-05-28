package com.healthcareplatform.PatientService.patient;

import com.healthcareplatform.PatientService.dto.PatientRequest;
import com.healthcareplatform.PatientService.dto.PatientResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {

    @Autowired
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    /**
     * Retrieve a list of all patients.

     * @return ResponseEntity containing a list of PatientResponse objects and HTTP 200 status.
     */
    @GetMapping
    public ResponseEntity<List<PatientResponse>> getAllPatients() {
        // Delegate to PatientService to retrieve all patients
        List<PatientResponse> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
    }

    /**
     * Retrieve details for a specific patient by ID.
     *
     * @param Id Unique identifier of the patient (path variable)
     * @return ResponseEntity containing PatientResponse and HTTP 200 status if found;
     *         otherwise exception is propagated (e.g., 404 Not Found).
     */
    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> getPatientById(@PathVariable Long Id) {
        // TODO: Delegate to PatientService to fetch patient by ID
        PatientResponse patient = patientService.getPatientById(Id);
        return ResponseEntity.ok(patient);
    }

    /**
     * Create a new patient record.
     *
     * @param patient Payload containing patient data (validated request body)
     * @return ResponseEntity containing created PatientResponse, HTTP 201 status.
     */
    @PostMapping
    public ResponseEntity<PatientResponse> createPatient(@Valid @RequestBody PatientRequest patient) {
        // Delegate to PatientService to create a new patient
        PatientResponse created = patientService.createPatient(patient);

        return ResponseEntity.ok(created);
    }

    /**
     * Update an existing patient's details.
     *
     * @param Id Unique identifier of the patient (path variable)
     * @param patientRequest Payload containing updated data (validated request body)
     * @return ResponseEntity containing updated PatientResponse and HTTP 200 status.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PatientResponse> updatePatient(
            @PathVariable Long Id,
            @Valid @RequestBody PatientRequest patientRequest) {
        // Delegate to PatientService to update patient details
        PatientResponse updated = patientService.updatePatient(Id, patientRequest);
        return ResponseEntity.ok(updated);
    }

    /**
     * Delete a patient record by ID.

     * @param Id Unique identifier of the patient (path variable)
     * @return ResponseEntity with HTTP 204 No Content on successful deletion.
     */
    @DeleteMapping("/{patientId}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long Id) {
        // Delegate to PatientService to delete patient
        patientService.deletePatient(Id);
        return ResponseEntity.noContent().build();
    }
}