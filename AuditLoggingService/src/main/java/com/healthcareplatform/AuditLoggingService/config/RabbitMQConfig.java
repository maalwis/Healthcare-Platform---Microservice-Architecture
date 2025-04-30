package com.healthcareplatform.AuditLoggingService.config;


import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    // Queue names for domain events
    public static final String PATIENT_REGISTERED_QUEUE     = "patient.registered";
    public static final String PATIENT_UPDATED_QUEUE        = "patient.updated";
    public static final String APPOINTMENT_CREATED_QUEUE    = "appointment.created";
    public static final String APPOINTMENT_UPDATED_QUEUE    = "appointment.updated";
    public static final String APPOINTMENT_CANCELLED_QUEUE  = "appointment.cancelled";
    public static final String STAFF_ONBOARDED_QUEUE        = "staff.onboarded";
    public static final String STAFF_UPDATED_QUEUE          = "staff.updated";
    public static final String PRESCRIPTION_FILLED_QUEUE    = "prescription.filled";
    public static final String MEDICATION_DISPENSED_QUEUE   = "medication.dispensed";
    public static final String STOCK_LOW_QUEUE              = "stock.low";
    public static final String STOCK_REPLENISHED_QUEUE      = "stock.replenished";
    public static final String INVOICE_GENERATED_QUEUE      = "invoice.generated";
    public static final String CLAIM_SUBMITTED_QUEUE        = "claim.submitted";
    public static final String CLAIM_DENIED_QUEUE           = "claim.denied";

    /**
     * Queue bean for patient registration events.
     * @return a new Queue named "patient.registered".
     */
    @Bean
    public Queue patientRegisteredQueue() {
        return new Queue(PATIENT_REGISTERED_QUEUE, true);
    }

    /**
     * Queue bean for patient update events.
     * @return a new Queue named "patient.updated".
     */
    @Bean
    public Queue patientUpdatedQueue() {
        return new Queue(PATIENT_UPDATED_QUEUE, true);
    }

    /**
     * Queue bean for appointment creation events.
     * @return a new Queue named "appointment.created".
     */
    @Bean
    public Queue appointmentCreatedQueue() {
        return new Queue(APPOINTMENT_CREATED_QUEUE, true);
    }

    /**
     * Queue bean for appointment update events.
     * @return a new Queue named "appointment.updated".
     */
    @Bean
    public Queue appointmentUpdatedQueue() {
        return new Queue(APPOINTMENT_UPDATED_QUEUE, true);
    }

    /**
     * Queue bean for appointment cancellation events.
     * @return a new Queue named "appointment.cancelled".
     */
    @Bean
    public Queue appointmentCancelledQueue() {
        return new Queue(APPOINTMENT_CANCELLED_QUEUE, true);
    }

    /**
     * Queue bean for staff onboarding events.
     * @return a new Queue named "staff.onboarded".
     */
    @Bean
    public Queue staffOnboardedQueue() {
        return new Queue(STAFF_ONBOARDED_QUEUE, true);
    }

    /**
     * Queue bean for staff update events.
     * @return a new Queue named "staff.updated".
     */
    @Bean
    public Queue staffUpdatedQueue() {
        return new Queue(STAFF_UPDATED_QUEUE, true);
    }

    /**
     * Queue bean for prescription filled events.
     * @return a new Queue named "prescription.filled".
     */
    @Bean
    public Queue prescriptionFilledQueue() {
        return new Queue(PRESCRIPTION_FILLED_QUEUE, true);
    }

    /**
     * Queue bean for medication dispensed events.
     * @return a new Queue named "medication.dispensed".
     */
    @Bean
    public Queue medicationDispensedQueue() {
        return new Queue(MEDICATION_DISPENSED_QUEUE, true);
    }

    /**
     * Queue bean for low stock alerts.
     * @return a new Queue named "stock.low".
     */
    @Bean
    public Queue stockLowQueue() {
        return new Queue(STOCK_LOW_QUEUE, true);
    }

    /**
     * Queue bean for stock replenishment events.
     * @return a new Queue named "stock.replenished".
     */
    @Bean
    public Queue stockReplenishedQueue() {
        return new Queue(STOCK_REPLENISHED_QUEUE, true);
    }

    /**
     * Queue bean for invoice generation events.
     * @return a new Queue named "invoice.generated".
     */
    @Bean
    public Queue invoiceGeneratedQueue() {
        return new Queue(INVOICE_GENERATED_QUEUE, true);
    }

    /**
     * Queue bean for claim submission events.
     * @return a new Queue named "claim.submitted".
     */
    @Bean
    public Queue claimSubmittedQueue() {
        return new Queue(CLAIM_SUBMITTED_QUEUE, true);
    }

    /**
     * Queue bean for claim denial events.
     * @return a new Queue named "claim.denied".
     */
    @Bean
    public Queue claimDeniedQueue() {
        return new Queue(CLAIM_DENIED_QUEUE, true);
    }

    /**
     * Message converter that uses Jackson to serialize/deserialize JSON messages.
     * @return a configured Jackson2JsonMessageConverter.
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * RabbitTemplate configured with JSON message converter for publishing events.
     * @param connectionFactory the RabbitMQ ConnectionFactory
     * @return a configured RabbitTemplate instance
     */
    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}

