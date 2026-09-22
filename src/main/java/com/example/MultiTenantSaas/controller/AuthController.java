package com.example.MultiTenantSaas.controller;

import com.example.MultiTenantSaas.dto.LoginRequest;
import com.example.MultiTenantSaas.dto.LoginResponse;
import com.example.MultiTenantSaas.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> Login(@RequestBody @Valid LoginRequest request){
        return ResponseEntity.ok(authService.Login(request));
    }
}
