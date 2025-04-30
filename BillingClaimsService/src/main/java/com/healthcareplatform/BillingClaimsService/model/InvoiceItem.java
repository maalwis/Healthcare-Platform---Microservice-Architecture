package com.healthcareplatform.BillingClaimsService.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Denotes each billed service or item on an invoice.
 *
 * <p>In the “invoice_items” table, the composite key consists of:
 * <ul>
 *   <li><b>invoiceId</b> referring to its parent invoice.</li>
 *   <li><b>description</b> summarizing the charge (e.g., “Consultation Fee”).</li>
 *   <li><b>amount</b> as the cost for that specific line item.</li>
 * </ul>
 *
 * <p>This entity ensures a granular breakdown of invoice totals for transparency and reporting.
 */


@Entity
@IdClass(InvoiceItemId.class)
@Table(name = "invoice_items")
public class InvoiceItem {

    @Id
    @Column(name = "invoice_id")
    private UUID invoiceId;

    @Id
    private String description;

    @Column(nullable = false)
    private BigDecimal amount;

    public UUID getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(UUID invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}