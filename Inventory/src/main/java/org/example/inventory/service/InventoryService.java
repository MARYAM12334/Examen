package org.example.inventory.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.example.inventory.dto.InventoryResponse;
import org.example.inventory.model.Inventory;
import org.example.inventory.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
@Slf4j
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public List<InventoryResponse> isInStock(List<String> skuCode) {
        log.info("Checking stock for skuCode: {}", skuCode);

        return inventoryRepository.findBySkuCodeIn(skuCode).stream()
                .map(inventory ->
                        InventoryResponse.builder()
                                .skuCode(inventory.getSkuCode())
                                .isInStock(inventory.getQuantity() > 0)
                                .build()
                ).toList();
    }

    @Transactional
    public Inventory addInventory(Inventory inventory) {
        log.info("Adding new inventory item: {}", inventory);
        return inventoryRepository.save(inventory);
    }

    @Transactional
    public void updateQuantity(String skuCode, Integer quantity) {
        log.info("Updating quantity for skuCode: {} to: {}", skuCode, quantity);

        Inventory inventory = inventoryRepository.findBySkuCodeIn(Collections.singletonList(skuCode))
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found with skuCode: " + skuCode));

        inventory.setQuantity(quantity);
        inventoryRepository.save(inventory);
    }

}

