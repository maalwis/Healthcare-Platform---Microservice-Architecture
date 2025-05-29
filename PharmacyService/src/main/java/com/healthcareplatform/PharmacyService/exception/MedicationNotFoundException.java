package com.healthcareplatform.PharmacyService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a requested resource is not found.
 * Results in HTTP 404 Not Found by default.
 */

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MedicationNotFoundException extends RuntimeException {
    public MedicationNotFoundException(String message) {
        super(message);
    }
}
