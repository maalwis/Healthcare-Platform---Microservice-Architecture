package com.healthcareplatform.BillingClaimsService.eventPublisher;

import com.healthcareplatform.BillingClaimsService.config.RabbitMQConfig;
import com.healthcareplatform.BillingClaimsService.dto.ClaimResponse;
import com.healthcareplatform.BillingClaimsService.dto.InvoiceRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * Publishes billing-related events to RabbitMQ.
 */
@Service
public class BillingEventPublisher {
    private final RabbitTemplate rabbitTemplate;

    public BillingEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Publish InvoiceGenerated event after an invoice is created.
     * @param invoice the generated invoice data
     */
    public void publishInvoiceGenerated(InvoiceRequest invoice) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.INVOICE_GENERATED_QUEUE, invoice);
    }

    /**
     * Publish ClaimSubmitted event after submitting to insurer.
     * @param claim the claim submission data
     */
    public void publishClaimSubmitted(ClaimResponse claim) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.CLAIM_SUBMITTED_QUEUE, claim);
    }

    /**
     * Publish ClaimDenied event when a claim is rejected by insurer.
     * @param claim the denied claim data
     */
    public void publishClaimDenied(ClaimResponse claim) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.CLAIM_DENIED_QUEUE, claim);
    }
}
