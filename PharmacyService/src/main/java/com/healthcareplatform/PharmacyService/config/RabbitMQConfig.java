package com.healthcareplatform.PharmacyService.config;


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
    public static final String PRESCRIPTION_FILLED_QUEUE    = "prescription.filled";
    public static final String MEDICATION_DISPENSED_QUEUE   = "medication.dispensed";

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

