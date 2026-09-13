package com.example.MultiTenantSaas.controller;

import com.example.MultiTenantSaas.dto.CreateRoleRequest;
import com.example.MultiTenantSaas.dto.RoleResponse;
import com.example.MultiTenantSaas.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    //create role
    @PostMapping
    public ResponseEntity<RoleResponse> createRole(@RequestBody @Valid CreateRoleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.createRole(request));

    }

    //get role By id
    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> getRoleById(@PathVariable UUID id){
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    //get all role of tenant
    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<RoleResponse>> getRolesByTenant(@PathVariable UUID tenantId){
        return ResponseEntity.ok(roleService.getRolesByTenant(tenantId));
    }

    //delete role
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(UUID id){
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}
