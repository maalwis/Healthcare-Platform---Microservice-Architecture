package com.healthcareplatform.AppointmentService.config;


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

    public static final String APPOINTMENT_CREATED_QUEUE    = "appointment.created";
    public static final String APPOINTMENT_UPDATED_QUEUE    = "appointment.updated";
    public static final String APPOINTMENT_CANCELLED_QUEUE  = "appointment.cancelled";

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

