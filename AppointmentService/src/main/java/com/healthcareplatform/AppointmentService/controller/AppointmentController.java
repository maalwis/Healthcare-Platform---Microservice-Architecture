package com.healthcareplatform.AppointmentService.controller;

import com.healthcareplatform.AppointmentService.dto.AppointmentDto;
import com.healthcareplatform.AppointmentService.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for managing patient appointments.
 */
@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    /**
     * Retrieve a list of all appointments.
     *
     * TODO: Delegate to AppointmentService to retrieve all appointments
     *
     * @return ResponseEntity containing a list of AppointmentDto objects and HTTP 200 status.
     */
    @GetMapping
    public ResponseEntity<List<AppointmentDto>> getAllAppointments() {
        List<AppointmentDto> appointments = appointmentService.getAllAppointments();
        return ResponseEntity.ok(appointments);
    }

    /**
     * Retrieve details for a specific appointment by ID.
     *
     * TODO: Delegate to AppointmentService to fetch appointment by ID
     *
     * @param appointmentId Unique identifier of the appointment (path variable)
     * @return ResponseEntity containing AppointmentDto and HTTP 200 status if found;
     *         otherwise exception is propagated (e.g., 404 Not Found).
     */
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDto> getAppointmentById(@PathVariable UUID appointmentId) {
        AppointmentDto appt = appointmentService.getAppointmentById(appointmentId);
        return ResponseEntity.ok(appt);
    }

    /**
     * Create a new appointment.
     *
     * TODO: Delegate to AppointmentService to create an appointment
     *
     * @param appointment Payload containing appointment data (validated request body)
     * @return ResponseEntity containing created AppointmentDto and HTTP 201 status.
     */
    @PostMapping
    public ResponseEntity<AppointmentDto> createAppointment(@Valid @RequestBody AppointmentDto appointment) {
        AppointmentDto created = appointmentService.createAppointment(appointment);
        return ResponseEntity.status(201).body(created);
    }

    /**
     * Update an existing appointment's details.
     *
     * TODO: Delegate to AppointmentService to update appointment details
     *
     * @param appointmentId Unique identifier of the appointment (path variable)
     * @param appointmentDto Payload containing updated data (validated request body)
     * @return ResponseEntity containing updated AppointmentDto and HTTP 200 status.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDto> updateAppointment(
            @PathVariable UUID appointmentId,
            @Valid @RequestBody AppointmentDto appointmentDto) {
        AppointmentDto updated = appointmentService.updateAppointment(appointmentId, appointmentDto);
        return ResponseEntity.ok(updated);
    }

    /**
     * delete an appointment by ID.
     *
     * TODO: Delegate to AppointmentService to delete appointment
     *
     * @param appointmentId Unique identifier of the appointment (path variable)
     * @return ResponseEntity with HTTP 204 No Content on successful cancellation.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable UUID appointmentId) {
        appointmentService.deleteAppointment(appointmentId);
        return ResponseEntity.noContent().build();
    }/**
     * Cancel an appointment by ID.
     *
     * TODO: Delegate to AppointmentService to cancel appointment
     *
     * @param appointmentId Unique identifier of the appointment (path variable)
     * @return ResponseEntity with HTTP 204 No Content on successful cancellation.
     */
    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelAppointment(@PathVariable UUID appointmentId) {
        appointmentService.cancelAppointment(appointmentId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Reschedule an appointment by ID.
     * <p>
     * TODO: Delegate to AppointmentService to reschedule appointment
     *
     * @param appointmentId Unique identifier of the appointment (path variable)
     * @return ResponseEntity with HTTP 204 No Content on successful cancellation.
     */
    @PostMapping("/{id}/reschedule")
    public ResponseEntity<Void> rescheduleAppointment(@PathVariable UUID appointmentId) {
        appointmentService.rescheduleAppointment(appointmentId);
        return ResponseEntity.noContent().build();
    }
}
