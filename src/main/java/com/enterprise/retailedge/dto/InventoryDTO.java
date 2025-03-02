package com.enterprise.retailedge.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class InventoryDTO {
	
    @NotBlank(message = "Product name is required")
    private String productName;

    @PositiveOrZero(message = "Quantity must be a positive number or zero")
    private int quantityInStock;

    @NotBlank(message = "Category is required")
    private String category;

    @NotBlank(message = "Supplier is required")
    private String supplier;
}