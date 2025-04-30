package com.healthcareplatform.AuditLoggingService.service;

import com.healthcareplatform.AuditLoggingService.dto.AuditLogDto;

import java.util.List;
import java.util.UUID;

public interface AuditLoggingService {
    List<AuditLogDto> getAllAuditLogs();

    AuditLogDto getAuditLogById(UUID logId);
}
