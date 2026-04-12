package com.catalogo.dragon_ball.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequestDTO {
    private String nombre;
    private String email;
    private String password;
    private Integer idRol;
}