package com.enterprise.retailedge.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enterprise.retailedge.dto.InventoryDTO;
import com.enterprise.retailedge.model.Inventory;
import com.enterprise.retailedge.repository.InventoryRepository;

@Service
public class InventoryService {

	@Autowired
	private InventoryRepository inventoryRepository;
	
	public Inventory addProduct(InventoryDTO inventoryDTO) {
	    Inventory product = new Inventory();
	    product.setProductName(inventoryDTO.getProductName());
	    product.setQuantityInStock(inventoryDTO.getQuantityInStock());
	    product.setCategory(inventoryDTO.getCategory());
	    product.setSupplier(inventoryDTO.getSupplier());
	    return inventoryRepository.save(product);
	}

	public Inventory updateProduct(UUID productId, InventoryDTO inventoryDTO) {
	    return inventoryRepository.findById(productId).map(product -> {
	        product.setProductName(inventoryDTO.getProductName());
	        product.setQuantityInStock(inventoryDTO.getQuantityInStock());
	        product.setCategory(inventoryDTO.getCategory());
	        product.setSupplier(inventoryDTO.getSupplier());
	        return inventoryRepository.save(product);
	    }).orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));
	}
	
	public List<Inventory> getAllProducts(){
		return inventoryRepository.findAll();
	}
	
	public Optional<Inventory> getProductById(UUID productId) {
		return inventoryRepository.findById(productId);
	}
	
	public List<Inventory> getProductsByCategory(String category){
		return inventoryRepository.findByCategory(category);
	}
	
    public void deleteProduct(UUID productId) {
        inventoryRepository.deleteById(productId);
    }
}
