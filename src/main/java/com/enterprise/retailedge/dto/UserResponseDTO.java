package com.enterprise.retailedge.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class UserResponseDTO {
    private UUID userId;
    private String username;
    private String name;
    private String email;
    private boolean isActive;
}