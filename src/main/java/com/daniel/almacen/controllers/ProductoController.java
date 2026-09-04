package com.daniel.almacen.controllers;

import com.daniel.almacen.dto.productos.ProductoRequest;
import com.daniel.almacen.dto.productos.ProductoResponse;
import com.daniel.almacen.services.productos.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@AllArgsConstructor
@Validated
@Tag(name = "Productos", description = "Endpoint para gestion de productos")
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    @Operation(
            summary = "Listar productos",
            tags = {"Productos - Consultas"}
    )
    public ResponseEntity<List<ProductoResponse>> listar(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) BigDecimal precioMin,
            @RequestParam(required = false) BigDecimal precioMax
    ){
        return ResponseEntity.ok(productoService.listar(
                nombre, categoria, precioMin, precioMax));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener productos por ID",
            tags = {"Productos - Consultas"}
    )
    public ResponseEntity<ProductoResponse> obtenerPorId(
            @PathVariable @Positive(message = "El ID debe ser positivo") Long id
    ){
        return ResponseEntity.ok(productoService.obtenerPorId(id));
    }

    @PostMapping
    @Operation(
            summary = "Registrar un nuevo producto",
            tags = {"Productos - Consultas"}
    )
    public ResponseEntity<ProductoResponse> registrar(
            @Valid @RequestBody ProductoRequest request
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productoService.registrar(request));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar un producto existente",
            tags = {"Productos - Consultas"}
    )
    public ResponseEntity<ProductoResponse> actualizar(
            @PathVariable @Positive(message = "El ID debe ser positivo") Long id,
            @Valid @RequestBody ProductoRequest request
    ){
        return ResponseEntity.ok(productoService.actualizar(request, id));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar un productos",
            tags = {"Productos - Consultas"}
    )
    public ResponseEntity<Void> eliminar(
            @PathVariable @Positive(message = "El ID debe ser positivo") Long id
    ){
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}