package com.catalogo.dragon_ball.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleVentaRequestDTO {
    private Long idProducto;
    private Long cantidad;
}