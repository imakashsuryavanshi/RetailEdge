package com.enterprise.retailedge.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class SalesDTO {
	
    private UUID productId;

    @PositiveOrZero(message = "Quantity must be a positive number or zero")
    private int quantity;

    @PositiveOrZero(message = "Price must be a positive number or zero")
    private double price;

    @NotBlank(message = "Customer name is required")
    private String customerName;

    private LocalDateTime saleDate;
}