package com.healthcareplatform.PharmacyService.medication;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Defines a medication’s master data within the pharmacy domain.
 */


@Entity
@Table(name = "medications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Medication {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 200)
    private String name;

    private String description;

    private String manufacturer;

    private BigDecimal cost;

    @Column(name = "info")
    private String info;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private Integer stock;



}