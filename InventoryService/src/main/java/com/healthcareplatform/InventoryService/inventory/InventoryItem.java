package com.healthcareplatform.InventoryService.inventory;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;


@Entity
@Table(name = "inventory_items")
public class InventoryItem {

    @Id
    @GeneratedValue
    private Long id;

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

    private LocalDateTime lastRestock;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

}