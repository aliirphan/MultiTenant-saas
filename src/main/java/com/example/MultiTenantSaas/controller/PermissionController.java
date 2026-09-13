package com.example.MultiTenantSaas.controller;

import com.example.MultiTenantSaas.dto.PermissionRequest;
import com.example.MultiTenantSaas.dto.PermissionResponse;
import com.example.MultiTenantSaas.service.PermissionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/permissions")
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostMapping
    public ResponseEntity<PermissionResponse> createPermission(@RequestBody @Valid PermissionRequest request) {
        PermissionResponse response = permissionService.createPermission(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //get permission by id
    @GetMapping("/{id}")
    public ResponseEntity<PermissionResponse> getPermissionById(@PathVariable UUID id) {
        return ResponseEntity.ok(permissionService.getPermissionById(id));
    }

    //get permission by name
    @GetMapping("/name/{name}")
    public ResponseEntity<PermissionResponse> getPermissionByName(@PathVariable String name) {
        return ResponseEntity.ok(permissionService.getPermissionByName(name));
    }

    //get ALl

    @GetMapping
    public ResponseEntity<List<PermissionResponse>> getAll() {
        return ResponseEntity.ok(permissionService.getAllPermissions());
    }

    //delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {

        permissionService.deletePermission(id);

        return ResponseEntity.noContent().build();
    }
}
