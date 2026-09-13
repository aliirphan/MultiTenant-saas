package com.example.MultiTenantSaas.service;

import com.example.MultiTenantSaas.dto.PermissionRequest;
import com.example.MultiTenantSaas.dto.PermissionResponse;
import com.example.MultiTenantSaas.entity.Permission;
import com.example.MultiTenantSaas.exception.PermissionAlreadyExistsException;
import com.example.MultiTenantSaas.exception.PermissionNotFoundException;
import com.example.MultiTenantSaas.repo.PermissionRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PermissionService {

    private final PermissionRepo permissionRepo;

    public PermissionService(PermissionRepo permissionRepo) {
        this.permissionRepo = permissionRepo;
    }

    //create permission
    public PermissionResponse createPermission(PermissionRequest request) {

        if (permissionRepo.existsByName(request.getName())) {
            throw new PermissionAlreadyExistsException(request.getName());
        }

        Permission permission = Permission.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();

        Permission savedPermission = permissionRepo.save(permission);
        return mapToResponse(savedPermission);
    }

    //get all
    public List<PermissionResponse> getAllPermissions() {
        return permissionRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    //get by id
    public PermissionResponse getPermissionById(UUID id) {
        Permission permission = permissionRepo.findById(id)
                .orElseThrow(() -> new PermissionNotFoundException(id));

        return mapToResponse(permission);

    }

    //get By name
    public PermissionResponse getPermissionByName(String name) {
        Permission permission = permissionRepo.findByName(name)
                .orElseThrow(() -> new PermissionNotFoundException(name));

        return mapToResponse(permission);
    }

    //delete
    public void deletePermission(UUID id) {
        Permission permission = permissionRepo.findById(id)
                .orElseThrow(() -> new PermissionNotFoundException(id));

        permissionRepo.delete(permission);
    }


    //Entity --> DTO
    public PermissionResponse mapToResponse(Permission permission) {
        return PermissionResponse.builder()
                .id(permission.getId())
                .name(permission.getName())
                .description(permission.getDescription())
                .build();
    }
}
