package com.healthcareplatform.AnalyticsService.eventConsumer;

import com.healthcareplatform.AnalyticsService.analytic.AnalyticsService;
import com.healthcareplatform.AnalyticsService.config.RabbitMQConfig;
import com.healthcareplatform.AnalyticsService.dto.*;
import com.healthcareplatform.AnalyticsService.dto.MedicationDispensedDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import static com.healthcareplatform.AnalyticsService.config.RabbitMQConfig.*;

@Component
public class EventListener {

    @Autowired
    private AnalyticsService analyticsService;

    // ─── Listener methods ─────────────────────────────────────────────────────

    @RabbitListener(queues = PATIENT_REGISTERED_QUEUE)
    public void patientRegistered(PatientDto dto) {
        AnalyticsEventDto evt = buildEvent(PATIENT_REGISTERED_QUEUE);
        evt.setEventTime(dto.getCreatedAt());
        handleEvent(evt);
    }

    @RabbitListener(queues = RabbitMQConfig.PATIENT_UPDATED_QUEUE)
    public void patientUpdated(PatientDto dto) {

        AnalyticsEventDto evt = buildEvent(PATIENT_UPDATED_QUEUE);
        evt.setEventTime(dto.getCreatedAt());
        handleEvent(evt);
    }

    @RabbitListener(queues = RabbitMQConfig.APPOINTMENT_CREATED_QUEUE)
    public void appointmentCreated(AppointmentDto dto) {

        AnalyticsEventDto evt = buildEvent(APPOINTMENT_CREATED_QUEUE);
        evt.setEventTime(dto.getCreatedAt());
        handleEvent(evt);
    }

    @RabbitListener(queues = RabbitMQConfig.APPOINTMENT_UPDATED_QUEUE)
    public void appointmentUpdated(AppointmentDto dto) {

        AnalyticsEventDto evt = buildEvent(APPOINTMENT_UPDATED_QUEUE);
        evt.setEventTime(dto.getCreatedAt());
        handleEvent(evt);
    }

    @RabbitListener(queues = RabbitMQConfig.APPOINTMENT_CANCELLED_QUEUE)
    public void appointmentCancelled(AppointmentDto dto) {

        AnalyticsEventDto evt = buildEvent(APPOINTMENT_CANCELLED_QUEUE);
        evt.setEventTime(dto.getCreatedAt());
        handleEvent(evt);
    }

    @RabbitListener(queues = RabbitMQConfig.STAFF_ONBOARDED_QUEUE)
    public void staffOnboarded(StaffDto dto) {

        AnalyticsEventDto evt = buildEvent(STAFF_ONBOARDED_QUEUE);
        evt.setEventTime(dto.getCreatedAt());
        handleEvent(evt);
    }

    @RabbitListener(queues = RabbitMQConfig.STAFF_UPDATED_QUEUE)
    public void staffUpdated(StaffDto dto) {

        AnalyticsEventDto evt = buildEvent(STAFF_UPDATED_QUEUE);
        evt.setEventTime(dto.getCreatedAt());
        handleEvent(evt);
    }

    @RabbitListener(queues = RabbitMQConfig.PRESCRIPTION_FILLED_QUEUE)
    public void prescriptionFilled(PrescriptionDto dto) {

        AnalyticsEventDto evt = buildEvent(PRESCRIPTION_FILLED_QUEUE);
        evt.setEventTime(dto.getCreatedAt());
        handleEvent(evt);
    }

    @RabbitListener(queues = RabbitMQConfig.MEDICATION_DISPENSED_QUEUE)
    public void medicationDispensed(MedicationDispensedDto dto) {

        AnalyticsEventDto evt = buildEvent(MEDICATION_DISPENSED_QUEUE);
        evt.setEventTime(dto.getCreatedAt());
        handleEvent(evt);
    }

    @RabbitListener(queues = RabbitMQConfig.STOCK_LOW_QUEUE)
    public void stockLow(StockDto dto) {

        AnalyticsEventDto evt = buildEvent(STOCK_LOW_QUEUE);
        evt.setEventTime(dto.getReportedAt());
        handleEvent(evt);
    }

    @RabbitListener(queues = RabbitMQConfig.STOCK_REPLENISHED_QUEUE)
    public void stockReplenished(StockDto dto) {

        AnalyticsEventDto evt = buildEvent(STOCK_REPLENISHED_QUEUE);
        evt.setEventTime(dto.getReportedAt());
        handleEvent(evt);
    }

    @RabbitListener(queues = RabbitMQConfig.INVOICE_GENERATED_QUEUE)
    public void invoiceGenerated(InvoiceDto dto) {

        AnalyticsEventDto evt = buildEvent(INVOICE_GENERATED_QUEUE);
        evt.setEventTime(dto.getCreatedAt());
        handleEvent(evt);
    }

    @RabbitListener(queues = CLAIM_SUBMITTED_QUEUE)
    public void claimSubmitted(ClaimDto dto) {

        AnalyticsEventDto evt = buildEvent(CLAIM_SUBMITTED_QUEUE);
        evt.setEventTime(dto.getCreatedAt());
        handleEvent(evt);
    }

    @RabbitListener(queues = CLAIM_DENIED_QUEUE)
    public void claimDenied(ClaimDto dto) {

        AnalyticsEventDto evt = buildEvent(CLAIM_DENIED_QUEUE);
        evt.setEventTime(dto.getCreatedAt());
        handleEvent(evt);
    }

    // ─── Helpers ────────────────────────────────────────────────────────────────

    /**
     * Builds a bare AnalyticsEventDto with type.
     */
    private AnalyticsEventDto buildEvent(String eventType) {
        AnalyticsEventDto evt = new AnalyticsEventDto();
        evt.setEventType(eventType);
        return evt;
    }

    /**
     * Generic handler: wrap payload into AnalyticsEventDto and persist.
     */
    private void handleEvent(AnalyticsEventDto evt) {
        analyticsService.createAnalyticEvent(evt);
    }
}
