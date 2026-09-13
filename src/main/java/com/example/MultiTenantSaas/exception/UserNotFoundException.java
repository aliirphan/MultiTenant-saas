package com.example.MultiTenantSaas.exception;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(UUID id){
        super("User id not found with this id" + id);
    }
}
