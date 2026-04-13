package com.catalogo.dragon_ball.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.catalogo.dragon_ball.entity.Venta;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    Optional<Venta> findByUsuarioName(String nombre);
}