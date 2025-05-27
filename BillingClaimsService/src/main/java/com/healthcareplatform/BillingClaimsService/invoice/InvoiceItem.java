package com.healthcareplatform.BillingClaimsService.invoice;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "invoice_items")
@IdClass(InvoiceItem.InvoiceItemId.class)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class InvoiceItem {

    /**
     * Composite key class.
     */
    @Data
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class InvoiceItemId implements Serializable {
        private Long invoiceId;
        private String description;
    }

    @Id
    @Column(name = "invoice_id", insertable = false, updatable = false)
    private Long invoiceId;

    @Id
    private String description;

    @Column(nullable = false)
    private BigDecimal amount;

    /**
     * Back‐reference to the owning invoice.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id", nullable = false)
    @JsonBackReference
    private Invoice invoice;
}
