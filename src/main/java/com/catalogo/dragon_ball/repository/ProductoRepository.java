package com.catalogo.dragon_ball.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.catalogo.dragon_ball.entity.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Optional <Producto> findByNombre(String nombre);
}