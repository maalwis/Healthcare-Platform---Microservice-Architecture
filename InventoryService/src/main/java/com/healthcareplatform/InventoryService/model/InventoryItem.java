package com.healthcareplatform.InventoryService.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Captures inventory levels for consumable items and supplies.
 *
 * <p>In the “inventory_items” table:
 * <ul>
 *   <li><b>name</b> and <b>description</b> identify the item.</li>
 *   <li><b>category</b> (“Bandage,” “Syringe”...) for classification and reporting.</li>
 *   <li><b>quantityOnHand</b> and <b>reorderThreshold</b> to drive replenishment logic.</li>
 *   <li><b>attributes</b> String for dimensions, supplier part numbers, or other variant data.</li>
 *   <li><b>lastRestock</b> to track when stock was last updated.</li>
 *   <li><b>createdAt</b> for initial record time.</li>
 * </ul>
 *
 * <p>This entity underpins stock management.
 */


@Entity
@Table(name = "inventory_items")
public class InventoryItem {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, length = 200)
    private String name;

    private String description;

    private String category;

    @Column(name = "quantity_on_hand", nullable = false)
    private Integer quantityOnHand;

    @Column(name = "reorder_threshold")
    private Integer reorderThreshold;


    @Column(name = "attributes")
    private String attributes;

    private OffsetDateTime lastRestock;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(Integer quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }

    public Integer getReorderThreshold() {
        return reorderThreshold;
    }

    public void setReorderThreshold(Integer reorderThreshold) {
        this.reorderThreshold = reorderThreshold;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public OffsetDateTime getLastRestock() {
        return lastRestock;
    }

    public void setLastRestock(OffsetDateTime lastRestock) {
        this.lastRestock = lastRestock;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}