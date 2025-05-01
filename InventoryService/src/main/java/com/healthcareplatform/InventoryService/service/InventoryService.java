package com.healthcareplatform.InventoryService.service;

import com.healthcareplatform.InventoryService.dto.InventoryDto;
import com.healthcareplatform.InventoryService.dto.ReorderRequestDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InventoryService {
    public List<InventoryDto> getAllItems(){
        return null;
    };

    public InventoryDto getItemById(UUID itemId){
        return null;
    };

    public InventoryDto addItem(@Valid InventoryDto item){
        return null;
    };

    public InventoryDto updateItem(UUID itemId, @Valid InventoryDto itemDto){
        return null;
    };

    public void reorderItem(UUID itemId, ReorderRequestDto reorderRequest){
        return ;
    };
}
