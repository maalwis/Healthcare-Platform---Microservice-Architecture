package com.healthcareplatform.PatientService.config;

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
    public static final String PATIENT_REGISTERED_QUEUE = "patient.registered";
    public static final String PATIENT_UPDATED_QUEUE = "patient.updated";


    /**
     * Queue bean for patient registration events.
     *
     * @return a new Queue named "patient.registered".
     */
    @Bean
    public Queue patientRegisteredQueue() {
        return new Queue(PATIENT_REGISTERED_QUEUE, true);
    }

    /**
     * Queue bean for patient update events.
     *
     * @return a new Queue named "patient.updated".
     */
    @Bean
    public Queue patientUpdatedQueue() {
        return new Queue(PATIENT_UPDATED_QUEUE, true);
    }

    /**
     * Message converter that uses Jackson to serialize/deserialize JSON messages.
     *
     * @return a configured Jackson2JsonMessageConverter.
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * RabbitTemplate configured with JSON message converter for publishing events.
     *
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

