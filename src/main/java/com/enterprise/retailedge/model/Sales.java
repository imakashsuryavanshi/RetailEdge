package com.enterprise.retailedge.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="sales")
public class Sales {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID salesId;

	@Column(nullable = false)
	private UUID productId; //Reference to the product sold
	
	@Column(nullable = false)
	private int quantity; //Quantity of the product sold
	
	@Column(nullable = false)
	private double price; //Price per unit
	
	@Column(nullable = false)
	private double totalAmount; //Total amount (Price * Quantity)
	
	@Column(nullable = false)
	private String customerName;
	
	@Column(nullable = false)
	private LocalDateTime saleDate; //Date & Time of sale
	
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;
	
	@Column(nullable = false)
	private LocalDateTime updatedAt;
	
	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
		updatedAt = createdAt;
		calculateTotalAmount();
	}
	
	private void calculateTotalAmount() {
		this.totalAmount = this.quantity * this.price;
	}

	@PreUpdate
	protected void onUpdate() {
		updatedAt = LocalDateTime.now();
		calculateTotalAmount();
	}
}
