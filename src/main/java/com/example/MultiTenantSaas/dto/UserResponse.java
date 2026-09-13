package com.example.MultiTenantSaas.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse(

        UUID id,
        String name,
        String email,
        boolean active,
        UUID tenantId,
        LocalDateTime createdAt

){

}
