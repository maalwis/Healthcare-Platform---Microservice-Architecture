package com.healthcareplatform.NotificationService.controller;

import com.healthcareplatform.NotificationService.dto.NotificationDto;
import com.healthcareplatform.NotificationService.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * REST controller for managing notifications.
 */
@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * Retrieve a list of all notifications.
     *
     * TODO: Delegate to NotificationService to fetch all notifications
     *
     * @return ResponseEntity containing a list of NotificationDto objects and HTTP 200 status.
     */
    @GetMapping
    public ResponseEntity<List<NotificationDto>> getAllNotifications() {
        List<NotificationDto> notifications = notificationService.getAllNotifications();
        return ResponseEntity.ok(notifications);
    }

    /**
     * Send a new notification.
     *
     * TODO: Delegate to NotificationService to create and dispatch notification
     *
     * @param notification Payload containing notification data (validated request body)
     * @return ResponseEntity containing created NotificationDto and HTTP 201 status.
     */
    @PostMapping
    public ResponseEntity<NotificationDto> sendNotification(@Valid @RequestBody NotificationDto notification) {
        NotificationDto created = notificationService.sendNotification(notification);
        return ResponseEntity.status(201).body(created);
    }
}
