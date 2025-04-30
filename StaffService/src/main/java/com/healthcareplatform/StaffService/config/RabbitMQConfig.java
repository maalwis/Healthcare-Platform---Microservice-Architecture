package com.healthcareplatform.StaffService.config;


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
    public static final String STAFF_ONBOARDED_QUEUE        = "staff.onboarded";
    public static final String STAFF_UPDATED_QUEUE          = "staff.updated";

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

