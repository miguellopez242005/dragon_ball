package com.catalogo.dragon_ball.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.catalogo.dragon_ball.dto.LoginRequestDTO;
import com.catalogo.dragon_ball.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService; 

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
}