package com.example.MultiTenantSaas.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequest(

        @NotBlank(message = "Name is Required")
        String name,

        @NotBlank(message = "Email is Required")
        @Email(message = "Invalid email")
        String email
) {
}
