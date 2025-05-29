package com.healthcareplatform.PharmacyService.prescription;

import com.healthcareplatform.PharmacyService.dto.PrescriptionRequest;
import com.healthcareplatform.PharmacyService.dto.PrescriptionResponse;
import com.healthcareplatform.PharmacyService.dto.PrescriptionItemResponse;
import com.healthcareplatform.PharmacyService.exception.ExternalServiceException;
import com.healthcareplatform.PharmacyService.exception.PrescriptionNotFoundException;
import com.healthcareplatform.PharmacyService.exception.ResourceNotFoundException;
import com.healthcareplatform.PharmacyService.patientClient.PatientClient;
import com.healthcareplatform.PharmacyService.staffClient.StaffClient;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PrescriptionService {

    private static final String STATUS_ISSUED   = "ISSUED";
    private static final String STATUS_FILLED   = "FILLED";
    private static final String STATUS_DISPENSED = "DISPENSED";

    @Autowired
    private final PrescriptionRepository prescriptionRepository;

    @Autowired
    private final PrescriptionItemRepository itemRepository;

    @Autowired
    private final PatientClient patientClient;

    @Autowired
    private final StaffClient staffClient;

    public PrescriptionService(PrescriptionRepository prescriptionRepository,
                               PrescriptionItemRepository itemRepository,
                               PatientClient patientClient,
                               StaffClient staffClient) {
        this.prescriptionRepository = prescriptionRepository;
        this.itemRepository = itemRepository;
        this.patientClient = patientClient;
        this.staffClient = staffClient;
    }

    /**
     * Retrieve all prescriptions.
     */
    @Transactional(readOnly = true)
    public List<PrescriptionResponse> getAllPrescriptions() {
        return prescriptionRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieve one prescription by ID.
     */
    @Transactional(readOnly = true)
    public PrescriptionResponse getPrescriptionById(Long id) {
        Prescription p = loadPrescription(id);
        return mapToDto(p);
    }

    /**
     * Create a new prescription.
     */
    @Transactional
    public PrescriptionResponse createPrescription(@Valid PrescriptionRequest req) {
        Objects.requireNonNull(req, "PrescriptionRequest must not be null");

        // fetch patient & doctor info
        var patientDto = fetchPatient(req.getPatientId());
        var staffDto   = fetchStaff(req.getDoctorId());

        // build and save prescription
        Prescription presc = mapToEntity(req, patientDto, staffDto);
        presc.setStatus(STATUS_ISSUED);
        presc.setDateIssued(LocalDateTime.now());
        Prescription saved = prescriptionRepository.save(presc);

        // save items
        req.getItems().forEach(itemReq -> {
            PrescriptionItem item = new PrescriptionItem();
            item.setPrescriptionId(saved.getId());
            item.setMedicationId(itemReq.getMedicationId());
            item.setQuantity(itemReq.getQuantity());
            itemRepository.save(item);
        });

        return mapToDto(saved);
    }

    /**
     * Mark as FILLED.
     */
    @Transactional
    public void fillPrescription(Long id) {
        Prescription p = loadPrescription(id);
        p.setStatus(STATUS_FILLED);
        prescriptionRepository.save(p);
    }

    /**
     * Mark as DISPENSED.
     */
    @Transactional
    public void dispensePrescription(Long id) {
        Prescription p = loadPrescription(id);
        p.setStatus(STATUS_DISPENSED);
        prescriptionRepository.save(p);
    }

    //––– Helpers –––//

    /** Load or throw 404 */
    private Prescription loadPrescription(Long id) {
        return prescriptionRepository.findById(id)
                .orElseThrow(() ->
                        new PrescriptionNotFoundException("Prescription not found: " + id));
    }

    /** Remote call to patient service */
    private com.healthcareplatform.PharmacyService.dto.PatientDto fetchPatient(Long pid) {
        try {
            ResponseEntity<com.healthcareplatform.PharmacyService.dto.PatientDto> resp =
                    patientClient.getPatientById(pid);
            if (resp.getStatusCode().is2xxSuccessful() && resp.getBody() != null) {
                return resp.getBody();
            }
            throw new ResourceNotFoundException("Patient not found: " + pid);
        } catch (Exception e) {
            throw new ExternalServiceException("Unable to fetch patient", e);
        }
    }

    /** Remote call to staff service */
    private com.healthcareplatform.PharmacyService.dto.StaffDto fetchStaff(Long did) {
        try {
            ResponseEntity<com.healthcareplatform.PharmacyService.dto.StaffDto> resp =
                    staffClient.getAvailability(did);
            if (resp.getStatusCode().is2xxSuccessful() && resp.getBody() != null) {
                return resp.getBody();
            }
            throw new ResourceNotFoundException("Doctor not found: " + did);
        } catch (Exception e) {
            throw new ExternalServiceException("Unable to fetch staff", e);
        }
    }

    /** Map entity → DTO (including items) */
    private PrescriptionResponse mapToDto(Prescription p) {
        List<PrescriptionItemResponse> items = itemRepository
                .findByPrescriptionId(p.getId())
                .stream()
                .map(i -> new PrescriptionItemResponse())
                .collect(Collectors.toList());

        return new PrescriptionResponse(
                p.getId(),
                p.getPatientId(),
                p.getPatientFirstName(),
                p.getPatientSecondName(),
                p.getPatientDateOfBirth(),
                p.getPatientGender(),
                p.getPatientContactInfo(),
                p.getDoctorId(),
                p.getDoctorFirstName(),
                p.getDoctorLastName(),
                p.getDoctorRole(),
                p.getDateIssued(),
                p.getStatus(),
                p.getNotes(),
                items,
                p.getCreatedAt()
        );
    }

    /** Map DTO → entity (master data only) */
    private Prescription mapToEntity(PrescriptionRequest dto,
                                     com.healthcareplatform.PharmacyService.dto.PatientDto pat,
                                     com.healthcareplatform.PharmacyService.dto.StaffDto staff) {
        Prescription p = new Prescription();
        p.setPatientId(pat.getId());
        p.setPatientFirstName(pat.getFirstName());
        p.setPatientSecondName(pat.getLastName());
        p.setPatientDateOfBirth(pat.getDateOfBirth().atStartOfDay());
        p.setPatientGender(pat.getGender());
        p.setPatientContactInfo(pat.getContactInfo());

        p.setDoctorId(staff.getDoctorId());
        p.setDoctorFirstName(staff.getDoctorFirstName());
        p.setDoctorLastName(staff.getDoctorLastName());
        p.setDoctorRole(staff.getDoctorRole());

        p.setNotes(dto.getNotes());
        return p;
    }

}
