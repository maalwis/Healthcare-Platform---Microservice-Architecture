package com.healthcareplatform.AnalyticsService.model;

import jakarta.persistence.*;

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
public class AnalyticsEvent {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "event_type", length = 100, nullable = false)
    private String eventType;

    @Column(name = "event_time", nullable = false)
    private OffsetDateTime eventTime;

    @Column(name = "data", nullable = false)
    private String data;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public OffsetDateTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(OffsetDateTime eventTime) {
        this.eventTime = eventTime;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}