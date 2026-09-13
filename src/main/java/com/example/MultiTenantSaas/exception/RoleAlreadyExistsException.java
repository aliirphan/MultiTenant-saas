package com.example.MultiTenantSaas.exception;

public class RoleAlreadyExistsException extends RuntimeException {
    public RoleAlreadyExistsException(String name) {
        super("Role already with this name :" + name);
    }
}
