package com.healthcareplatform.AppointmentService.appointment;

import com.healthcareplatform.AppointmentService.dto.AppointmentRequest;
import com.healthcareplatform.AppointmentService.dto.AppointmentResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing patient appointments.
 */
@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentController {

    @Autowired
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    /**
     * Retrieve a list of all appointments.
     *
     * @return ResponseEntity containing a list of AppointmentResponse objects and HTTP 200 status.
     */
    @GetMapping
    public ResponseEntity<List<AppointmentResponse>> getAllAppointments() {
        List<AppointmentResponse> appointments = appointmentService.getAllAppointments();
        return ResponseEntity.ok(appointments);
    }

    /**
     * Retrieve details for a specific appointment by ID.
     *
     * @param Id Unique identifier of the appointment (path variable)
     * @return ResponseEntity containing AppointmentResponse and HTTP 200 status if found;
     *         otherwise exception is propagated (e.g., 404 Not Found).
     */
    @GetMapping("/{Id}")
    public ResponseEntity<AppointmentResponse> getAppointmentById(@PathVariable Long Id) {
        AppointmentResponse response = appointmentService.getAppointmentById(Id);
        return ResponseEntity.ok(response);
    }

    /**
     * Create a new appointment.
     *
     * @param appointmentRequest Payload containing appointment data (validated request body)
     * @return ResponseEntity containing created AppointmentResponse and HTTP 201 status.
     */
    @PostMapping
    public ResponseEntity<AppointmentResponse> createAppointment(@Valid @RequestBody AppointmentRequest appointmentRequest) {
        AppointmentResponse created = appointmentService.createAppointment(appointmentRequest);
        return ResponseEntity.status(201).body(created);
    }

    /**
     * Update an existing appointment's details.
     *
     * @param Id Unique identifier of the appointment (path variable)
     * @param appointmentRequest Payload containing updated data (validated request body)
     * @return ResponseEntity containing updated AppointmentResponse and HTTP 200 status.
     */
    @PutMapping("/{Id}")
    public ResponseEntity<AppointmentResponse> updateAppointment(
            @PathVariable Long Id,
            @Valid @RequestBody AppointmentRequest appointmentRequest) {
        AppointmentResponse updated = appointmentService.updateAppointment(Id, appointmentRequest);
        return ResponseEntity.ok(updated);
    }

    /**
     * delete an appointment by ID.
     *
     * @param Id Unique identifier of the appointment (path variable)
     * @return ResponseEntity with HTTP 204 No Content on successful cancellation.
     */
    @DeleteMapping("/{Id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long Id) {
        appointmentService.deleteAppointment(Id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Cancel an appointment by ID.
     *
     * @param Id Unique identifier of the appointment (path variable)
     * @return ResponseEntity with HTTP 204 No Content on successful cancellation.
     */
    @PostMapping("/{Id}/cancel")
    public ResponseEntity<Void> cancelAppointment(@PathVariable Long Id) {
        appointmentService.cancelAppointment(Id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Reschedule an appointment by ID.
     * <p>
     *
     * @param Id Unique identifier of the appointment (path variable)
     * @return ResponseEntity with HTTP 204 No Content on successful cancellation.
     */
    @PostMapping("/{Id}/reschedule")
    public ResponseEntity<Void> rescheduleAppointment(@PathVariable Long Id) {
        appointmentService.rescheduleAppointment(Id);
        return ResponseEntity.noContent().build();
    }
}
