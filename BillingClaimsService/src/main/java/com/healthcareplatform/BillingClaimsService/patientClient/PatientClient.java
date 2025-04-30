package com.healthcareplatform.BillingClaimsService.patientClient;

import com.healthcareplatform.BillingClaimsService.dto.PatientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "PatientService")
public interface PatientClient {

    /**
     * Calls the PatientService to retrieve and validate a patient by ID.
     *
     * @param patientId the patient’s unique identifier, bound from the {patientId} path variable.
     *                  Explicitly naming the placeholder ensures Spring’s
     *                  PathVariableMethodArgumentResolver enforces presence and
     *                  uses ConversionService for type conversion.
     * @return a ResponseEntity containing the PatientDto on success.
     */
    @GetMapping("/api/v1/private/patient/{patientId}")
    ResponseEntity<PatientDto> getPatientById(@PathVariable("patientId") Long patientId);
}
