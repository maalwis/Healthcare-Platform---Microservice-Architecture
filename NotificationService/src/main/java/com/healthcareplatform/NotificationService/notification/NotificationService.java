package com.healthcareplatform.NotificationService.notification;

import com.healthcareplatform.NotificationService.dto.NotificationDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    public List<NotificationDto> getAllNotifications(){
        return null;
    };

    public NotificationDto sendNotification(@Valid NotificationDto notification){
        return null;
    };
}
