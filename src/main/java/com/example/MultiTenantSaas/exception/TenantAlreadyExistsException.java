package com.example.MultiTenantSaas.exception;

public class TenantAlreadyExistsException extends RuntimeException{

    public TenantAlreadyExistsException(String message){
        super(message);
    }
}
