package com.healthcareplatform.BillingClaimsService.invoice;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.healthcareplatform.BillingClaimsService.insuranceClaim.InsuranceClaim;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "invoices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invoice {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "patient_id", nullable = false)
    private Long patientId;

    @Column(name = "date_issued", nullable = false)
    private LocalDateTime dateIssued;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @Column(length = 20)
    private String status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * The line items on this invoice.
     * Cascade on all operations, and remove orphans if they’re deleted from the set.
     */
    @OneToMany(
            mappedBy = "invoice",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JsonManagedReference
    @Builder.Default
    private Set<InvoiceItem> items = new HashSet<>();

    /**
     * All insurance claims submitted against this invoice.
     */
    @OneToMany(
            mappedBy = "invoice",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JsonManagedReference
    @Builder.Default
    private Set<InsuranceClaim> claims = new HashSet<>();

    // convenience methods
    public void addItem(InvoiceItem item) {
        item.setInvoice(this);
        items.add(item);
    }

    public void removeItem(InvoiceItem item) {
        items.remove(item);
        item.setInvoice(null);
    }

    public void addClaim(InsuranceClaim claim) {
        claim.setInvoice(this);
        claims.add(claim);
    }
}
