package com.example.MultiTenantSaas.service;

import com.example.MultiTenantSaas.dto.CreateUserRequest;
import com.example.MultiTenantSaas.dto.UpdateUserRequest;
import com.example.MultiTenantSaas.dto.UserResponse;
import com.example.MultiTenantSaas.entity.Tenant;
import com.example.MultiTenantSaas.entity.User;
import com.example.MultiTenantSaas.exception.UserAlreadyExistsException;
import com.example.MultiTenantSaas.exception.UserNotFoundException;
import com.example.MultiTenantSaas.repo.TenantRepo;
import com.example.MultiTenantSaas.repo.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final TenantRepo tenantRepo;


    public UserService(UserRepo userRepo, TenantRepo tenantRepo) {
        this.userRepo = userRepo;
        this.tenantRepo = tenantRepo;
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
                .password(request.password())
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
    public List<UserResponse> getAllUser() {
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


    //Entity to DTO conversion
    private UserResponse mapToResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.isActive(),
                user.getTenant().getId(),
                user.getCreated_at()


        );
    }
}
