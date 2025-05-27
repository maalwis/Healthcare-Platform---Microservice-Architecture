package com.healthcareplatform.AnalyticsService.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * DTO representing a request to generate an analytics report.
 * <p>
 * This object encapsulates filters such as event type and time range.
 * when generating reports.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticsReportRequest {

    @NotNull(message = "eventType must not be null")
    private String eventType;

    @NotNull(message = "createdAt must not be null")
    private LocalDateTime createdAt;
}
