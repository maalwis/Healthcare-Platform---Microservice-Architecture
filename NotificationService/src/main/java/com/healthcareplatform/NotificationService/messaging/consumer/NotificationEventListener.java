package com.healthcareplatform.NotificationService.messaging.consumer;

import com.healthcareplatform.NotificationService.config.RabbitMQConfig;
import com.healthcareplatform.NotificationService.dto.AppointmentDto;
import com.healthcareplatform.NotificationService.dto.ClaimDto;
import com.healthcareplatform.NotificationService.dto.InvoiceDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Listens for domain events to send notifications to patients.
 */
@Component
public class NotificationEventListener {

    public NotificationEventListener() {}

    @RabbitListener(queues = RabbitMQConfig.APPOINTMENT_CREATED_QUEUE)
    public void onAppointmentCreated(AppointmentDto appointment) {
        // Send SMS/email confirmation for new appointment.

    }

    @RabbitListener(queues = RabbitMQConfig.APPOINTMENT_UPDATED_QUEUE)
    public void onAppointmentUpdated(AppointmentDto appointment) {
        // Send updates when appointment details change.

    }

    @RabbitListener(queues = RabbitMQConfig.APPOINTMENT_CANCELLED_QUEUE)
    public void onAppointmentCancelled(Long appointmentId) {
        // Notify patient and provider of cancellation.

    }

    @RabbitListener(queues = RabbitMQConfig.INVOICE_GENERATED_QUEUE)
    public void onInvoiceGenerated(InvoiceDto invoice) {
        // Alert patient when invoice is generated.
    }

    @RabbitListener(queues = RabbitMQConfig.CLAIM_DENIED_QUEUE)
    public void onClaimDenied(ClaimDto claim) {
        // Notify patient if a claim is denied so they can follow up.

    }
}