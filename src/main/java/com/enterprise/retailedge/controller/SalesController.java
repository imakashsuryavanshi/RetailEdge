package com.enterprise.retailedge.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enterprise.retailedge.model.Inventory;
import com.enterprise.retailedge.model.Sales;
import com.enterprise.retailedge.service.SalesService;

@RestController
@RequestMapping("/api/sales")
public class SalesController {

	@Autowired
	private SalesService salesService;
	
	@PostMapping
    public ResponseEntity<Sales> createSale(@RequestBody Sales sale) {
		
        Sales createdSale = salesService.createSale(sale);
        return ResponseEntity.ok(createdSale);
    }

    @GetMapping
    public ResponseEntity<List<Sales>> getAllSales() {
        List<Sales> sales = salesService.getAllSales();
        return ResponseEntity.ok(sales);
    }

    @GetMapping("/{saleId}")
    public ResponseEntity<Sales> getSaleById(@PathVariable UUID saleId) {
        Optional<Sales> sale = salesService.getSaleById(saleId);
        return sale.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<Sales>> getSalesByDateRange(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        List<Sales> sales = salesService.getSalesByDateRange(startDate, endDate);
        return ResponseEntity.ok(sales);
    }
    
    @PutMapping("/{saleId}")
    public ResponseEntity<Sales> updateSale(@PathVariable UUID saleId, @RequestBody Sales updatedSale) {
        Sales sale = salesService.updateSale(saleId, updatedSale);
        return ResponseEntity.ok(sale);
    }

    @DeleteMapping("/{saleId}")
    public ResponseEntity<Void> deleteSale(@PathVariable UUID saleId) {
        salesService.deleteSale(saleId);
        return ResponseEntity.noContent().build();
    }
}
