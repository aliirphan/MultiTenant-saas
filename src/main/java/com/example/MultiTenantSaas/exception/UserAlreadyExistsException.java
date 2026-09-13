package com.example.MultiTenantSaas.exception;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(String mesaage){
        super(mesaage);
    }
}
