package com.catalogo.dragon_ball.service;

import com.catalogo.dragon_ball.dto.LoginRequestDTO;
import com.catalogo.dragon_ball.dto.AuthResponseDTO;
import com.catalogo.dragon_ball.entity.Users;
import com.catalogo.dragon_ball.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsuarioRepository usuariosRepository;
    private final JwtService jwtService;

    public Optional<AuthResponseDTO> login(LoginRequestDTO request) {
        Optional<Users> userOpt = usuariosRepository.findByEmail(request.getEmail());
        
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(request.getPassword())) {
            Users user = userOpt.get();
            String token = jwtService.generateToken(user.getId(), user.getName());
            
            return Optional.of(new AuthResponseDTO(
                token, 
                user.getName(), 
                user.getEmail(), 
                user.getRol().getNombre()
            ));
        }
        return Optional.empty();
    }
}