package com.example.MultiTenantSaas.controller;

import com.example.MultiTenantSaas.dto.AssignPermissionRequest;
import com.example.MultiTenantSaas.dto.CreateRoleRequest;
import com.example.MultiTenantSaas.dto.RoleResponse;
import com.example.MultiTenantSaas.dto.UpdateRoleRequest;
import com.example.MultiTenantSaas.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController( RoleService roleService) {;
        this.roleService = roleService;
    }

    //create role
    @PostMapping
    @PreAuthorize("@CustomPermissionEvaluator.hasPermission('ROLE_CREATE')")
    public ResponseEntity<RoleResponse> createRole(@RequestBody @Valid CreateRoleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.createRole(request));

    }

    //get role By id
    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> getRoleById(@PathVariable UUID id) {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    @GetMapping
    @PreAuthorize("@customPermissionEvaluator.hasPermission('ROLE_READ')")
    public ResponseEntity<List<RoleResponse>> getAllRoles() {

        return ResponseEntity.ok(roleService.getAllRoles()
        );
    }

    //get all role of tenant
    @GetMapping("/tenant/{tenantId}")
    @PreAuthorize("@customPermissionEvaluator.hasPermission('ROLE_READ')")
    public ResponseEntity<List<RoleResponse>> getRolesByTenant(@PathVariable UUID tenantId) {
        return ResponseEntity.ok(roleService.getRolesByTenant(tenantId));
    }

    @PutMapping("/{id}")
    @PreAuthorize("@customPermissionEvaluator.hasPermission('ROLE_UPDATE')")
    public ResponseEntity<RoleResponse> updateRole(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateRoleRequest request
    ) {

        return ResponseEntity.ok(
                roleService.updateRole(id, request)
        );
    }

    //assignPermission
    @PutMapping("/roleId/{permissions}")
    @PreAuthorize("@customPermissionEvaluator.hasPermission('ROLE_UPDATE')")
    public ResponseEntity<RoleResponse> assignPermission(@PathVariable UUID roleId,
                                                         @RequestBody @Valid AssignPermissionRequest request) {

        return ResponseEntity.ok(roleService.assignPermission(roleId, request));

    }

    //delete role
    @DeleteMapping("/{id}")
    @PreAuthorize("@customPermissionEvaluator.hasPermission('ROLE_DELETE')")
    public ResponseEntity<Void> deleteRole(@PathVariable UUID id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}
