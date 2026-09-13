package com.example.MultiTenantSaas.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record TenantResponse (

     UUID id,
     String name,
     String slug,
     boolean active,
     LocalDateTime createdAt
){
}
