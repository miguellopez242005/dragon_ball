package com.catalogo.dragon_ball.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleVentaResponseDTO {
    private String nombreProducto;
    private Integer cantidad;
    private BigDecimal precio;
    private BigDecimal subtotal;
}
