package com.healthcareplatform.InventoryService.eventPublisher;

import com.healthcareplatform.InventoryService.config.RabbitMQConfig;
import com.healthcareplatform.InventoryService.dto.InventoryResponse;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class InventoryEventPublisher {
    private final RabbitTemplate rabbitTemplate;

    public InventoryEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Publish Stock Low event any item’s stock level drops below its reorder threshold.
     */
    public void publishStockLow(InventoryResponse inventory) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.STOCK_LOW_QUEUE, inventory);
    }

    /**
     * Publish Once a replenishment order arrives and inventory is restocked.
     */
    public void publishStockReplenished(InventoryResponse inventory) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.STOCK_REPLENISHED_QUEUE, inventory);
    }

}
