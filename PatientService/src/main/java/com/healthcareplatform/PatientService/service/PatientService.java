package com.healthcareplatform.PatientService.service;

import com.healthcareplatform.PatientService.dto.PatientDto;
import com.healthcareplatform.PatientService.exception.ResourceNotFoundException;
import com.healthcareplatform.PatientService.messaging.publisher.PatientEventPublisher;
import com.healthcareplatform.PatientService.model.Patient;
import com.healthcareplatform.PatientService.repository.PatientRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.UUID;
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
    private final PatientRepository patientRepository;

    /**
     * Event publisher for broadcasting patient registration, and update events.
     */
    private final PatientEventPublisher patientEventPublisher;

    /**
     * Constructor injection of PatientRepository.
     *
     * @param patientRepository Spring Data JPA repository for Patient entity operations
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
     * @return List of PatientDto objects representing all patients
     * @throws DataAccessException if a database access error occurs
     */
    @Transactional(readOnly = true)
    public List<PatientDto> getAllPatients() {
        // fetch all Patient entities
        List<Patient> patients = patientRepository.findAll();
        // map each Patient entity to PatientDto and collect in a list
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
     * @return PatientDto representing the found patient
     * @throws ResourceNotFoundException if patient with given id does not exist
     * @throws DataAccessException if a database access error occurs
     */
    @Transactional(readOnly = true)
    public PatientDto getPatientById(UUID id) {
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
     * Patient entity is converted to PatientDto and returned.
     *
     * @param patientDto PatientDto containing data for the new patient
     * @return PatientDto of the newly created patient (includes generated UUID)
     * @throws DataIntegrityViolationException if validation or constraints fail
     * @throws DataAccessException if a database access error occurs
     */
    public PatientDto createPatient(PatientDto patientDto) {
        // map DTO to a new Patient entity
        Patient patient = mapToEntity(patientDto);
        // set creation Time
        patient.setCreatedAt(OffsetDateTime.now());
        // generate a new UUID for the new patient record
        patient.setId(UUID.randomUUID());
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
     * @param id  UUID of the patient to update
     * @param patientDto PatientDto containing updated data
     * @return PatientDto of the updated patient
     * @throws ResourceNotFoundException if patient with given id does not exist
     * @throws OptimisticLockingFailureException if concurrent modification detected
     * @throws DataAccessException if a database access error occurs
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public PatientDto updatePatient(UUID id, PatientDto patientDto) {
        // fetch existing patient or throw if not found
        Patient existing = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
        // update entity fields from DTO
        // TODO: update entity fields from DTO
        try {
            // Implementation: save updated entity (commit at transaction end)
            Patient updated = patientRepository.save(existing);
            // Implementation: map updated entity to DTO and return
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
     * @throws DataAccessException if a database access error occurs
     */
    public void deletePatient(UUID patientId) {
        // verify existence to provide clear error if absent
        if (!patientRepository.existsById(patientId)) {
            throw new ResourceNotFoundException("Patient not found with id: " + patientId);
        }
        // Implementation: delete entity by ID within transactional context
        patientRepository.deleteById(patientId);
    }

    // Helper method to map Patient entity to PatientDto
    private PatientDto mapToDto(Patient patient) {
        PatientDto dto = new PatientDto();
        dto.setId(patient.getId());
        dto.setFirstName(patient.getFirstName());
        dto.setLastName(patient.getLastName());
        dto.setDateOfBirth(patient.getDateOfBirth());
        dto.setGender(patient.getGender());
        dto.setContactInfo(patient.getContactInfo());
        dto.setMetadata(patient.getMetadata());
        return dto;
    }

    // Helper method to map PatientDto to Patient entity
    private Patient mapToEntity(PatientDto dto) {
        Patient patient = new Patient();
        // set fields from DTO; ID is generated in createPatient
        patient.setFirstName(dto.getFirstName());
        patient.setLastName(dto.getLastName());
        patient.setDateOfBirth(dto.getDateOfBirth());
        patient.setGender(dto.getGender());
        patient.setContactInfo(dto.getContactInfo());
        patient.setMetadata(dto.getMetadata());
        return patient;
    }
}


