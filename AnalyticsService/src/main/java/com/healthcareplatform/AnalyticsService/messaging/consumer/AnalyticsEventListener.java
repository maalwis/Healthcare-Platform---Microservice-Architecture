package com.healthcareplatform.AnalyticsService.messaging.consumer;

import com.healthcareplatform.AnalyticsService.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Ingests all domain events into analytics data warehouse for dashboards.
 */
@Component
public class AnalyticsEventListener {

    public AnalyticsEventListener() {}

    @RabbitListener(queues = {
            RabbitMQConfig.PATIENT_REGISTERED_QUEUE,
            RabbitMQConfig.PATIENT_UPDATED_QUEUE,
            RabbitMQConfig.APPOINTMENT_CREATED_QUEUE,
            RabbitMQConfig.APPOINTMENT_UPDATED_QUEUE,
            RabbitMQConfig.APPOINTMENT_CANCELLED_QUEUE,
            RabbitMQConfig.STAFF_ONBOARDED_QUEUE,
            RabbitMQConfig.STAFF_UPDATED_QUEUE,
            RabbitMQConfig.PRESCRIPTION_FILLED_QUEUE,
            RabbitMQConfig.MEDICATION_DISPENSED_QUEUE,
            RabbitMQConfig.STOCK_LOW_QUEUE,
            RabbitMQConfig.STOCK_REPLENISHED_QUEUE,
            RabbitMQConfig.INVOICE_GENERATED_QUEUE,
            RabbitMQConfig.CLAIM_SUBMITTED_QUEUE,
            RabbitMQConfig.CLAIM_DENIED_QUEUE
    })
    public void onAnyEvent(Object rawEvent) {

    }
}