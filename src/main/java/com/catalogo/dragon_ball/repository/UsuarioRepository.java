package com.catalogo.dragon_ball.repository;

import com.catalogo.dragon_ball.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
}