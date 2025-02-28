package com.enterprise.retailedge.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enterprise.retailedge.model.Sales;

@Repository
public interface SalesRepository extends JpaRepository<Sales, UUID>{
	
	List<Sales> findBySaleDateBetween(LocalDateTime startDate, LocalDateTime endDate); //For reports
}
