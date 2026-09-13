package com.example.MultiTenantSaas.repo;

import com.example.MultiTenantSaas.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<User, UUID> {


    Optional<User> findByEmailAndTenantId(String email, UUID tenantId);

    boolean existsByEmailAndTenantId(String email, UUID tenantId);

    List<User> findAllByTenantId(UUID tenantId);
}
