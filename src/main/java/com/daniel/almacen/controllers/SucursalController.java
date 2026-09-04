package com.daniel.almacen.controllers;


import com.daniel.almacen.dto.sucursales.SucursalRequest;
import com.daniel.almacen.dto.sucursales.SucursalResponse;
import com.daniel.almacen.services.sucursales.SucursalService;
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
@RequestMapping("/api/sucursal")
@AllArgsConstructor
@Validated
@Tag(name = "Sucursal", description = "Endpoint para gestion de Sucursales")
public class SucursalController {
    private final SucursalService sucursalService;

    @GetMapping
    @Operation(
            summary = "Listar Sucursales",
            tags = {"Sucursales - Consultas"}
    )
    public ResponseEntity<List<SucursalResponse>> listar(){
        return ResponseEntity.ok(sucursalService.listar());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener sucursales por ID",
            tags = {"Sucursales - Consultas"}
    )
    public ResponseEntity<SucursalResponse> obtenerPorId(
            @PathVariable @Positive(message = "El ID debe ser positivo") Long id
    ){
        return ResponseEntity.ok(sucursalService.obtenerPorID(id));
    }

    @PostMapping
    @Operation(
            summary = "Registrar una nueva Sucursal",
            tags = {"sucursal - Consultas"}
    )
    public ResponseEntity<SucursalResponse> registrar(
            @Valid @RequestBody SucursalRequest request
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(sucursalService.registrar(request));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar una Sucursal existente",
            tags = {"Sucursales - Consultas"}
    )
    public ResponseEntity<SucursalResponse> actualizar(
            @PathVariable @Positive(message = "El ID debe ser positivo") Long id,
            @Valid @RequestBody SucursalRequest request
    ){
        return ResponseEntity.ok(sucursalService.actualizar(request, id));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar una Sucursal",
            tags = {"Sucursals - Consultas"}
    )
    public ResponseEntity<Void> eliminar(
            @PathVariable @Positive(message = "El ID debe ser positivo") Long id
    ){
        sucursalService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
