package com.healthcareplatform.AppointmentService.appointment;

import com.healthcareplatform.AppointmentService.dto.AppointmentRequest;
import com.healthcareplatform.AppointmentService.dto.AppointmentResponse;
import com.healthcareplatform.AppointmentService.exception.AppointmentNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for managing Appointments.
 */
@Service
public class AppointmentService {

    private static final String STATUS_SCHEDULED  = "SCHEDULED";
    private static final String STATUS_CANCELLED  = "CANCELLED";
    private static final String STATUS_RESCHEDULED = "RESCHEDULED";

    @Autowired
    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    /**
     * Retrieve all appointments.
     *
     * @return list of {@link AppointmentResponse}
     * @throws DataAccessException on DB error
     */
    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAllAppointments() {
        return appointmentRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieve a single appointment by its ID.
     *
     * @param appointmentId the appointment ID
     * @return {@link AppointmentResponse}
     * @throws AppointmentNotFoundException if not found
     * @throws DataAccessException          on DB error
     */
    @Transactional(readOnly = true)
    public AppointmentResponse getAppointmentById(Long appointmentId) {
        Appointment appt = getAppointmentByIdHelper(appointmentId);
        return mapToDto(appt);
    }

    /**
     * Create a new appointment with status = SCHEDULED.
     *
     * @param req validated {@link AppointmentRequest}
     * @return created {@link AppointmentResponse}
     * @throws DataAccessException on DB error
     */
    @Transactional
    public AppointmentResponse createAppointment(@Valid AppointmentRequest req) {
        Appointment appt = mapToEntity(req);
        appt.setStatus(STATUS_SCHEDULED);

        try {
            Appointment saved = appointmentRepository.save(appt);
            return mapToDto(saved);
        } catch (DataIntegrityViolationException ex) {
            // translate any constraint violation into a 409 if needed
            throw ex;
        }
    }

    /**
     * Update an existing appointment's scheduled time (and doctor/patient if changed).
     *
     * @param appointmentId the appointment ID
     * @param req           validated {@link AppointmentRequest}
     * @return updated {@link AppointmentResponse}
     * @throws AppointmentNotFoundException if not found
     * @throws DataAccessException          on DB error
     */
    @Transactional
    public AppointmentResponse updateAppointment(Long appointmentId,
                                                 @Valid AppointmentRequest req) {
        Appointment appt = getAppointmentByIdHelper(appointmentId);
        appt.setPatientId(req.getPatientId());
        appt.setDoctorId(req.getDoctorId());
        appt.setScheduledTime(req.getScheduledTime());
        // leave duration, reason, names unchanged

        Appointment saved = appointmentRepository.save(appt);
        return mapToDto(saved);
    }

    /**
     * Hard‐delete an appointment.
     *
     * @param appointmentId the appointment ID
     * @throws AppointmentNotFoundException if not found
     * @throws DataAccessException          on DB error
     */
    @Transactional
    public void deleteAppointment(Long appointmentId) {
        Appointment appt = getAppointmentByIdHelper(appointmentId);
        appointmentRepository.delete(appt);
    }

    /**
     * Cancel an appointment (sets status = CANCELLED).
     *
     * @param appointmentId the appointment ID
     * @throws AppointmentNotFoundException if not found
     * @throws DataAccessException          on DB error
     */
    @Transactional
    public void cancelAppointment(Long appointmentId) {
        Appointment appt = getAppointmentByIdHelper(appointmentId);
        appt.setStatus(STATUS_CANCELLED);
        appointmentRepository.save(appt);
    }

    /**
     * Reschedule an appointment (sets status = RESCHEDULED).
     * Note: if you need to update the scheduled time as part of rescheduling,
     * you might overload this method to accept a new time.
     *
     * @param appointmentId the appointment ID
     * @throws AppointmentNotFoundException if not found
     * @throws DataAccessException          on DB error
     */
    @Transactional
    public void rescheduleAppointment(Long appointmentId) {
        Appointment appt = getAppointmentByIdHelper(appointmentId);
        appt.setStatus(STATUS_RESCHEDULED);
        appointmentRepository.save(appt);
    }

    /**
     * Helper to load an Appointment or throw a 404.
     *
     * @param id appointment ID
     * @return the {@link Appointment}
     * @throws AppointmentNotFoundException if not found
     * @throws DataAccessException          on DB error
     */
    @Transactional(readOnly = true)
    private Appointment getAppointmentByIdHelper(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() ->
                        new AppointmentNotFoundException("Appointment not found with id: " + id));
    }

    /**
     * Map Appointment entity to DTO.
     */
    private AppointmentResponse mapToDto(Appointment appt) {
        return new AppointmentResponse(
                appt.getId(),
                appt.getPatientId(),
                appt.getDoctorId(),
                appt.getScheduledTime(),
                appt.getDurationMinutes(),
                appt.getStatus(),
                appt.getReason(),
                appt.getPatientName(),
                appt.getDoctorName(),
                appt.getCreatedAt()
        );
    }

    /**
     * Map DTO to a new Appointment entity.
     * Does not set status, duration, names, etc.
     */
    private Appointment mapToEntity(AppointmentRequest dto) {
        Appointment appointment = new Appointment();
        appointment.setPatientId(dto.getPatientId());
        appointment.setDoctorId(dto.getDoctorId());
        appointment.setScheduledTime(dto.getScheduledTime());
        return appointment;
    }
}
