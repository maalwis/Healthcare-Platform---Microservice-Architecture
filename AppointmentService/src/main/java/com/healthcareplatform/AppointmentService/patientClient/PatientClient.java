package com.healthcareplatform.AppointmentService.patientClient;

import com.healthcareplatform.AppointmentService.dto.PatientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "PatientService")
public interface PatientClient {

    /**
     * Calls the PatientService to validate and fetch patient details by ID.
     *
     * @param patientId the patient’s unique identifier, bound from the
     *                  {patientId} path variable. Explicit naming and use of
     *                  Long type ensure proper handling by Spring’s resolver
     *                  and converter.
     * @return a ResponseEntity containing the PatientDto.
     */
    @GetMapping("/api/v1/private/patient/{patientId}")
    ResponseEntity<PatientDto> getPatientById(@PathVariable("patientId") Long patientId);
}
