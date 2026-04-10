package com.catalogo.dragon_ball.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.catalogo.dragon_ball.entity.Venta;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Integer> {
    <optional> Venta findByNombre(String nombre);
}