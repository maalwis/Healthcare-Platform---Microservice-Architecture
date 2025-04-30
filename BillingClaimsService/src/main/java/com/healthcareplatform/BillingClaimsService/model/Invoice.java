package com.healthcareplatform.BillingClaimsService.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.util.UUID;

import java.time.OffsetDateTime;

/**
 * Represents a patient’s financial charge, summarizing all billed line items.
 *
 * <p>Within the “invoices” table:
 * <ul>
 *   <li><b>patientId</b> links to the billing profile of the patient.</li>
 *   <li><b>dateIssued</b> records when the invoice was generated.</li>
 *   <li><b>totalAmount</b> is the sum of all invoice items for payment processing.</li>
 *   <li><b>status</b> tracks invoice state (“UNPAID,” “PAID,” “VOID”).</li>
 *   <li><b>createdAt</b> marks record creation for auditing.</li>
 * </ul>
 *
 * <p>Invoices group individual charges, and line items are stored separately for detailed breakdowns.
 *
 <p>
 * Billing & Claims Service Composition
 <p>
 * In the billing domain, an Invoice is composed of multiple InvoiceItem entries.
 * Each InvoiceItem represents a single line charge (e.g. “Consultation Fee”) and
 * exists only within the context of its parent invoice. In our model, the Invoice
 * entity would have a @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
 * collection of InvoiceItems so that persisting or removing an invoice automatically
 * persists or removes all its line items. This tightly-coupled composition ensures
 * that an invoice’s total amount remains consistent with its items, and it prevents
 * orphaned line items from lingering if an invoice is deleted.
 * */

@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    @Column(name = "date_issued", nullable = false)
    private OffsetDateTime dateIssued;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @Column(length = 20)
    private String status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public OffsetDateTime getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(OffsetDateTime dateIssued) {
        this.dateIssued = dateIssued;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}