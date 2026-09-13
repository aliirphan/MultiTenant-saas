package com.example.MultiTenantSaas.exception;

import java.util.UUID;

public class PermissionNotFoundException extends RuntimeException{

    public PermissionNotFoundException(UUID id) {
        super("Permission not found with this id: " + id);

    }

     public PermissionNotFoundException(String name){
     super("Permission not found with this name: " + name);
    }
}
