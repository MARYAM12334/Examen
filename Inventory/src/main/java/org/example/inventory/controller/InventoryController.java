package org.example.inventory.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.inventory.dto.InventoryResponse;
import org.example.inventory.model.Inventory;
import org.example.inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@Slf4j
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<List<InventoryResponse>> isInStock(@RequestParam  List<String> skuCode) {
        log.info("Received inventory check request for skuCode: {}", skuCode);
        List<InventoryResponse> response = inventoryService.isInStock(skuCode);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Inventory> addInventory(@RequestBody Inventory inventory) {
        log.info("Received request to add inventory: {}", inventory);
        Inventory savedInventory = inventoryService.addInventory(inventory);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedInventory);
    }

    @PutMapping("/{skuCode}")
    public ResponseEntity<Void> updateQuantity(
            @PathVariable String skuCode,
            @RequestParam Integer quantity) {
        log.info("Received request to update quantity for skuCode: {} to: {}", skuCode, quantity);
        inventoryService.updateQuantity(skuCode, quantity);
        return ResponseEntity.ok().build();
    }
}