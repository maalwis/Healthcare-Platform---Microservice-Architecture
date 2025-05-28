package com.healthcareplatform.PatientService.patient;

import com.healthcareplatform.PatientService.dto.PatientRequest;
import com.healthcareplatform.PatientService.dto.PatientResponse;
import com.healthcareplatform.PatientService.exception.ResourceNotFoundException;
import com.healthcareplatform.PatientService.eventPublisher.PatientEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.stream.Collectors;



/**
 * Service layer implementation for patient management operations.
 * <p>
 * This class handles business logic and transaction management for creating,
 * retrieving, updating, and deleting patient records. All public methods
 * run within a transactional context provided by Spring.
 */
@Service
@Transactional
public class PatientService {

    /**
     * Repository for performing CRUD operations on Patient entities.
     */
    @Autowired
    private final PatientRepository patientRepository;

    /**
     * Event publisher for broadcasting patient registration, and update events.
     */
    @Autowired
    private final PatientEventPublisher patientEventPublisher;

    /**
     * Constructor injection of PatientRepository.
     *
     * @param patientRepository     Spring Data JPA repository for Patient entity operations
     * @param patientEventPublisher Publisher to emit events after patient creation or patient update
     */
    public PatientService(PatientRepository patientRepository,
                          PatientEventPublisher patientEventPublisher) {
        this.patientRepository = patientRepository;
        this.patientEventPublisher = patientEventPublisher;
    }

    /**
     * Retrieve all patients from the database.
     * <p>
     * Runs within a read-only transaction for performance optimization.
     *
     * @return List of PatientResponse objects representing all patients
     * @throws DataAccessException if a database access error occurs
     */
    @Transactional(readOnly = true)
    public List<PatientResponse> getAllPatients() {
        // fetch all Patient entities
        List<Patient> patients = patientRepository.findAll();
        // map each Patient entity to PatientResponse and collect in a list
        return patients.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieve a specific patient by their unique identifier.
     * <p>
     * Runs within a read-only transaction. Throws a ResourceNotFoundException
     * if no matching patient is found.
     *
     * @param id UUID of the patient to retrieve
     * @return PatientResponse representing the found patient
     * @throws ResourceNotFoundException if patient with given id does not exist
     * @throws DataAccessException       if a database access error occurs
     */
    @Transactional(readOnly = true)
    public PatientResponse getPatientById(Long id) {
        // attempt to find the Patient entity by ID
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
        // convert found entity to DTO
        return mapToDto(patient);
    }

    /**
     * Create a new patient record in the database.
     * <p>
     * Runs within a read-write transaction. On success, the persisted
     * Patient entity is converted to PatientResponse and returned.
     *
     * @param patientRequest PatientResponse containing data for the new patient
     * @return PatientResponse of the newly created patient (includes generated UUID)
     * @throws DataIntegrityViolationException if validation or constraints fail
     * @throws DataAccessException             if a database access error occurs
     */
    public PatientResponse createPatient(PatientRequest patientRequest) {
        // map DTO to a new Patient entity
        Patient patient = mapPatientCreateResponseToEntity(patientRequest);
        // set creation Time
        patient.setCreatedAt(LocalDateTime.now());

        // save entity to the database (transactional)
        Patient saved = patientRepository.save(patient);

        // Publish event for downstream systems (RabbitMQ)
        patientEventPublisher.publishPatientRegistered(mapToDto(saved));

        // convert saved entity back to DTO and return
        return mapToDto(saved);
    }

    /**
     * Update an existing patient's details.
     * <p>
     * Runs within a read-write transaction. Fetches the existing entity,
     * applies changes, and saves the updated entity.
     *
     * @param id             UUID of the patient to update
     * @param patientRequest PatientResponse containing updated data
     * @return PatientResponse of the updated patient
     * @throws ResourceNotFoundException         if patient with given id does not exist
     * @throws OptimisticLockingFailureException if concurrent modification detected
     * @throws DataAccessException               if a database access error occurs
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public PatientResponse updatePatient(Long id, PatientRequest patientRequest) {
        // fetch existing patient or throw if not found
        Patient existing = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));

        // TODO : there is better way to do this.
        try {
            if (patientRequest.getFirstName() != null) {
                existing.setFirstName(patientRequest.getFirstName());
            }
            if (patientRequest.getLastName() != null) {
                existing.setLastName(patientRequest.getLastName());
            }
            if (patientRequest.getDateOfBirth() != null) {
                existing.setDateOfBirth(patientRequest.getDateOfBirth());
            }
            if (patientRequest.getGender() != null) {
                existing.setGender(patientRequest.getGender());
            }
            if (patientRequest.getContactInfo() != null) {
                existing.setContactInfo(patientRequest.getContactInfo());
            }

            Patient updated = patientRepository.save(existing);


            patientEventPublisher.publishPatientUpdated(mapToDto(updated));

            return mapToDto(updated);

        } catch (ObjectOptimisticLockingFailureException e) {
            // Implementation: handle optimistic locking failure for concurrent updates
            throw new ConcurrentModificationException(
                    "Failed to update patient due to concurrent modification: " + id);
        }
    }

    /**
     * Delete a patient record by its unique identifier.
     * <p>
     * Runs within a read-write transaction. Checks for existence before deletion.
     *
     * @param patientId UUID of the patient to delete
     * @throws ResourceNotFoundException if patient with given patientId does not exist
     * @throws DataAccessException       if a database access error occurs
     */
    public void deletePatient(Long patientId) {
        // verify existence to provide clear error if absent
        if (!patientRepository.existsById(patientId)) {
            throw new ResourceNotFoundException("Patient not found with id: " + patientId);
        }
        // Implementation: delete entity by ID within transactional context
        patientRepository.deleteById(patientId);
    }

    // Helper method to map Patient entity to PatientResponse
    private PatientResponse mapToDto(Patient patient) {
        PatientResponse dto = new PatientResponse();
        dto.setId(patient.getId());
        dto.setFirstName(patient.getFirstName());
        dto.setLastName(patient.getLastName());
        dto.setDateOfBirth(patient.getDateOfBirth());
        dto.setGender(patient.getGender());
        dto.setContactInfo(patient.getContactInfo());
        dto.setMetadata(patient.getMetadata());
        return dto;
    }

    // Helper method to map PatientResponse to Patient entity
    private Patient mapPatientCreateResponseToEntity(PatientRequest dto) {
        Patient patient = new Patient();
        // set fields from DTO; ID is generated in createPatient
        patient.setFirstName(dto.getFirstName());
        patient.setLastName(dto.getLastName());
        patient.setDateOfBirth(dto.getDateOfBirth());
        patient.setGender(dto.getGender());
        patient.setContactInfo(dto.getContactInfo());
        return patient;
    }


}


