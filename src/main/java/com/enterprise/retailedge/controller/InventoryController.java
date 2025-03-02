package com.enterprise.retailedge.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enterprise.retailedge.dto.InventoryDTO;
import com.enterprise.retailedge.model.Inventory;
import com.enterprise.retailedge.service.InventoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

	@Autowired
	private InventoryService inventoryService;
	
	@PostMapping
	public ResponseEntity<Inventory> addProduct(@Valid @RequestBody InventoryDTO inventoryDTO) {
	    Inventory product = inventoryService.addProduct(inventoryDTO);
	    return ResponseEntity.ok(product);
	}


    @GetMapping
    public ResponseEntity<List<Inventory>> getAllProducts() {
        List<Inventory> products = inventoryService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Inventory> getProductById(@PathVariable UUID productId) {
        Optional<Inventory> product = inventoryService.getProductById(productId);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<?> getProductsByCategory(@PathVariable String category) {
        List<Inventory> products = inventoryService.getProductsByCategory(category);
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("No products found for category: " + category);
        }
        return ResponseEntity.ok(products);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Inventory> updateProduct(@PathVariable UUID productId, @Valid @RequestBody InventoryDTO inventoryDTO) {
        Inventory product = inventoryService.updateProduct(productId, inventoryDTO);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID productId) {
        inventoryService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
