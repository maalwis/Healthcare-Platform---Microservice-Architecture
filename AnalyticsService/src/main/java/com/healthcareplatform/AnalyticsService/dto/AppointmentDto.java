package com.healthcareplatform.AnalyticsService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDto {
    private UUID id;
    private UUID patientId;
    private UUID doctorId;
    private LocalDateTime scheduledTime;
    private Integer durationMinutes;
    private String status;
    private String reason;
    private String patientName;
    private String doctorName;
    private LocalDateTime createdAt;

}
