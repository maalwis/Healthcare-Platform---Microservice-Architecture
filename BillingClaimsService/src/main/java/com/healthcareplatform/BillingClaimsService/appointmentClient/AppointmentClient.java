package com.healthcareplatform.BillingClaimsService.appointmentClient;

import com.healthcareplatform.BillingClaimsService.dto.AppointmentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient("AppointmentService")
public interface AppointmentClient {

    /**
     * Calls the AppointmentService to retrieve appointment details by ID.
     *
     * @param appointmentId the appointment’s unique identifier, bound from the
     *                      {appointmentId} path variable. Explicit naming ensures
     *                      correct URI expansion and type conversion via
     *                      ConversionService.
     * @return a ResponseEntity containing the AppointmentDto.
     */
    @GetMapping("/api/v1/private/appointments/{appointmentId}")
    ResponseEntity<AppointmentDto> getAppointment(@PathVariable("appointmentId") Long appointmentId);
}
