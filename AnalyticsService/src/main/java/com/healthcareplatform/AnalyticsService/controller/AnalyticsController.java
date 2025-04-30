package com.healthcareplatform.AnalyticsService.controller;

import com.healthcareplatform.AnalyticsService.dto.AnalyticsReportDto;
import com.healthcareplatform.AnalyticsService.dto.AnalyticsRequestDto;
import com.healthcareplatform.AnalyticsService.service.AnalyticsService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for analytics reporting and dashboards.
 */
@RestController
@RequestMapping("/api/v1/analytics/reports")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    /**
     * Retrieve a list of all analytics reports.
     *
     * TODO: Delegate to AnalyticsService to fetch all reports
     *
     * @return ResponseEntity containing a list of AnalyticsReportDto objects and HTTP 200 status.
     */
    @GetMapping
    public ResponseEntity<List<AnalyticsReportDto>> getAllReports() {
        List<AnalyticsReportDto> reports = analyticsService.getAllReports();
        return ResponseEntity.ok(reports);
    }

    /**
     * Retrieve a specific analytics report by ID.
     *
     * TODO: Delegate to AnalyticsService to fetch report by ID
     *
     * @param reportId Unique identifier of the report (path variable)
     * @return ResponseEntity containing AnalyticsReportDto and HTTP 200 status if found;
     *         otherwise exception is propagated.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AnalyticsReportDto> getReportById(@PathVariable UUID reportId) {
        AnalyticsReportDto report = analyticsService.getReportById(reportId);
        return ResponseEntity.ok(report);
    }

    /**
     * Generate a new analytics report based on request criteria.
     *
     * TODO: Delegate to AnalyticsService to create a new report
     *
     * @param request Payload containing report criteria (validated request body)
     * @return ResponseEntity containing created AnalyticsReportDto and HTTP 201 status.
     */
    @PostMapping
    public ResponseEntity<AnalyticsReportDto> generateReport(@Valid @RequestBody AnalyticsRequestDto request) {
        AnalyticsReportDto created = analyticsService.generateReport(request);
        return ResponseEntity.status(201).body(created);
    }
}