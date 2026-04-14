package com.catalogo.dragon_ball.service;

import com.catalogo.dragon_ball.dto.*;
import com.catalogo.dragon_ball.entity.*;
import com.catalogo.dragon_ball.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VentaService {
    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository; 

    public VentaResponseDTO procesarVenta(VentaRequestDTO request, Users nombreCliente) {
        // 1. Creamos la entidad Venta (Cabecera)
        Venta venta = new Venta();
        venta.setFecha(LocalDateTime.now());
        venta.setUsuario(nombreCliente);
        
        List<DetalleVenta> detalles = new ArrayList<>();
        BigDecimal totalVenta = BigDecimal.ZERO;

        // 2. Recorremos los items del DTO que me pasaste en la foto
        for (DetalleVentaRequestDTO item : request.getItems()) {
            
            // BUSQUEDA: Usamos .intValue() por si tu ID en la entidad es Integer
            Producto producto = productoRepository.findById(item.getIdProducto())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + item.getIdProducto()));

            // 3. Validar y restar stock
            if (producto.getStock() < item.getCantidad()) {
                throw new RuntimeException("No hay suficiente stock para: " + producto.getNombre());
            }
            producto.setStock(producto.getStock() - item.getCantidad());
            productoRepository.save(producto);

            // 4. Crear el objeto DetalleVenta (Entidad)
            DetalleVenta dv = new DetalleVenta();
            dv.setProducto(producto);
            dv.setCantidad(item.getCantidad());
            dv.setPrecio(producto.getPrecio());
            
            // Calculamos subtotal: precio * cantidad
            BigDecimal subtotal = producto.getPrecio().multiply(new BigDecimal(item.getCantidad()));
            dv.setPrecio(subtotal);
            dv.setVenta(venta); // Relacionamos con la cabecera
            
            detalles.add(dv);
            totalVenta = totalVenta.add(subtotal);
        }

        venta.setDetalles(detalles);
        venta.setTotal(totalVenta);
        
        // 5. Guardamos en la base de datos
        ventaRepository.save(venta);

        VentaResponseDTO response = new VentaResponseDTO();
        response.setIdVenta(venta.getId().intValue());
        response.setFecha(venta.getFecha());
        response.setTotal(venta.getTotal());
        response.setNombreCliente(venta.getUsuario().getName());
        
        // Mapeamos la lista de detalles al DTO de respuesta
        List<DetalleVentaResponseDTO> detallesDTO = new ArrayList<>();
        for (DetalleVenta d : detalles) {
            DetalleVentaResponseDTO dDto = new DetalleVentaResponseDTO();
            dDto.setNombreProducto(d.getProducto().getNombre());
            dDto.setCantidad(d.getCantidad());
            dDto.setPrecio(d.getPrecio());
            detallesDTO.add(dDto);
        }
        
        response.setDetalles(detallesDTO);
        return response;
    }
}