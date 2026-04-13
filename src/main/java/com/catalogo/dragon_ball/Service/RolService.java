package com.catalogo.dragon_ball.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catalogo.dragon_ball.dto.RolDTO;
import com.catalogo.dragon_ball.entity.Roles;
import com.catalogo.dragon_ball.repository.RolRepository;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    public List<RolDTO> listarTodos() {
        return rolRepository.findAll().stream()
                .map(rol -> new RolDTO(rol.getId(), rol.getNombre()))
                .toList();
    }

    public RolDTO guardar(RolDTO dto) {
        Roles rol = new Roles();
        rol.setNombre(dto.getNombre());
        Roles guardado = rolRepository.save(rol);
        return new RolDTO(guardado.getId(), guardado.getNombre());
    }

    public RolDTO buscarPorId(Long id) {
        Roles rol = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        return new RolDTO(rol.getId(), rol.getNombre());
    }

    public void eliminar(Long id) {
        rolRepository.deleteById(id);
    }
}