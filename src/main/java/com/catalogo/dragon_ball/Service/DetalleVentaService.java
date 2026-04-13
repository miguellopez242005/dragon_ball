package com.catalogo.dragon_ball.Service;

import com.catalogo.dragon_ball.dto.DetalleVentaRequestDTO;
import com.catalogo.dragon_ball.dto.DetalleVentaResponseDTO;
import com.catalogo.dragon_ball.repository.DeatlleVentaRepository;
import com.catalogo.dragon_ball.entity.DetalleVenta;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DetalleVentaService {
    private final DeatlleVentaRepository deatlleVentaRepository;

    public DetalleVentaResponseDTO crearDetalleventa(DetalleVentaRequestDTO detalleVentaRequestDTO){
        
    }
}
