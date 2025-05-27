package com.healthcareplatform.AuditLoggingService.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogResponse {
    private Long id;
    private LocalDateTime eventTime;
    private String serviceName;
    private String entityName;
    private Long entityId;
    private String action;
    private String username;
    private String details;
}
