package com.enterprise.retailedge.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enterprise.retailedge.model.Sales;
import com.enterprise.retailedge.repository.SalesRepository;

@Service
public class SalesService {
	
	@Autowired
	private SalesRepository salesRepository;
	
	public Sales createSale(Sales sale) {
		return salesRepository.save(sale);
	}
	
	public List<Sales> getAllSales(){
		return salesRepository.findAll();
	}
	
	public Optional<Sales> findSaleById(UUID saleId) {
		return salesRepository.findById(saleId);
	}
	
	public List<Sales> getSalesByDateRange(LocalDateTime startDate, LocalDateTime endDate){
		return salesRepository.findBySaleDateBetween(startDate, endDate);
	}
	
	public void deleteSale(UUID saleId) {
		salesRepository.deleteById(saleId);
	}
}
