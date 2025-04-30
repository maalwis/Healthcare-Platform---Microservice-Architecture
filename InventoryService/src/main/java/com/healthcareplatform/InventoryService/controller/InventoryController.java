package com.healthcareplatform.InventoryService.controller;

import com.healthcareplatform.InventoryService.dto.InventoryDto;
import com.healthcareplatform.InventoryService.dto.ReorderRequestDto;
import com.healthcareplatform.InventoryService.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for managing inventory items and reorders.
 */
@RestController
@RequestMapping("/api/v1/inventory/items")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    /**
     * Retrieve a list of all inventory items.
     *
     * TODO: Delegate to InventoryService to retrieve all items
     *
     * @return ResponseEntity containing a list of InventoryDto objects and HTTP 200 status.
     */
    @GetMapping
    public ResponseEntity<List<InventoryDto>> getAllItems() {
        List<InventoryDto> items = inventoryService.getAllItems();
        return ResponseEntity.ok(items);
    }

    /**
     * Retrieve details for a specific inventory item by ID.
     *
     * TODO: Delegate to InventoryService to fetch item by ID
     *
     * @param itemId Unique identifier of the inventory item (path variable)
     * @return ResponseEntity containing InventoryDto and HTTP 200 status if found;
     *         otherwise exception is propagated.
     */
    @GetMapping("/{id}")
    public ResponseEntity<InventoryDto> getItemById(@PathVariable UUID itemId) {
        InventoryDto item = inventoryService.getItemById(itemId);
        return ResponseEntity.ok(item);
    }

    /**
     * Create a new inventory item.
     *
     * TODO: Delegate to InventoryService to add a new item
     *
     * @param item Payload containing item data (validated request body)
     * @return ResponseEntity containing created InventoryDto and HTTP 201 status.
     */
    @PostMapping
    public ResponseEntity<InventoryDto> addItem(@Valid @RequestBody InventoryDto item) {
        InventoryDto created = inventoryService.addItem(item);
        return ResponseEntity.status(201).body(created);
    }

    /**
     * Update an existing inventory item.
     *
     * TODO: Delegate to InventoryService to update item details
     *
     * @param itemId Unique identifier of the inventory item (path variable)
     * @param itemDto Payload containing updated data (validated request body)
     * @return ResponseEntity containing updated InventoryDto and HTTP 200 status.
     */
    @PutMapping("/{id}")
    public ResponseEntity<InventoryDto> updateItem(
            @PathVariable UUID itemId,
            @Valid @RequestBody InventoryDto itemDto) {
        InventoryDto updated = inventoryService.updateItem(itemId, itemDto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Submit a reorder request for an inventory item.
     *
     * TODO: Delegate to InventoryService to reorder stock
     *
     * @param itemId Unique identifier of the inventory item (path variable)
     * @param reorderRequest Payload containing reorder data (validated request body)
     * @return ResponseEntity with HTTP 202 Accepted on successful request.
     */
    @PostMapping("/{id}/reorder")
    public ResponseEntity<Void> reorderItem(
            @PathVariable UUID itemId,
            @Valid @RequestBody ReorderRequestDto reorderRequest) {
        inventoryService.reorderItem(itemId, reorderRequest);
        return ResponseEntity.accepted().build();
    }
}
