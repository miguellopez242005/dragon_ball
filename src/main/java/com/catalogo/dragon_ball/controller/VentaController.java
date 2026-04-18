package com.catalogo.dragon_ball.controller;

import com.catalogo.dragon_ball.dto.VentaRequestDTO;
import com.catalogo.dragon_ball.dto.VentaResponseDTO;
import com.catalogo.dragon_ball.entity.Users;
import com.catalogo.dragon_ball.repository.UsuarioRepository;
import com.catalogo.dragon_ball.service.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/ventas")
public class VentaController {

    private final VentaService ventaService;
    private final UsuarioRepository usuarioRepository;

    @PostMapping("/{idUsuario}")
    public ResponseEntity<VentaResponseDTO> procesarVenta(
            @PathVariable Long idUsuario,
            @RequestBody VentaRequestDTO request) {

        Users usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        VentaResponseDTO response = ventaService.procesarVenta(request, usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}