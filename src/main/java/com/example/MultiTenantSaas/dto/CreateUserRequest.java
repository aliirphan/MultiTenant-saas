package com.example.MultiTenantSaas.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record CreateUserRequest (

        @NotBlank(message = "Name is Required")
        String name,

        @NotBlank(message = "Email is Required")
        @Email(message = "message Invalid")
        String email,

        @NotBlank(message = "Password is Required")
        String password,

        UUID tenantId
){
}
