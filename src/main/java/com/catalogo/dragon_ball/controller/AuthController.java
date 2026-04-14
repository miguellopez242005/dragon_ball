package com.catalogo.dragon_ball.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.catalogo.dragon_ball.dto.LoginRequestDTO;
import com.catalogo.dragon_ball.service.AuthService;
import com.catalogo.dragon_ball.service.JwtService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService; 

@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginDto) {
    return authService.login(loginDto)
        .map(response -> {
            return ResponseEntity.ok(response); 
        })
        .orElseGet(() -> {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        });
}
@PostMapping("/refresh")
public ResponseEntity<?> refresh(@RequestHeader("Authorization") String authHeader) {
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token faltante");
    }

    String token = authHeader.substring(7);
    try {
        String newToken = jwtService.refreshToken(token);
        return ResponseEntity.ok(Map.of("token", newToken));
    } catch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
}
}