package com.healthcareplatform.InventoryService.service;

import com.healthcareplatform.InventoryService.dto.InventoryDto;
import com.healthcareplatform.InventoryService.dto.ReorderRequestDto;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface InventoryService {
    List<InventoryDto> getAllItems();

    InventoryDto getItemById(UUID itemId);

    InventoryDto addItem(@Valid InventoryDto item);

    InventoryDto updateItem(UUID itemId, @Valid InventoryDto itemDto);

    void reorderItem(UUID itemId, ReorderRequestDto reorderRequest);
}
