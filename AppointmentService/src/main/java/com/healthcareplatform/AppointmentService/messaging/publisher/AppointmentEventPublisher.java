package com.healthcareplatform.AppointmentService.messaging.publisher;

import com.healthcareplatform.AppointmentService.config.RabbitMQConfig;
import com.healthcareplatform.AppointmentService.dto.AppointmentDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class AppointmentEventPublisher {
    private final RabbitTemplate rabbitTemplate;

    public AppointmentEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Publish AppointmentCreated event after booking.
     */
    public void publishAppointmentCreated(AppointmentDto appointment) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.APPOINTMENT_CREATED_QUEUE, appointment);
    }

    /**
     * Publish AppointmentUpdated event after modifications.
     */
    public void publishAppointmentUpdated(AppointmentDto appointment) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.APPOINTMENT_UPDATED_QUEUE, appointment);
    }

    /**
     * Publish AppointmentCancelled event when an appointment is cancelled.
     */
    public void publishAppointmentCancelled(Long appointmentId) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.APPOINTMENT_CANCELLED_QUEUE, appointmentId);
    }
}