package com.example.MultiTenantSaas.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;


@Configuration
public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(
                HttpSecurity http
        ) throws Exception {

            http
                    .csrf(AbstractHttpConfigurer::disable)

                    .sessionManagement(session ->
                            session.sessionCreationPolicy(
                                    SessionCreationPolicy.STATELESS
                            )
                    )

                    .authorizeHttpRequests(auth -> auth

                            // Public endpoints
                            .requestMatchers(
                                    "/api/v1/auth/**",
                                    "/api/v1/tenants",
                                    "/api/v1/users"
                            ).permitAll()

                            // Everything else requires authentication
                            .anyRequest().authenticated()
                    );

            return http.build();
        }
    }
