package com.healthcareplatform.AuditLoggingService.exception;

import java.time.Instant;

/**
 * Generic error response sent to clients when an exception occurs.
 */
public class ErrorResponse {
    private Instant timestamp = Instant.now();
    private int status;
    private String error;
    private String message;
    private String path;        // the request path

    // Constructors
    public ErrorResponse() {}

    public ErrorResponse(int status, String error, String message, String path) {
        this.status  = status;
        this.error   = error;
        this.message = message;
        this.path    = path;
    }

}
