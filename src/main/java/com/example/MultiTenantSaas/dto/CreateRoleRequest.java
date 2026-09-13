package com.example.MultiTenantSaas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateRoleRequest(

        @NotBlank(message = "Role name is required")
        String name,

        @NotNull(message = "Tenant Id is required")
        UUID tenantId
) {
}
