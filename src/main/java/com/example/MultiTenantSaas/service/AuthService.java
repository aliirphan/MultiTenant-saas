package com.example.MultiTenantSaas.service;

import com.example.MultiTenantSaas.dto.LoginRequest;
import com.example.MultiTenantSaas.dto.LoginResponse;
import com.example.MultiTenantSaas.entity.User;
import com.example.MultiTenantSaas.repo.UserRepo;
import com.example.MultiTenantSaas.security.JwtService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    public AuthService(UserRepo userRepo, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponse Login(LoginRequest request){
        User user = userRepo.findByEmail(request.email())
                .orElseThrow(() -> new BadCredentialsException("Invalid Email or Password"));

        if (!user.isActive()){
            throw new BadCredentialsException("User account is inActive");
        }

        if (!passwordEncoder.matches(request.password(), user.getPassword())){
            throw new BadCredentialsException("Invalid Email or Password");
        }

        String token = jwtService.generateToken(user);

        return new LoginResponse(token,"Bearer");
    }
}
