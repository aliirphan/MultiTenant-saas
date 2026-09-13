package com.example.MultiTenantSaas.exception;

public class PermissionAlreadyExistsException extends RuntimeException{

    public PermissionAlreadyExistsException(String name){
        super("Permission already exists with this name:" + name);
    }
}
