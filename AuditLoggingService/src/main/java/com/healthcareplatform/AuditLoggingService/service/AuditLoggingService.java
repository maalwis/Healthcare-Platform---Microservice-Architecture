package com.healthcareplatform.AuditLoggingService.service;

import com.healthcareplatform.AuditLoggingService.dto.AuditLogDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AuditLoggingService {
    public List<AuditLogDto> getAllAuditLogs(){
        return null;
    };

    public AuditLogDto getAuditLogById(UUID logId){
        return null;
    };
}
