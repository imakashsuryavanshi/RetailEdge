package com.enterprise.retailedge.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.enterprise.retailedge.dto.SalesDTO;
import com.enterprise.retailedge.exception.InsufficientStockException;
import com.enterprise.retailedge.exception.ProductNotFoundException;
import com.enterprise.retailedge.model.Inventory;
import com.enterprise.retailedge.model.Sales;
import com.enterprise.retailedge.repository.InventoryRepository;
import com.enterprise.retailedge.repository.SalesRepository;

@Service
public class SalesService {
	
	@Autowired
	private SalesRepository salesRepository;
	
	@Autowired
	private InventoryRepository inventoryRepository;
	
	@Transactional
	public Sales createSale(SalesDTO salesDTO) {
	    Optional<Inventory> productOpt = inventoryRepository.findById(salesDTO.getProductId());
	    if (productOpt.isEmpty()) {
	        throw new ProductNotFoundException("Product not found in inventory.");
	    }

	    Inventory product = productOpt.get();
	    if (product.getQuantityInStock() < salesDTO.getQuantity()) {
	        throw new InsufficientStockException("Insufficient stock for product: " + product.getProductName()
	                + ". Available stock: " + product.getQuantityInStock());
	    }

	    product.setQuantityInStock(product.getQuantityInStock() - salesDTO.getQuantity());
	    inventoryRepository.save(product);

	    Sales sale = new Sales();
	    sale.setProductId(salesDTO.getProductId());
	    sale.setQuantity(salesDTO.getQuantity());
	    sale.setPrice(salesDTO.getPrice());
	    sale.setCustomerName(salesDTO.getCustomerName());
	    sale.setSaleDate(salesDTO.getSaleDate());
	    return salesRepository.save(sale);
	}
	
	public List<Sales> getAllSales(){
		return salesRepository.findAll();
	}
	
	public Optional<Sales> getSaleById(UUID saleId) {
		return salesRepository.findById(saleId);
	}
	
	public List<Sales> getSalesByDateRange(LocalDateTime startDate, LocalDateTime endDate){
		return salesRepository.findBySaleDateBetween(startDate, endDate);
	}
	
	@Transactional
    public Sales updateSale(UUID saleId, Sales updatedSale) {
        return salesRepository.findById(saleId).map(existingSale -> {
            Optional<Inventory> productOpt = inventoryRepository.findById(existingSale.getProductId());
            if (productOpt.isEmpty()) {
                throw new RuntimeException("Product not found in inventory.");
            }
            Inventory product = productOpt.get();
            
            int quantityDifference = updatedSale.getQuantity() - existingSale.getQuantity();
            if (quantityDifference > 0 && product.getQuantityInStock() < quantityDifference) {
                throw new RuntimeException("Not enough stock available for update.");
            }
            
            product.setQuantityInStock(product.getQuantityInStock() - quantityDifference);
            inventoryRepository.save(product);
            
            existingSale.setQuantity(updatedSale.getQuantity());
            existingSale.setPrice(updatedSale.getPrice());
//            existingSale.setTotalAmount(updatedSale.getPrice() * updatedSale.getQuantity());
            existingSale.setCustomerName(updatedSale.getCustomerName());
            existingSale.setSaleDate(updatedSale.getSaleDate());
            return salesRepository.save(existingSale);
        }).orElseThrow(() -> new RuntimeException("Sale not found with ID: " + saleId));
    }
	
	@Transactional
    public void deleteSale(UUID saleId) {
        salesRepository.findById(saleId).ifPresent(sale -> {
            inventoryRepository.findById(sale.getProductId()).ifPresent(product -> {
                product.setQuantityInStock(product.getQuantityInStock() + sale.getQuantity());
                inventoryRepository.save(product);
            });
            salesRepository.deleteById(saleId);
        });
    }
}
