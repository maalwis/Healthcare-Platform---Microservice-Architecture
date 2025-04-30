package com.healthcareplatform.InventoryService.config;


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
    public static final String STOCK_LOW_QUEUE              = "stock.low";
    public static final String STOCK_REPLENISHED_QUEUE      = "stock.replenished";
    public static final String MEDICATION_DISPENSED_QUEUE   = "medication.dispensed";


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
     * Queue bean for medication dispensed events.
     * @return a new Queue named "medication.dispensed".
     */
    @Bean
    public Queue medicationDispensedQueue() {
        return new Queue(MEDICATION_DISPENSED_QUEUE, true);
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

