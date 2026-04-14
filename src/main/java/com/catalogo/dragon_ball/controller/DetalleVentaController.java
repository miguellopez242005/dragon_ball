package com.catalogo.dragon_ball.controller;

import com.catalogo.dragon_ball.dto.DetalleVentaRequestDTO;
import com.catalogo.dragon_ball.dto.DetalleVentaResponseDTO;
import com.catalogo.dragon_ball.dto.ProductoDTO;
import com.catalogo.dragon_ball.service.DetalleVentaService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/DetalleVenta")
public class DetalleVentaController {
     private final DetalleVentaService detalleVentaService;

    @PostMapping
    public ResponseEntity<DetalleVentaResponseDTO> crearDetalleVenta(@RequestBody DetalleVentaRequestDTO detalleVentaRequestDTO) {
        DetalleVentaResponseDTO response = detalleVentaService.crearDetalleVenta(detalleVentaRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<DetalleVentaResponseDTO>> obtenerDetallesVenta() {
        List<DetalleVentaResponseDTO> ObtenerDetallesVenta = detalleVentaService.obtenerDetallesVenta();
        return ResponseEntity.status(HttpStatus.FOUND).body(ObtenerDetallesVenta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleVentaResponseDTO> obtenerDetallesVentaporID(@PathVariable Long id) {
        DetalleVentaResponseDTO response = detalleVentaService.obtenerDetallesVenta(id).orElse(null);
        if (response != null) {
            return ResponseEntity.status(HttpStatus.FOUND).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleVentaResponseDTO> actualizarDetallesVenta(@PathVariable Long id, @RequestBody DetalleVentaRequestDTO detalleVentaRequestDTO) {
        DetalleVentaResponseDTO response = detalleVentaService.actualizarDetallesVenta(id, detalleVentaRequestDTO).orElse(null);
        if (response != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
     @DeleteMapping("/{id}")
    public ResponseEntity<DetalleVentaResponseDTO> eliminarDetallesVenta(@PathVariable Long id){
        DetalleVentaResponseDTO response= detalleVentaService.eliminarDetallesVenta(id).orElse(null);
        if (response !=null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);   
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}

