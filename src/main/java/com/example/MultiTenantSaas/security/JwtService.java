package com.example.MultiTenantSaas.security;

import com.example.MultiTenantSaas.entity.Role;
import com.example.MultiTenantSaas.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Service
public class JwtService {


    private final SecretKey secretKey;
    private final long expiration;

    public JwtService(
            @Value("${JWT_SECRET}") String secret,
            @Value("${jwt.expiration}") long expiration
    ) {
        this.secretKey = Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );

        this.expiration = expiration;
    }

    public String generateToken(User user) {

        List<String> roles = user.getRoles()
                .stream()
                .map(Role::getName)
                .toList();

        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("tenant_id", user.getTenant().getId().toString())
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + expiration)
                )
                .signWith(secretKey)
                .compact();
    }

    public Claims extractAllClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUserId(String token) {

        return extractAllClaims(token)
                .getSubject();
    }

    public String extractTenantId(String token) {

        return extractAllClaims(token)
                .get("tenant_id", String.class);
    }

    public List<String> extractRoles(String token) {

        return extractAllClaims(token)
                .get("roles", List.class);
    }
}

