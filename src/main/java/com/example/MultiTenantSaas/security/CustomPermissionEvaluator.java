package com.example.MultiTenantSaas.security;

import com.example.MultiTenantSaas.entity.Permission;
import com.example.MultiTenantSaas.entity.Role;
import com.example.MultiTenantSaas.entity.User;
import com.example.MultiTenantSaas.repo.UserRepo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
public class CustomPermissionEvaluator {

    private final UserRepo userRepo;

    public CustomPermissionEvaluator(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Transactional(readOnly = true)
    public boolean hasPermission(String permissionName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        String userId = authentication.getName();

        if (userId == null) {
            return false;
        }

        UUID tenantId = TenantContext.getTenantId();

        if (tenantId == null){
            return false;
        }

        User user = userRepo.findByIdAndTenantId(UUID.fromString(userId),tenantId).orElse(null);

        if (user == null) {
            return false;
        }

        for (Role role : user.getRoles()) {
            for (Permission permission : role.getPermision()) {

                if (permission.getName().equals(permissionName)) ;
                return true;
            }
        }

        return false;
    }
}
