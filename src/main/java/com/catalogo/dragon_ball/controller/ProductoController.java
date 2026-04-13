package com.catalogo.dragon_ball.controller;

import com.catalogo.dragon_ball.dto.ProductoDTO;
import com.catalogo.dragon_ball.service.ProductoService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/Producto")
public class ProductoController {
    private final ProductoService productoService;
        @PostMapping
    public ResponseEntity<ProductoDTO> crearproducto(@RequestBody ProductoDTO productoRequestDTO) {
        ProductoDTO response = productoService.crearproducto(productoRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
        @GetMapping
    public ResponseEntity<List<ProductoDTO>> listarproductos() {
        List<ProductoDTO> listarproductos = productoService.obtenerProductos();
        return ResponseEntity.status(HttpStatus.FOUND).body(listarproductos);
    }
 @GetMapping("/{nombre}")
    public ResponseEntity<ProductoDTO> obtenerProducto(@PathVariable String nombre) {
        ProductoDTO response = productoService.obtenerProductos(nombre).orElse(null);
        if (response != null) {
            return ResponseEntity.status(HttpStatus.FOUND).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
     @PutMapping("/{nombre}")
    public ResponseEntity<ProductoDTO> actualizarProducto(@PathVariable String nombre, @RequestBody ProductoDTO productoRequestDTO) {
        ProductoDTO response = productoService.actualizarProducto(nombre, productoRequestDTO).orElse(null);
        if (response != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @DeleteMapping("/{nombre}")
    public ResponseEntity<ProductoDTO> eliminarProducto(@PathVariable String nombre){
        ProductoDTO response= productoService.eliminarProducto(nombre).orElse(null);
        if (response !=null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);   
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
