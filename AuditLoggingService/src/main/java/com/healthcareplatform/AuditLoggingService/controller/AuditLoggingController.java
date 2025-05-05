package com.healthcareplatform.AuditLoggingService.controller;

import com.healthcareplatform.AuditLoggingService.dto.AuditLogDto;
import com.healthcareplatform.AuditLoggingService.service.AuditLoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
     * TODO: Delegate to AuditLoggingService to fetch all logs
     *
     * @return ResponseEntity containing a list of AuditLogDto objects and HTTP 200 status.
     */
    @GetMapping
    public ResponseEntity<List<AuditLogDto>> getAllAuditLogs() {
        List<AuditLogDto> logs = auditLoggingService.getAllAuditLogs();
        return ResponseEntity.ok(logs);
    }

    /**
     * Retrieve details for a specific audit log entry by ID.
     *
     * TODO: Delegate to AuditLoggingService to fetch log by ID
     *
     * @param logId Unique identifier of the audit log entry (path variable)
     * @return ResponseEntity containing AuditLogDto and HTTP 200 status if found;
     *         otherwise exception is propagated (e.g., 404 Not Found).
     */
    @GetMapping("/{id}")
    public ResponseEntity<AuditLogDto> getAuditLogById(@PathVariable UUID logId) {
        AuditLogDto log = auditLoggingService.getAuditLogById(logId);
        return ResponseEntity.ok(log);
    }
}