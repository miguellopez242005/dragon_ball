package com.catalogo.dragon_ball.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleVentaRequestDTO {
    private Integer idProducto;
    private Integer cantidad;
}