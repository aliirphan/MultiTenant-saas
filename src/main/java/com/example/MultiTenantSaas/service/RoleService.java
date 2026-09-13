package com.example.MultiTenantSaas.service;

import com.example.MultiTenantSaas.dto.CreateRoleRequest;
import com.example.MultiTenantSaas.dto.RoleResponse;
import com.example.MultiTenantSaas.entity.Role;
import com.example.MultiTenantSaas.entity.Tenant;
import com.example.MultiTenantSaas.exception.RoleAlreadyExistsException;
import com.example.MultiTenantSaas.exception.RoleNotFoundException;
import com.example.MultiTenantSaas.repo.RoleRepo;
import com.example.MultiTenantSaas.repo.TenantRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RoleService {

    private final RoleRepo roleRepo;
    private final TenantRepo tenantRepo;

    public RoleService(RoleRepo roleRepo, TenantRepo tenantRepo) {
        this.roleRepo = roleRepo;
        this.tenantRepo = tenantRepo;
    }

    //create role
    public RoleResponse createRole(CreateRoleRequest request) {
        if (roleRepo.existsByNameAndTenantId(request.name(), request.tenantId())) {
            throw new RoleAlreadyExistsException(request.name());
        }

        Tenant tenant = tenantRepo.findById(request.tenantId())
                .orElseThrow(() -> new RuntimeException("tenant not found with this id :" + request.tenantId()));

        Role role = Role.builder()
                .name(request.name())
                .tenant(tenant)
                .build();

        Role savedRole = roleRepo.save(role);

        return mapToResponse(role);
    }

    //get role By id
    public RoleResponse getRoleById(UUID id) {
        Role role = roleRepo.findById(id)
                .orElseThrow(() -> new RoleNotFoundException(id));

        return mapToResponse(role);
    }

    //get All role of Tenant
    public List<RoleResponse> getRolesByTenant(UUID tenantId) {
        return roleRepo.findAllByTenantId(tenantId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    //delete Role
    public void deleteRole(UUID id) {

        Role role = roleRepo.findById(id)
                .orElseThrow(() -> new RoleNotFoundException(id));

        roleRepo.delete(role);
    }

    //Entity to Dto
    public RoleResponse  mapToResponse(Role role) {
        return new RoleResponse(
                role.getId(),
                role.getName(),
                role.getTenant().getId(),
                role.getCreatedAt()
        );
    }
}
