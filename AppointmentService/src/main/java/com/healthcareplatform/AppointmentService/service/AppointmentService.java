package com.healthcareplatform.AppointmentService.service;

import com.healthcareplatform.AppointmentService.dto.AppointmentDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public class AppointmentService {
    public List<AppointmentDto> getAllAppointments(){
        return null;
    };

    public AppointmentDto getAppointmentById(UUID appointmentId){
        return null;
    };

    public AppointmentDto createAppointment(@Valid AppointmentDto appointment){
        return null;
    };

    public AppointmentDto updateAppointment(UUID appointmentId, @Valid AppointmentDto appointmentDto){
        return null;
    };

    public void cancelAppointment(UUID appointmentId){
        return;
    };
}
