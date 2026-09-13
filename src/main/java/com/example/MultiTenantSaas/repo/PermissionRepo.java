package com.example.MultiTenantSaas.repo;

import com.example.MultiTenantSaas.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PermissionRepo extends JpaRepository<Permission, UUID> {

    Optional<Permission> findByName(String name);

    boolean existsByName(String name);

}
