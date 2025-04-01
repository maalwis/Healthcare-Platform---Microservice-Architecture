package com.healthcareplatform.AppointmentSchedulingService.service;

import com.healthcareplatform.AppointmentSchedulingService.dto.AppointmentResponse;

import java.util.List;

public interface AppointmentService {

    AppointmentResponse createAppointment();

    AppointmentResponse getAppointmentById(Long id);

    List<AppointmentResponse> getAllAppointments();

    AppointmentResponse updateAppointment(Long id);

    void deleteAppointment(Long id);
}