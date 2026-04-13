package com.catalogo.dragon_ball.service;

import com.catalogo.dragon_ball.dto.UsuarioRequestDTO;
import com.catalogo.dragon_ball.dto.UsuarioResponseDTO;
import com.catalogo.dragon_ball.entity.Roles;
import com.catalogo.dragon_ball.entity.Users;
import com.catalogo.dragon_ball.repository.RolRepository;
import com.catalogo.dragon_ball.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository UsuarioRepository;
    private final RolRepository rolRepository;

    public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO dto) {
        Users usuario = new Users();
        usuario.setName(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(dto.getPassword().toString());
        
        Roles rolExistente = rolRepository.findById(dto.getIdRol())
            .orElseThrow(() -> new RuntimeException("Error: El rol no existe en la tienda"));

        usuario.setRol(rolExistente);
        UsuarioRepository.save(usuario);

        return mapearADTO(usuario);
    }

    public List<UsuarioResponseDTO> obtenerUsuarios() {
        return UsuarioRepository.findAll().stream()
                .map(this::mapearADTO)
                .collect(Collectors.toList());
    }

    public Optional<UsuarioResponseDTO> obtenerUsuario(Long id) {
        return UsuarioRepository.findById(id).map(this::mapearADTO);
    }

    private UsuarioResponseDTO mapearADTO(Users user) {
        UsuarioResponseDTO res = new UsuarioResponseDTO();
        res.setId(user.getId());
        res.setNombre(user.getName());
        res.setCorreo(user.getEmail());
        if (user.getRol() != null) {
            res.setRol(user.getRol().getId());
        }
        return res;
    }

    public Optional<UsuarioResponseDTO> actualizarUsuario(Long id, UsuarioRequestDTO dto) {
        Optional<Users> usuarioOptional = UsuarioRepository.findById(id);
        
        if (usuarioOptional.isPresent()) {
            Users usuario = usuarioOptional.get();
            
            usuario.setName(dto.getNombre());
            usuario.setEmail(dto.getEmail());
            usuario.setPassword(dto.getPassword().toString()); 

            UsuarioRepository.save(usuario);
            return Optional.of(mapearADTO(usuario));
        }
        return Optional.empty();
    }

    public boolean eliminarUsuario(Long id) {
        if (UsuarioRepository.existsById(id)) {
            UsuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }
}