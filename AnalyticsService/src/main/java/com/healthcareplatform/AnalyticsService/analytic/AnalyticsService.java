package com.healthcareplatform.AnalyticsService.analytic;

import com.healthcareplatform.AnalyticsService.dto.AnalyticsEventDto;
import com.healthcareplatform.AnalyticsService.dto.AnalyticsReportRequest;
import com.healthcareplatform.AnalyticsService.dto.AnalyticsReportResponse;
import com.healthcareplatform.AnalyticsService.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {

    @Autowired
    private final AnalyticsRepository analyticsRepository;

    public AnalyticsService(AnalyticsRepository analyticsRepository) {
        this.analyticsRepository = analyticsRepository;
    }

    /**
     * Retrieve all analytics events from the database.
     * <p>
     * Runs within a read-only transaction for performance optimization.
     *
     * @return List of AnalyticsReportResponse representing all events
     * @throws DataAccessException if a database access error occurs
     */
    @Transactional(readOnly = true)
    public List<AnalyticsReportResponse> getAllReports() {
        List<AnalyticsEvent> events = analyticsRepository.findAll();
        return events.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieve a specific analytics report/event by its unique ID.
     *
     * @param reportId UUID of the report to retrieve
     * @return the corresponding AnalyticsReportResponse
     * @throws ResourceNotFoundException if no matching event is found
     * @throws DataAccessException       on DB errors
     */
    @Transactional(readOnly = true)
    public AnalyticsReportResponse getReportById(UUID reportId) {
        AnalyticsEvent event = analyticsRepository.findById(reportId)
                .orElseThrow(() -> new ResourceNotFoundException("Report not found with ID: " + reportId));
        return mapToDto(event);
    }

    /**
     * Generate a report based on filtering criteria provided in the request.
     *
     * @param request validated request containing filter parameters like eventType, createdAt
     * @return Aggregated report wrapped in AnalyticsReportResponse
     * @throws DataAccessException on database errors
     */
    @Transactional(readOnly = true)
    public List<AnalyticsReportResponse> generateReport(@Valid AnalyticsReportRequest request) {
        List<AnalyticsEvent> filteredEvents = analyticsRepository
                .findByEventType(request.getEventType());

        List<AnalyticsReportResponse> response = filteredEvents.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        return response;
    }

    /**
     * Create a new AnalyticsEventDto record in the database.
     * <p>
     * Maps the DTO to an entity and persists it.
     *
     * @param evt the validated request DTO
     * @throws DataAccessException on DB errors
     */
    @Transactional
    public void createAnalyticEvent(@Valid AnalyticsEventDto evt) {
        AnalyticsEvent analyticsEvent = mapToEntity(evt);
        try {
            analyticsRepository.save(analyticsEvent);
        } catch (DataIntegrityViolationException ex) {
            String msg = ex.getRootCause() != null
                    ? ex.getRootCause().getMessage()
                    : ex.getMessage();
            throw ex;  // fallback to 500
        }
    }

    /**
     * Maps a creation AnalyticsEventDTO to a new AnalyticsEvent entity.
     *
     * @param evt the incoming request containing AnalyticsEvent details
     * @return a new {@link AnalyticsEvent} entity ready for persistence
     */
    private AnalyticsEvent mapToEntity(AnalyticsEventDto evt) {
        AnalyticsEvent analyticsEvent = new AnalyticsEvent();
        analyticsEvent.setEventType(evt.getEventType());
        analyticsEvent.setEventTime(evt.getEventTime());
        return analyticsEvent;
    }

    /**
     * Maps an {@link AnalyticsEvent} entity to a {@link AnalyticsReportResponse} DTO.
     *
     * @param event the entity to convert
     * @return the DTO representation
     */
    private AnalyticsReportResponse mapToDto(AnalyticsEvent event) {
        AnalyticsReportResponse response = new AnalyticsReportResponse();
        response.setId(event.getId());
        response.setEventType(event.getEventType());
        response.setCreatedAt(event.getEventTime());
        return response;
    }
}
