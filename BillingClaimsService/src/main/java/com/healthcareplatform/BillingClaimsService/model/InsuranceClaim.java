package com.healthcareplatform.BillingClaimsService.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Records interactions with external payers for insurance claims management.
 *
 * <p>Stored in the “insurance_claims” table with:
 * <ul>
 *   <li><b>invoiceId</b> linking the claim to the original invoice.</li>
 *   <li><b>insurer</b> identifying the payer name or code.</li>
 *   <li><b>claimStatus</b> reflecting adjudication results (e.g., “SUBMITTED,” “DENIED,” “PAID”).</li>
 *   <li><b>details</b> String capturing full request/response payloads or error messages.</li>
 *   <li><b>submittedAt</b> timestamp for when the claim was sent.</li>
 *   <li><b>createdAt</b> for record creation.</li>
 * </ul>
 *
 * <p>This model supports auditability and troubleshooting of insurer interactions.
 */

@Entity
@Table(name = "insurance_claims")
public class InsuranceClaim {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "invoice_id", nullable = false)
    private UUID invoiceId;

    private String insurer;

    @Column(name = "claim_status")
    private String claimStatus;


    @Column(name = "details")
    private String details;

    private OffsetDateTime submittedAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(UUID invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getInsurer() {
        return insurer;
    }

    public void setInsurer(String insurer) {
        this.insurer = insurer;
    }

    public String getClaimStatus() {
        return claimStatus;
    }

    public void setClaimStatus(String claimStatus) {
        this.claimStatus = claimStatus;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public OffsetDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(OffsetDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}