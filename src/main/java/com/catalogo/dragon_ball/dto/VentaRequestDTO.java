package com.catalogo.dragon_ball.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaRequestDTO {
    private List<DetalleVentaRequestDTO> items;
}
