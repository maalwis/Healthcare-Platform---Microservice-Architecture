package com.healthcareplatform.AnalyticsService.service;

import com.healthcareplatform.AnalyticsService.dto.AnalyticsReportDto;
import com.healthcareplatform.AnalyticsService.dto.AnalyticsRequestDto;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface AnalyticsService {
    List<AnalyticsReportDto> getAllReports();

    AnalyticsReportDto getReportById(UUID reportId);

    AnalyticsReportDto generateReport(@Valid AnalyticsRequestDto request);
}
