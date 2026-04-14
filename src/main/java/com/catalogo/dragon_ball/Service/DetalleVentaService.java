package com.catalogo.dragon_ball.service;
import com.catalogo.dragon_ball.dto.DetalleVentaRequestDTO;
import com.catalogo.dragon_ball.dto.DetalleVentaResponseDTO;
import com.catalogo.dragon_ball.repository.DetalleVentaRepository;
import com.catalogo.dragon_ball.repository.ProductoRepository;
import com.catalogo.dragon_ball.entity.DetalleVenta;
import com.catalogo.dragon_ball.entity.Producto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DetalleVentaService {
    private final DetalleVentaRepository detalleVentaRepository;
    private final ProductoRepository productoRepository;

    public DetalleVentaResponseDTO crearDetalleVenta(DetalleVentaRequestDTO detalleVentaRequestDTO){
      
        Producto producto = productoRepository.findById(detalleVentaRequestDTO.getIdProducto())
    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        if (detalleVentaRequestDTO.getCantidad() == null || detalleVentaRequestDTO.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }
         if (producto.getStock() < detalleVentaRequestDTO.getCantidad()) {
             throw new IllegalArgumentException("Stock insuficiente");
        }
     DetalleVenta detalleVenta = new DetalleVenta();
        detalleVenta.setProducto(producto);
        detalleVenta.setCantidad(detalleVentaRequestDTO.getCantidad());
        detalleVenta.setPrecio(producto.getPrecio());


        detalleVentaRepository.save(detalleVenta);

        DetalleVentaResponseDTO response= new DetalleVentaResponseDTO();
        response.setNombreProducto(producto.getNombre());
        response.setCantidad(detalleVentaRequestDTO.getCantidad());
        response.setPrecio(producto.getPrecio());

        return response;
    }
      public List<DetalleVentaResponseDTO> obtenerDetallesVenta() {
        List<DetalleVenta> detalleVentas = detalleVentaRepository.findAll();
        List<DetalleVentaResponseDTO> listaDetalleVenta = new ArrayList<>();

        for (DetalleVenta d: detalleVentas) {
            DetalleVentaResponseDTO detalleventa = new DetalleVentaResponseDTO();
             detalleventa.setNombreProducto(d.getProducto().getNombre());
            detalleventa.setCantidad(d.getCantidad());
             detalleventa.setPrecio(d.getPrecio());
            listaDetalleVenta.add(detalleventa);
        }
        return listaDetalleVenta;
    }
    public Optional<DetalleVentaResponseDTO> obtenerDetallesVenta(Long id) {
        if (id == null) {
        throw new IllegalArgumentException("El id no puede ser nulo");
        }
        Optional<DetalleVenta> detalleVentOptional = detalleVentaRepository.findById(id);

        if(detalleVentOptional.isPresent()) {
            DetalleVenta detalleVenta = detalleVentOptional.get();
            DetalleVentaResponseDTO response = new DetalleVentaResponseDTO();
            response.setNombreProducto(detalleVenta.getProducto().getNombre());
            response.setCantidad(detalleVenta.getCantidad());
            response.setPrecio(detalleVenta.getPrecio());

            return Optional.of(response);
        } else {
            return Optional.empty();
        }
    }

        public Optional<DetalleVentaResponseDTO> actualizarDetallesVenta(Long id, DetalleVentaRequestDTO detalleVentaRequestDTO) {
           
            if (detalleVentaRequestDTO.getIdProducto() == null) {
            throw new IllegalArgumentException("El id no puede ser nulo");
        }

        if (detalleVentaRequestDTO.getCantidad() == null || detalleVentaRequestDTO.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }
        Optional<DetalleVenta> detalleVentOptional = detalleVentaRepository.findById(id);
        if(detalleVentOptional.isPresent()) {
            DetalleVenta detalleVenta = detalleVentOptional.get();

             Producto producto = productoRepository.findById(detalleVentaRequestDTO.getIdProducto())
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

            if (producto.getStock() < detalleVentaRequestDTO.getCantidad()) {
                throw new IllegalArgumentException("Stock insuficiente");
            }
            detalleVenta.setProducto(producto);
            detalleVenta.setCantidad(detalleVentaRequestDTO.getCantidad());
             detalleVenta.setPrecio(producto.getPrecio());

            DetalleVenta detalleVentaActualizado = detalleVentaRepository.save(detalleVenta);

         DetalleVentaResponseDTO response= new DetalleVentaResponseDTO();
        response.setNombreProducto(detalleVentaActualizado.getProducto().getNombre());
        response.setCantidad(detalleVentaActualizado.getCantidad());
        response.setPrecio(detalleVentaActualizado.getPrecio());

            return Optional.of(response);
        } else {
            return Optional.empty();
        }
    }
    public Optional<DetalleVentaResponseDTO> eliminarDetallesVenta(Long id) {
         if (id == null) {
         throw new IllegalArgumentException("El id no puede ser nulo");
        }
    Optional<DetalleVenta> detalleVentaOptional = detalleVentaRepository.findById(id);

    if (detalleVentaOptional.isPresent()) {
        DetalleVenta detalleVenta = detalleVentaOptional.get();

        DetalleVentaResponseDTO response = new DetalleVentaResponseDTO();
        response.setNombreProducto(detalleVenta.getProducto().getNombre());
        response.setCantidad(detalleVenta.getCantidad());
        response.setPrecio(detalleVenta.getPrecio());

        detalleVentaRepository.delete(detalleVenta);
        return Optional.of(response);
    } else {
        return Optional.empty();
    }
}
}