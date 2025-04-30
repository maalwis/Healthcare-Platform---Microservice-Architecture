package com.healthcareplatform.NotificationService.service;

import com.healthcareplatform.NotificationService.dto.NotificationDto;
import jakarta.validation.Valid;

import java.util.List;

public interface NotificationService {
    List<NotificationDto> getAllNotifications();

    NotificationDto sendNotification(@Valid NotificationDto notification);
}
