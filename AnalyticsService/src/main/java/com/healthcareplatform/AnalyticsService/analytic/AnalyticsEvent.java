package com.healthcareplatform.AnalyticsService.analytic;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Serves as the landing table for all domain events ingested for reporting and analytics.
 *
 * <p>Within the “analytics_events” table:
 * <ul>
 *   <li><b>eventType</b> names the event (“appointment.created”...).</li>
 *   <li><b>eventTime</b> when the event was published.</li>
 *   <li><b>data</b> String containing the full event payload, including denormalized fields
 *       required for slice-and-dice analytics.</li>
 * </ul>
 *
 * <p>By storing all events in a single, indexed table, the system supports flexible
 * BI queries and dashboard generation without impacting transactional services.
 */

@Entity
@Table(name = "analytics_events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticsEvent {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "event_type", length = 100, nullable = false)
    private String eventType;

    @Column(name = "event_time", nullable = false)
    private LocalDateTime eventTime;


}