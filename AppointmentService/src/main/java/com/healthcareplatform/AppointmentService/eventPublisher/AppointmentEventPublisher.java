package com.healthcareplatform.AppointmentService.eventPublisher;

import com.healthcareplatform.AppointmentService.dto.AppointmentResponse;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.healthcareplatform.AppointmentService.config.RabbitMQConfig.*;

@Service
public class AppointmentEventPublisher {
    private final RabbitTemplate rabbitTemplate;

    public AppointmentEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Publish AppointmentCreated event after booking.
     */
    public void publishAppointmentCreated(AppointmentResponse appointment) {
        rabbitTemplate.convertAndSend(APPOINTMENT_CREATED_QUEUE, appointment);
    }

    /**
     * Publish AppointmentUpdated event after modifications.
     */
    public void publishAppointmentUpdated(AppointmentResponse appointment) {
        rabbitTemplate.convertAndSend(APPOINTMENT_UPDATED_QUEUE, appointment);
    }

    /**
     * Publish AppointmentCancelled event when an appointment is cancelled.
     */
    public void publishAppointmentCancelled(Long appointmentId) {
        rabbitTemplate.convertAndSend(APPOINTMENT_CANCELLED_QUEUE, appointmentId);
    }
}