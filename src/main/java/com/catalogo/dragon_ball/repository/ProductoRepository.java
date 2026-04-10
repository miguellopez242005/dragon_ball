package com.catalogo.dragon_ball.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.catalogo.dragon_ball.entity.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    <optional> Producto findByNombre(String nombre);
}