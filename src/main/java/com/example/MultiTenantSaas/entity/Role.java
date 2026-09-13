package com.example.MultiTenantSaas.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(
            name = "tenant_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_role_tenant")
    )
    private Tenant tenant;

    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;


    @PrePersist
    public void onCreate(){
        createdAt=LocalDateTime.now();
    }
}
