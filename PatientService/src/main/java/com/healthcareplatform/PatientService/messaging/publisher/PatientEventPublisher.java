package com.healthcareplatform.PatientService.messaging.publisher;

import com.healthcareplatform.PatientService.config.RabbitMQConfig;
import com.healthcareplatform.PatientService.dto.PatientDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * Publishes patient-related events to RabbitMQ.
 */
@Service
public class PatientEventPublisher {
    private final RabbitTemplate rabbitTemplate;

    public PatientEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Publish PatientRegistered event after successful registration.
     */
    public void publishPatientRegistered(PatientDto patient) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.PATIENT_REGISTERED_QUEUE, patient);
    }

    /**
     * Publish PatientUpdated event after profile update.
     */
    public void publishPatientUpdated(PatientDto patient) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.PATIENT_UPDATED_QUEUE, patient);
    }
}