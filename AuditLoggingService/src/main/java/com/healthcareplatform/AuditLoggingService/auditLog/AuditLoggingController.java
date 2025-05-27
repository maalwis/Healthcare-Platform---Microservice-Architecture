package com.healthcareplatform.AuditLoggingService.auditLog;

import com.healthcareplatform.AuditLoggingService.dto.AuditLogResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for retrieving audit log entries.
 */
@RestController
@RequestMapping("/api/v1/audit/logs")
public class AuditLoggingController {

    @Autowired
    private final AuditLoggingService auditLoggingService;

    public AuditLoggingController(AuditLoggingService auditLoggingService) {
        this.auditLoggingService = auditLoggingService;
    }

    /**
     * Retrieve a list of all audit log entries.
     *
     * @return ResponseEntity containing a list of AuditLogResponse objects and HTTP 200 status.
     */
    @GetMapping
    public ResponseEntity<List<AuditLogResponse>> getAllAuditLogs() {
        List<AuditLogResponse> logs = auditLoggingService.getAllAuditLogs();
        return ResponseEntity.ok(logs);
    }

    /**
     * Retrieve details for a specific audit log entry by ID.
     *
     * @param Id Unique identifier of the audit log entry (path variable)
     * @return ResponseEntity containing AuditLogResponse and HTTP 200 status if found;
     *         otherwise exception is propagated (e.g., 404 Not Found).
     */
    @GetMapping("/{Id}")
    public ResponseEntity<AuditLogResponse> getAuditLogById(@PathVariable Long Id) {
        AuditLogResponse log = auditLoggingService.getAuditLogById(Id);
        return ResponseEntity.ok(log);
    }
}