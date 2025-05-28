package com.healthcareplatform.InventoryService.inventory;

import com.healthcareplatform.InventoryService.dto.InventoryRequest;
import com.healthcareplatform.InventoryService.dto.InventoryResponse;
import com.healthcareplatform.InventoryService.dto.ReorderInventoryRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InventoryService {
    public List<InventoryResponse> getAllItems(){
        return null;
    };

    public InventoryResponse getItemById(Long itemId){
        return null;
    };

    public InventoryResponse addItem(@Valid InventoryRequest item){
        return null;
    };

    public InventoryResponse updateItem(Long itemId, @Valid InventoryRequest itemDto){
        return null;
    };

    public void reorderItem(Long itemId, ReorderInventoryRequest reorderRequest){
        return ;
    };
}
