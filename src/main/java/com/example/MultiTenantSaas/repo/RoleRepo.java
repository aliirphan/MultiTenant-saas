package com.example.MultiTenantSaas.repo;

import com.example.MultiTenantSaas.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepo extends JpaRepository<Role, UUID> {

    boolean existsByNameAndTenantId(String name, UUID TenantId);

    Optional<Role> findByNameAndTenantId(String name, UUID tenantId);

    List<Role> findAllByTenantId(UUID tenantId);
}
