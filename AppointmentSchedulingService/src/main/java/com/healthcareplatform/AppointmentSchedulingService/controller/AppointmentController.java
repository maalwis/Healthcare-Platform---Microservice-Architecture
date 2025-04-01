package com.healthcareplatform.AppointmentSchedulingService.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentController {

    @PostMapping
    public ResponseEntity<?> createAppointment() {
        try {
            // Delegate to AppointmentService to create an appointment
            return ResponseEntity.status(HttpStatus.CREATED).body("Appointment created successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create appointment.");
        }
    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<?> getAppointmentById(@PathVariable Long appointmentId) {
        try {
            // Delegate to AppointmentService to retrieve appointment details
            return ResponseEntity.ok("Appointment details for id " + appointmentId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Appointment not found.");
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllAppointments() {
        try {
            // Delegate to AppointmentService to retrieve all appointments
            return ResponseEntity.ok("List of all appointments.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve appointments.");
        }
    }

    @PutMapping("/{appointmentId}")
    public ResponseEntity<?> updateAppointment(@PathVariable Long appointmentId) {
        try {
            // Delegate to AppointmentService to update the appointment
            return ResponseEntity.ok("Appointment updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Appointment update failed.");
        }
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<?> deleteAppointment(@PathVariable Long appointmentId) {
        try {
            // Delegate to AppointmentService to delete the appointment
            return ResponseEntity.ok("Appointment deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Appointment deletion failed.");
        }
    }
}