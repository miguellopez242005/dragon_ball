package com.catalogo.dragon_ball.Service;
import com.catalogo.dragon_ball.dto.ProductoDTO;
import com.catalogo.dragon_ball.entity.Producto;
import com.catalogo.dragon_ball.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class ProductoService {
    
    private final ProductoRepository productoRepository;

    public ProductoDTO crearproducto(ProductoDTO productoRequestDTO){
        Producto producto= new Producto();
        
        producto.setNombre(productoRequestDTO.getNombre());
        producto.setStock(productoRequestDTO.getStock());
        producto.setPrecio(productoRequestDTO.getPrecio());

        productoRepository.save(producto);

        ProductoDTO response = new ProductoDTO();
        response.setId(producto.getId());
        response.setNombre(productoRequestDTO.getNombre());
        response.setStock(productoRequestDTO.getStock());
        response.setPrecio(productoRequestDTO.getPrecio());

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
            listaProductos.add(producto);
        }
        return listaProductos;
    }

        public Optional<ProductoDTO> obtenerProductos(String nombre) {
        Optional<Producto> productoOptional = productoRepository.findByNombre(nombre);

        if(productoOptional.isPresent()) {
            Producto producto = productoOptional.get();
            ProductoDTO response = new ProductoDTO();
            response.setId(producto.getId());
            response.setNombre(producto.getNombre());
            response.setStock(producto.getStock());
            response.setPrecio(producto.getPrecio());

            return Optional.of(response);
        } else {
            return Optional.empty();
        }
    }

    
    public Optional<ProductoDTO> actualizarProducto(String nombre, ProductoDTO productoRequestDTO) {
        Optional<Producto> productoOptional = productoRepository.findByNombre(nombre);
        if(productoOptional.isPresent()) {
            Producto producto = productoOptional.get();
            producto.setNombre(productoRequestDTO.getNombre());
            producto.setStock(productoRequestDTO.getStock());
            producto.setPrecio(productoRequestDTO.getPrecio());

            Producto productoActualizado = productoRepository.save(producto);

            ProductoDTO response = new ProductoDTO();
            response.setId(producto.getId());
            response.setNombre(productoActualizado.getNombre());
            response.setStock(productoActualizado.getStock());
            response.setPrecio(productoActualizado.getPrecio());

            return Optional.of(response);
        } else {
            return Optional.empty();
        }
    }
    public Optional<ProductoDTO> eliminarProducto(String nombre) {
    Optional<Producto> productoOptional = productoRepository.findByNombre(nombre);

    if (productoOptional.isPresent()) {
        Producto producto = productoOptional.get();

        ProductoDTO response = new ProductoDTO();
        response.setId(producto.getId());
        response.setNombre(producto.getNombre());
        response.setPrecio(producto.getPrecio());
        response.setStock(producto.getStock());

        productoRepository.delete(producto);
        return Optional.of(response);
    } else {
        return Optional.empty();
    }
}
}
