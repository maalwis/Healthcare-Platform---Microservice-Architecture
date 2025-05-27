package com.healthcareplatform.BillingClaimsService.insuranceClaim;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.healthcareplatform.BillingClaimsService.invoice.Invoice;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "insurance_claims")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceClaim {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * Link back to the invoice we’re claiming on.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id", nullable = false)
    @JsonBackReference
    private Invoice invoice;

    private String insurer;

    @Column(name = "claim_status")
    private String claimStatus;

    @Column(columnDefinition = "TEXT")
    private String details;

    private LocalDateTime submittedAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
