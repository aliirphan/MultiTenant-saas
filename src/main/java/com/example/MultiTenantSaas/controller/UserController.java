package com.example.MultiTenantSaas.controller;

import com.example.MultiTenantSaas.dto.AssignRoleRequest;
import com.example.MultiTenantSaas.dto.CreateUserRequest;
import com.example.MultiTenantSaas.dto.UpdateUserRequest;
import com.example.MultiTenantSaas.dto.UserResponse;
import com.example.MultiTenantSaas.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    //Create
    @PostMapping
    @PreAuthorize("@CustomPermissionEvaluator.hasPermission('USER_CREATE')")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@RequestBody @Valid CreateUserRequest request) {
        return userService.createUser(request);
    }

    //get by id
    @GetMapping("/{id}")
    @PreAuthorize("@CustomPermissionEvaluator.hasPermission('USER_READ')")
    public UserResponse getUserById(@PathVariable UUID id) {
        return userService.getUserById(id);
    }

    //get all user
    @GetMapping
    @PreAuthorize("@CustomPermissionEvaluator.hasPermission('USER_READ')")
    public List<UserResponse> getAllUser(){
        return userService.getAllUser();
    }

    //get user by tenant
    @GetMapping("/tenant/{tenantId}")
    public List<UserResponse> getUserByTenant(@PathVariable UUID tenantId){
        return userService.getUserByTenant(tenantId);
    }

    //update
    @PutMapping("/{id}")
    @PreAuthorize("@CustomPermissionEvaluator.hasPermission('USER_UPDATE')")
    public UserResponse updateUser(@PathVariable UUID id, @RequestBody @Valid UpdateUserRequest request){
        return userService.updateUser(id,request);
    }

    //deactivate
    @DeleteMapping("/{id}/deactivate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("@CustomPermissionEvaluator.hasPermission('USER_DELETE')")
    public void deactivateUser(@PathVariable UUID id){
        userService.DeactivateUser(id);
    }

    @PutMapping("/{userId}/roles")
    @PreAuthorize("@customPermissionEvaluator.hasPermission('USER_UPDATE')")
    public UserResponse assignRoles(@PathVariable UUID userId, @RequestBody @Valid AssignRoleRequest request){
        return userService.assignRole(userId, request);
    }
}
