package com.example.MultiTenantSaas.service;

import com.example.MultiTenantSaas.dto.CreateTenantRequest;
import com.example.MultiTenantSaas.dto.TenantResponse;
import com.example.MultiTenantSaas.entity.Tenant;
import com.example.MultiTenantSaas.exception.TenantAlreadyExistsException;
import com.example.MultiTenantSaas.repo.TenantRepo;
import org.springframework.stereotype.Service;

@Service
public class TenantService {

    private final TenantRepo tenantRepo;

    public TenantService(TenantRepo tenantRepo) {
        this.tenantRepo = tenantRepo;
    }

    public TenantResponse createTenant(CreateTenantRequest request){

        if (tenantRepo.existsBySlug(request.slug())){
            throw new TenantAlreadyExistsException("Tenant with slug " + request.slug() + "already exists");
        }

        Tenant tenant= Tenant.builder()
                .name(request.name())
                .slug(request.slug())
                .active(true)
                .build();

        Tenant savedTenant= tenantRepo.save(tenant);

        return mapToResponse(savedTenant);
    }

    public TenantResponse mapToResponse(Tenant tenant){
        return new TenantResponse(
                tenant.getId(),
                tenant.getName(),
                tenant.getSlug(),
                tenant.isActive(),
                tenant.getCreatedAt()
        );
    }
}
