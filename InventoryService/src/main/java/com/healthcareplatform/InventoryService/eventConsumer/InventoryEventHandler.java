package com.healthcareplatform.InventoryService.eventConsumer;

import com.healthcareplatform.InventoryService.config.RabbitMQConfig;
import com.healthcareplatform.InventoryService.dto.MedicationDispensedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class InventoryEventHandler {
    private final RabbitTemplate rabbitTemplate;

    public InventoryEventHandler(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Consumer: adjust inventory when medication is dispensed.
     */
    @RabbitListener(queues = RabbitMQConfig.MEDICATION_DISPENSED_QUEUE)
    public void handleMedicationDispensed(MedicationDispensedEvent evt) {

    }
}
