package com.example.MultiTenantSaas.service;

import com.example.MultiTenantSaas.dto.AssignRoleRequest;
import com.example.MultiTenantSaas.dto.CreateUserRequest;
import com.example.MultiTenantSaas.dto.UpdateUserRequest;
import com.example.MultiTenantSaas.dto.UserResponse;
import com.example.MultiTenantSaas.entity.Role;
import com.example.MultiTenantSaas.entity.Tenant;
import com.example.MultiTenantSaas.entity.User;
import com.example.MultiTenantSaas.exception.UserAlreadyExistsException;
import com.example.MultiTenantSaas.exception.UserNotFoundException;
import com.example.MultiTenantSaas.repo.RoleRepo;
import com.example.MultiTenantSaas.repo.TenantRepo;
import com.example.MultiTenantSaas.repo.UserRepo;
import com.example.MultiTenantSaas.security.TenantFilterService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final TenantFilterService tenantFilterService;
    private final UserRepo userRepo;
    private final TenantRepo tenantRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;


    public UserService(TenantFilterService tenantFilterService, UserRepo userRepo, TenantRepo tenantRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        this.tenantFilterService = tenantFilterService;
        this.userRepo = userRepo;
        this.tenantRepo = tenantRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    //create user
    public UserResponse createUser(CreateUserRequest request) {
        Tenant tenant = tenantRepo.findById(request.tenantId())
                .orElseThrow(() -> new RuntimeException("Tenant not found with this id" + request.tenantId()));


        if (userRepo.existsByEmailAndTenantId(
                request.email(),
                request.tenantId())) {
            throw new UserAlreadyExistsException("User with email" + request.email() + "already exists in this tenant");
        }

        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .active(true)
                .tenant(tenant)
                .build();

        User savedUser = userRepo.save(user);
        return mapToResponse(savedUser);


    }

    //get userById
    public UserResponse getUserById(UUID id) {

        User user1 = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return mapToResponse(user1);

    }

    //  getAllUser
    @Transactional(readOnly=true)
    public List<UserResponse> getAllUser() {

        tenantFilterService.enableTenantFilter();

        return userRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    //get User of Tenant
    public List<UserResponse> getUserByTenant(UUID tenantId) {
        return userRepo.findAllByTenantId(tenantId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    //update user
    public UserResponse updateUser(UUID id, UpdateUserRequest request) {

        User user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        user.setName(request.name());
        user.setEmail(request.email());

        User updatedUser = userRepo.save(user);

        return mapToResponse(updatedUser);
    }

    //deactivate user
    public void DeactivateUser(UUID id) {

        User user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        user.setActive(false);
        userRepo.save(user);
    }

    //assign role to user
    public UserResponse assignRole(UUID userId, AssignRoleRequest request) {
        User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        List<Role> roles = roleRepo.findAllById(request.roleId());

        if (roles.size() != request.roleId().size()) {
            throw new RuntimeException("One or more role not found");
        }

        //check that all the roles belongs to the same tenant as user
        for (Role role : roles) {
            if (!role.getTenant().getId().equals(user.getTenant().getId())) {
                throw new RuntimeException("User and Role must belong to the same tenant");
            }
        }

        user.getRoles().addAll(roles);
        User savedUser = userRepo.save(user);
        return mapToResponse(savedUser);

    }


    //Entity to DTO conversion
    private UserResponse mapToResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.isActive(),
                user.getTenant().getId(),
                user.getCreated_at(),
                user.getRoles().
                        stream()
                        .map(Role::getId)
                        .toList()


        );
    }
}
