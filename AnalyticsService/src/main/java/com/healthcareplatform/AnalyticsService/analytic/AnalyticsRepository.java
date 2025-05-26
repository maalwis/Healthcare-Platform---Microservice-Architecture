package com.healthcareplatform.AnalyticsService.analytic;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface AnalyticsRepository extends JpaRepository<AnalyticsEvent, UUID> {

    List<AnalyticsEvent> findByEventType
            (@NotNull(message = "eventType must not be null") String eventType);
}
