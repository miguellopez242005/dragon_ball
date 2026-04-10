package com.catalogo.dragon_ball.repository;

import com.catalogo.dragon_ball.entity.Roles;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Roles, Integer> {
    Optional<Roles> findById(Long id);
} 