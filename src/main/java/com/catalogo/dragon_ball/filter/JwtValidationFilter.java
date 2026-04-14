package com.catalogo.dragon_ball.filter;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.catalogo.dragon_ball.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtValidationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    @Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
    
    String authHeader = request.getHeader("Authorization");

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        filterChain.doFilter(request, response); // Deja pasar para que Spring Security maneje el 403
        return;
    }

    String token = authHeader.substring(7);
    try {
        if (jwtService.isTokenValid(token)) {
            String username = jwtService.extractUsername(token);
            
            // 1. Crear la autenticación (Lo que quita el 403)
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                username, null, new ArrayList<>()
            );
            
            // 2. Avisarle a Spring que el usuario es válido
            SecurityContextHolder.getContext().setAuthentication(authToken);

            // 3. Guardar atributos opcianos por si los usas en el Controller
            request.setAttribute("username", username);
            request.setAttribute("userId", jwtService.extractUserId(token));
        }
    } catch (Exception e) {
        System.out.println("Error validando token: " + e.getMessage());
    }

    // 4. SIEMPRE debe ir esto al final para que la petición siga su camino
    filterChain.doFilter(request, response);
}


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        System.out.println("Ruta recibida por el filtro: " + path);
        return path.contains("/api/usuarios") || path.startsWith("/api/auth/login");
    }
}