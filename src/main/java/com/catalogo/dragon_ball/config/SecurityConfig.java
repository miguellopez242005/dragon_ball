package com.catalogo.dragon_ball.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.catalogo.dragon_ball.filter.JwtValidationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final JwtValidationFilter jwtValidationFilter;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // OBLIGATORIO para que Postman funcione
            .authorizeHttpRequests(auth -> auth
                // Aquí le das permiso al primer guardia para dejarte pasar a crear usuarios
                .requestMatchers("/api/usuarios/**").permitAll() 
                .requestMatchers("/api/auth/**").permitAll() 
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtValidationFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
}