package com.healthcareplatform.AppointmentService.service;

import com.healthcareplatform.AppointmentService.dto.AppointmentDto;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface AppointmentService {
    List<AppointmentDto> getAllAppointments();

    AppointmentDto getAppointmentById(UUID appointmentId);

    AppointmentDto createAppointment(@Valid AppointmentDto appointment);

    AppointmentDto updateAppointment(UUID appointmentId, @Valid AppointmentDto appointmentDto);

    void cancelAppointment(UUID appointmentId);
}
