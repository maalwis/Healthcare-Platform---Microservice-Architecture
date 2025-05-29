package com.healthcareplatform.PharmacyService.staffClient;

import com.healthcareplatform.PharmacyService.dto.StaffDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "StaffService")
public interface StaffClient {

    /**
     * Calls the StaffService to fetch availability for a given staff member and date.
     *
     * @param staffId the staff member’s ID, bound from the {stuffId} path variable.
     *                Explicit naming aligns with the URI template and triggers
     *                PathVariableMethodArgumentResolver for conversion.
     * @return a ResponseEntity containing the StaffAvailabilityDto.
     */
    @GetMapping("/api/v1/private/stuff/{stuffId}/availability")
    ResponseEntity<StaffDto> getAvailability(@PathVariable("stuffId") Long staffId);
}
