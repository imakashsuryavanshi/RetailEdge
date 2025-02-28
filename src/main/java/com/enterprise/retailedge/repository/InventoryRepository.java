package com.enterprise.retailedge.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enterprise.retailedge.model.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, UUID>{

	List<Inventory> findByCategory(String category); //For filtering by category
}
