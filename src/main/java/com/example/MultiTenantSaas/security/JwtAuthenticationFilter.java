package com.example.MultiTenantSaas.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final TenantFilterService tenantFilterService;

    public JwtAuthenticationFilter(JwtService jwtService, TenantFilterService tenantFilterService) {
        this.jwtService = jwtService;
        this.tenantFilterService = tenantFilterService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);

        }

        String token = authHeader.substring(7);

        try {
            Claims claims = jwtService.extractAllClaims(token);

            String userId = claims.getSubject();

            String tenantId = claims.get("tenant_id", String.class);

            TenantContext.setTenantId(UUID.fromString(tenantId));

            tenantFilterService.enableTenantFilter();

            List<String> roles = claims.get("roles", List.class);

            //store tenant in TenantContext
            TenantContext.setTenantId(UUID.fromString(tenantId));

            //convert roles into spring security authorities
            List<SimpleGrantedAuthority> authorities = roles
                    .stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE" + role))
                    .toList();

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userId,
                    null,
                    authorities);

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }

    }
}
