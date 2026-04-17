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
@RequestMapping("/api/producto")
public class ProductoController {
    private final ProductoService productoService;
        @PostMapping
    public ResponseEntity<ProductoDTO> crearproducto(@RequestBody ProductoDTO productoRequestDTO) {
        ProductoDTO response = productoService.crearproducto(productoRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
        @GetMapping
    public ResponseEntity<List<ProductoDTO>> obtenerproductos() {
        List<ProductoDTO> listarproductos = productoService.obtenerProductos();
        return ResponseEntity.status(HttpStatus.FOUND).body(listarproductos);
    }
 @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> obtenerProductoporid(@PathVariable Long id) {
        ProductoDTO response = productoService.obtenerProductos(id).orElse(null);
        if (response != null) {
            return ResponseEntity.status(HttpStatus.FOUND).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
     @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> actualizarProducto(@PathVariable Long id, @RequestBody ProductoDTO productoRequestDTO) {
        ProductoDTO response = productoService.actualizarProducto(id, productoRequestDTO).orElse(null);
        if (response != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ProductoDTO> eliminarProducto(@PathVariable Long id){
        ProductoDTO response= productoService.eliminarProducto(id).orElse(null);
        if (response !=null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);   
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
