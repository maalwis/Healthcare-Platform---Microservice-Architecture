package com.healthcareplatform.StaffService.messaging.publisher;

import com.healthcareplatform.StaffService.config.RabbitMQConfig;
import com.healthcareplatform.StaffService.dto.StaffDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class StaffEventPublisher {
    private final RabbitTemplate rabbitTemplate;

    public StaffEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Publish StaffOnboarded event after new staff added.
     */
    public void publishStaffOnboarded(StaffDto staff) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.STAFF_ONBOARDED_QUEUE, staff);
    }

    /**
     * Publish StaffUpdated event after staff profile changes.
     */
    public void publishStaffUpdated(StaffDto staff) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.STAFF_UPDATED_QUEUE, staff);
    }
}