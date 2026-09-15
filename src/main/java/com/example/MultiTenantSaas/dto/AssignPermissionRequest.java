package com.example.MultiTenantSaas.dto;


import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.UUID;

public record AssignPermissionRequest(

        @NotBlank(message = "Permission Id are required")
        List<UUID> permissionIds
) {
}
