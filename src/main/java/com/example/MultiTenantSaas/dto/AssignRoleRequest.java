package com.example.MultiTenantSaas.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.UUID;

public record AssignRoleRequest(

        @NotBlank(message = "Role id is required")
        List<UUID> roleId

) {
}
