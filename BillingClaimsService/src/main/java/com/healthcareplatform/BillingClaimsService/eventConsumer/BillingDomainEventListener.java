package com.healthcareplatform.BillingClaimsService.eventConsumer;

import com.healthcareplatform.BillingClaimsService.config.RabbitMQConfig;
import com.healthcareplatform.BillingClaimsService.dto.AppointmentDto;
import com.healthcareplatform.BillingClaimsService.dto.PatientDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class BillingDomainEventListener {


    public BillingDomainEventListener(){};

    @RabbitListener(queues = RabbitMQConfig.APPOINTMENT_CREATED_QUEUE)
    public void onAppointmentCreated(AppointmentDto appointment) {
        // Pre-generate draft billing line-items when an appointment is booked.

    }

    @RabbitListener(queues = RabbitMQConfig.PATIENT_REGISTERED_QUEUE)
    public void onPatientRegistered(PatientDto patient) {
        // Initialize billing profiles when a new patient registers.

    }
}