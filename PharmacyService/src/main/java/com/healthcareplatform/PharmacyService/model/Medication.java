package com.healthcareplatform.PharmacyService.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Defines a medication’s master data within the pharmacy domain.
 *
 * <p>Each record in “medications” carries:
 * <ul>
 *   <li><b>name</b> for the drug’s commercial or generic name.</li>
 *   <li><b>description</b> for detailed usage notes or pharmacology information.</li>
 *   <li><b>manufacturer</b> to trace drug provenance.</li>
 *   <li><b>cost</b> as a numeric field representing unit price for billing and inventory valuation.</li>
 *   <li><b>info</b> String for dosage forms, packaging sizes, or other attributes that vary by
 *       medication.</li>
 *   <li><b>createdAt</b> for audit of when this medication was first introduced into the system.</li>
 * </ul>
 *
 * <p>An index on name supports rapid lookup during prescription entry.
 */


@Entity
@Table(name = "medications")
public class Medication {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, length = 200)
    private String name;

    private String description;

    private String manufacturer;

    private BigDecimal cost;

    @Column(name = "info")
    private String info;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}