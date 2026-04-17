package com.catalogo.dragon_ball.service;
import com.catalogo.dragon_ball.dto.ProductoDTO;
import com.catalogo.dragon_ball.entity.Producto;
import com.catalogo.dragon_ball.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class ProductoService {
    
    private final ProductoRepository productoRepository;

    public ProductoDTO crearproducto(ProductoDTO productoRequestDTO){


         if (productoRequestDTO.getNombre()==null || productoRequestDTO.getNombre().trim().isEmpty()) {
        throw new IllegalArgumentException("Por favor, coloquele un nombre a su producto");
        }
         if (productoRequestDTO.getPrecio() == null || productoRequestDTO.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
             throw new IllegalArgumentException("El precio debe de ser mayor a 0");
        }
        if (productoRequestDTO.getStock() == null || productoRequestDTO.getStock() < 0) {
        throw new IllegalArgumentException("El stock debe de ser mayor a 0");
        }
         if (productoRepository.findByNombre(productoRequestDTO.getNombre()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un producto con ese nombre");
        }

        Producto producto= new Producto();    
        producto.setNombre(productoRequestDTO.getNombre());
        producto.setStock(productoRequestDTO.getStock());
        producto.setPrecio(productoRequestDTO.getPrecio());
        producto.setDescripcion(productoRequestDTO.getDescripcion());

        productoRepository.save(producto);

        ProductoDTO response = new ProductoDTO();
        response.setId(producto.getId());
        response.setNombre(productoRequestDTO.getNombre());
        response.setStock(productoRequestDTO.getStock());
        response.setPrecio(productoRequestDTO.getPrecio());
        response.setDescripcion(productoRequestDTO.getDescripcion());

        return response;

    }
        public List<ProductoDTO> obtenerProductos() {
        List<Producto> productos = productoRepository.findAll();
        List<ProductoDTO> listaProductos = new ArrayList<>();

        for (Producto p: productos) {
            ProductoDTO producto = new ProductoDTO();
            producto.setId(p.getId());
            producto.setNombre(p.getNombre());
            producto.setStock(p.getStock());
            producto.setPrecio(p.getPrecio());
            producto.setDescripcion(p.getDescripcion());
            listaProductos.add(producto);
        }
        return listaProductos;
    }

        public Optional<ProductoDTO> obtenerProductos(Long id) {

         if (id == null || id <=0) {
         throw new IllegalArgumentException("El id no puede estar vacío ni ser menor que 0");
        }

        Optional<Producto> productoOptional = productoRepository.findById(id);

        if(productoOptional.isPresent()) {
            Producto producto = productoOptional.get();
            ProductoDTO response = new ProductoDTO();
            response.setId(producto.getId());
            response.setNombre(producto.getNombre());
            response.setStock(producto.getStock());
            response.setPrecio(producto.getPrecio());
            response.setDescripcion(producto.getDescripcion());

            return Optional.of(response);
        } else {
            return Optional.empty();
        }
    }
    
    public Optional<ProductoDTO> actualizarProducto(Long id, ProductoDTO productoRequestDTO) {
        Optional<Producto> productoOptional = productoRepository.findById(id);

        if (productoRequestDTO.getNombre()==null || productoRequestDTO.getNombre().trim().isEmpty()) {
        throw new IllegalArgumentException("Por favor, coloquele un nombre a su producto");
        }
         if (productoRequestDTO.getPrecio() == null || productoRequestDTO.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
             throw new IllegalArgumentException("El precio debe de ser mayor a 0");
        }
        if (productoRequestDTO.getStock() == null || productoRequestDTO.getStock() < 0) {
        throw new IllegalArgumentException("El stock debe de ser mayor a 0");
        }

        if(productoOptional.isPresent()) {
            Producto producto = productoOptional.get();
            producto.setNombre(productoRequestDTO.getNombre());
            producto.setStock(productoRequestDTO.getStock());
            producto.setPrecio(productoRequestDTO.getPrecio());
            producto.setDescripcion(productoRequestDTO.getDescripcion());

            Producto productoActualizado = productoRepository.save(producto);

            ProductoDTO response = new ProductoDTO();
            response.setId(producto.getId());
            response.setNombre(productoActualizado.getNombre());
            response.setStock(productoActualizado.getStock());
            response.setPrecio(productoActualizado.getPrecio());
            response.setDescripcion(productoActualizado.getDescripcion());

            return Optional.of(response);
        } else {
            return Optional.empty();
        }
    }
    public Optional<ProductoDTO> eliminarProducto(Long id) {

         if (id == null || id <=0) {
         throw new IllegalArgumentException("El id no puede estar vacío ni ser menor que 0");
        }

    Optional<Producto> productoOptional = productoRepository.findById(id);
    if (productoOptional.isPresent()) {
        Producto producto = productoOptional.get();

        ProductoDTO response = new ProductoDTO();
        response.setId(producto.getId());
        response.setNombre(producto.getNombre());
        response.setPrecio(producto.getPrecio());
        response.setStock(producto.getStock());
        response.setDescripcion(producto.getDescripcion());

        productoRepository.delete(producto);
        return Optional.of(response);
    } else {
        return Optional.empty();
    }
}
}
