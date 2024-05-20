package com.wosapasa.inventory.service;

import com.wosapasa.inventory.repository.InventoryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public Boolean isInStock(String skuCode){
//        return inventoryRepository.findBySkuCode().isPresent();
        return true;
    }
}
