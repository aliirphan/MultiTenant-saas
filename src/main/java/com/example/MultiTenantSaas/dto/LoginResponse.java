package com.example.MultiTenantSaas.dto;

public record LoginResponse(

        String accessToken,
        String tokenType
) {

}
