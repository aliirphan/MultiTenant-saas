package com.example.MultiTenantSaas.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateRoleRequest(

        @NotBlank(message = "Role name is required")
        String name
){

}
