package com.healthcareplatform.PharmacyService.messaging.publisher;

import com.healthcareplatform.PharmacyService.config.RabbitMQConfig;
import com.healthcareplatform.PharmacyService.dto.MedicationDispensedEvent;
import com.healthcareplatform.PharmacyService.dto.PrescriptionDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class PharmacyEventPublisher {
    private final RabbitTemplate rabbitTemplate;

    public PharmacyEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Publish PrescriptionFilled event after prescription is prepared.
     */
    public void publishPrescriptionFilled(PrescriptionDto prescription) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.PRESCRIPTION_FILLED_QUEUE, prescription);
    }

    /**
     * Publish MedicationDispensed event after medication is dispensed.
     */
    public void publishMedicationDispensed(MedicationDispensedEvent evt) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.MEDICATION_DISPENSED_QUEUE, evt);
    }
}