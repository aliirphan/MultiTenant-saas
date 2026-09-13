package com.example.MultiTenantSaas.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateTenantRequest(

    @NotBlank(message = "Tenant name is Required")
     String name,

    @NotBlank(message = "Tenant slug is required")
    String slug

){
}
