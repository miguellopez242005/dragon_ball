package com.catalogo.dragon_ball.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


@Service
public class JwtService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.token-expiration}")
    private Long tokenExpiration;

    /**
     * Genera un nuevo token JWT incluyendo la lista de roles (authorities).
     */
    public String generateToken(Long userId, String username, List<String> roles) {
        return Jwts.builder()
                .claims(Map.of(
                    "userId", userId,
                    "authorities", roles 
                )) 
                .subject(username) 
                .issuedAt(new Date()) 
                .expiration(new Date(System.currentTimeMillis() + tokenExpiration)) 
                .signWith(getSigningKey()) 
                .compact(); 
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Boolean isTokenValid(String token) {
        try {
            Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public <T> T exctractClaims(String token, Function<Claims, T> resolver) {
        final Claims claims = Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();

        return resolver.apply(claims);
    }

    public String extractUsername(String token) {
        return exctractClaims(token, Claims::getSubject);
    }

    public Long extractUserId(String token) {
        return exctractClaims(token, claims -> claims.get("userId", Long.class));
    }

    public List<String> extractRoles(String token) {
        return exctractClaims(token, claims -> claims.get("authorities", List.class));
    }

    /**
     * Refresca el token recuperando los roles del token anterior.
     */
    public String refreshToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        } catch (ExpiredJwtException e) {
            claims = e.getClaims();
        } catch (JwtException e) {
            throw new RuntimeException("El token es inválido o ha sido manipulado");
        }

        List<String> roles = claims.get("authorities", List.class);

        return generateToken(claims.get("userId", Long.class), claims.getSubject(), roles);
    }
}