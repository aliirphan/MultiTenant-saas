package com.example.MultiTenantSaas.controller;

import com.example.MultiTenantSaas.dto.CreateTenantRequest;
import com.example.MultiTenantSaas.dto.TenantResponse;
import com.example.MultiTenantSaas.service.TenantService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tenants")
public class TenantController {

    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TenantResponse createTenant(@Valid @RequestBody CreateTenantRequest request){
        return tenantService.createTenant(request);
    }
}
