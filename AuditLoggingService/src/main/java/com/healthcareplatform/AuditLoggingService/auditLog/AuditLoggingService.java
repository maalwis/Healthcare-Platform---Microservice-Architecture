package com.healthcareplatform.AuditLoggingService.auditLog;

import com.healthcareplatform.AuditLoggingService.dto.AuditLogResponse;
import com.healthcareplatform.AuditLoggingService.exception.AuditLogNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for retrieving AuditLog entries.
 */
@Service
public class AuditLoggingService {

    private final AuditLogRepository auditLogRepository;

    public AuditLoggingService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    /**
     * Retrieve all audit log entries.
     *
     * <p>Runs within a read-only transaction for performance.</p>
     *
     * @return list of {@link AuditLogResponse}
     * @throws DataAccessException on DB error
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<AuditLogResponse> getAllAuditLogs() {
        return auditLogRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieve a single audit log entry by its ID.
     *
     * @param logId the audit log ID
     * @return the matching {@link AuditLogResponse}
     * @throws AuditLogNotFoundException if no entry with the given ID exists
     * @throws DataAccessException       on DB error
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public AuditLogResponse getAuditLogById(Long logId) {
        AuditLog entry = getAuditLogByIdHelper(logId);
        return mapToDto(entry);
    }

    /**
     * Helper to load an AuditLog or throw a 404.
     *
     * @param id the audit log ID
     * @return the {@link AuditLog} entity
     * @throws AuditLogNotFoundException if not found
     */
    private AuditLog getAuditLogByIdHelper(Long id) {
        return auditLogRepository.findById(id)
                .orElseThrow(() ->
                        new AuditLogNotFoundException("Audit log not found with id: " + id));
    }

    /**
     * Map an {@link AuditLog} entity to its DTO.
     */
    private AuditLogResponse mapToDto(AuditLog log) {
        AuditLogResponse dto = new AuditLogResponse();
        dto.setId(log.getId());
        dto.setEventTime(log.getEventTime());
        dto.setServiceName(log.getServiceName());
        dto.setEntityName(log.getEntityName());
        dto.setEntityId(log.getEntityId());
        dto.setAction(log.getAction());
        dto.setUsername(log.getUsername());
        dto.setDetails(log.getDetails());
        return dto;
    }
}
