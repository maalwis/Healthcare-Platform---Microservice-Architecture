package com.healthcareplatform.AuditLoggingService.model;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Centralizes all audit events emitted by microservices for compliance and tracing.
 *
 * <p>In the “audit_logs” table:
 * <ul>
 *   <li><b>eventTime</b> (TIMESTAMPTZ) when the action occurred.</li>
 *   <li><b>serviceName</b> indicating which microservice generated the event.</li>
 *   <li><b>entityName</b> and <b>entityId</b> to identify the affected resource.</li>
 *   <li><b>action</b> (“CREATE,” “UPDATE,” “DELETE”) describing the event type.</li>
 *   <li><b>username</b> of the actor who triggered the event for accountability.</li>
 *   <li><b>details</b> String containing record diffs or full payloads for forensic analysis.</li>
 * </ul>
 *
 * <p>This audit model ensures a tamper-evident log of all critical operations across services.
 */


@Entity
@Table(name = "audit_logs")

public class AuditLog {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "event_time", nullable = false)
    private OffsetDateTime eventTime;

    @Column(name = "service_name", length = 100, nullable = false)
    private String serviceName;

    @Column(name = "entity_name", length = 100)
    private String entityName;

    @Column(name = "entity_id")
    private UUID entityId;

    @Column(length = 50, nullable = false)
    private String action;

    @Column(length = 100)
    private String username;

    @Column(name = "details")
    private String details;
}