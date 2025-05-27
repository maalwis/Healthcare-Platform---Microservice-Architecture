package com.healthcareplatform.AnalyticsService.analytic;

import com.healthcareplatform.AnalyticsService.dto.AnalyticsReportResponse;
import com.healthcareplatform.AnalyticsService.dto.AnalyticsReportRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    /**
     * Retrieve a list of all analytics reports.
     *
     *
     * @return ResponseEntity containing a list of AnalyticsReportResponse objects and HTTP 200 status.
     */
    @GetMapping
    public ResponseEntity<List<AnalyticsReportResponse>> getAllReports() {
        List<AnalyticsReportResponse> reports = analyticsService.getAllReports();
        return ResponseEntity.ok(reports);
    }

    /**
     * Retrieve a specific analytics report by ID.
     *
     *
     * @param Id Unique identifier of the report (path variable)
     * @return ResponseEntity containing AnalyticsReportResponse and HTTP 200 status if found;
     *         otherwise exception is propagated.
     */
    @GetMapping("/{Id}")
    public ResponseEntity<AnalyticsReportResponse> getReportById(@PathVariable Long Id) {

        AnalyticsReportResponse report = analyticsService.getReportById(Id);
        return ResponseEntity.ok(report);
    }

    /**
     * Generate a new analytics report based on request criteria.
     *
     *
     * @param request Payload containing report criteria (validated request body)
     * @return ResponseEntity containing created AnalyticsReportResponse and HTTP 201 status.
     */
    @PostMapping
    public ResponseEntity<List<AnalyticsReportResponse>> generateReport(@Valid @RequestBody AnalyticsReportRequest request) {
        List<AnalyticsReportResponse> created = analyticsService.generateReport(request);
        return ResponseEntity.status(201).body(created);
    }
}