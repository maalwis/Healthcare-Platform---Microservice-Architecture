package com.healthcareplatform.AnalyticsService.analytic;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnalyticsRepository extends JpaRepository<AnalyticsEvent, Long> {

    List<AnalyticsEvent> findByEventType
            (@NotNull(message = "eventType must not be null") String eventType);
}
