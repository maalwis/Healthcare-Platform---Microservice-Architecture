package com.healthcareplatform.AppointmentService.staffClient;

import com.healthcareplatform.AppointmentService.dto.StaffAvailabilityDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "StaffService")
public interface StaffClient {

    /**
     * Calls the StaffService to fetch availability for a given staff member and date.
     *
     * @param staffId the staff member’s ID, bound from the {stuffId} path variable.
     *                Explicit naming aligns with the URI template and triggers
     *                PathVariableMethodArgumentResolver for conversion.
     * @param date    the target date, bound from the 'date' query parameter.
     *                The 'required' attribute (default true) enforces presence;
     *                use defaultValue or required=false if optional.
     * @return a ResponseEntity containing the StaffAvailabilityDto.
     */
    @GetMapping("/api/v1/private/stuff/{stuffId}/availability")
    ResponseEntity<StaffAvailabilityDto> getAvailability(@PathVariable("stuffId") Long staffId,
                                                         @RequestParam("date") String date);
}
