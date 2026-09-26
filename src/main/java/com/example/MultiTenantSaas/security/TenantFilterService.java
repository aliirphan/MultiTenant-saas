package com.example.MultiTenantSaas.security;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TenantFilterService {

    @PersistenceContext
    private EntityManager entityManager;

    public void enableTenantFilter(){
        UUID tenantId = TenantContext.getTenantId();

        if (tenantId == null){
            throw new IllegalStateException("Tenant ID is missing from TenantContext");
        }

        Session session = entityManager.unwrap(Session.class);

        session.enableFilter("tenantFilter").setParameter("tenantId", tenantId);
    }
}
