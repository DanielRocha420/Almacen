package com.daniel.almacen.controllers;


import com.daniel.almacen.dto.venta.VentaRequest;
import com.daniel.almacen.dto.venta.VentaResponse;
import com.daniel.almacen.services.ventas.VentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@AllArgsConstructor
@Validated
@Tag(name = "Ventas", description = "Endpoint para gestion de las Ventas")
public class VentasController {

    private final VentaService ventaService;

    @GetMapping
    @Operation(
            summary = "Listar Ventas",
            tags = {"Ventas - Consultas"}
    )

    //ventas en estado REGISTRADA
    public ResponseEntity<List<VentaResponse>> listar(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha,
            @RequestParam(required = false) Long id_sucursal
            ) {
        return ResponseEntity.ok(ventaService.listar(estado, fecha, id_sucursal));

    }

    @GetMapping("/historico-canceladas")
    public ResponseEntity<List<VentaResponse>> listarHistoricoCanceladas(
            @RequestParam(required = false, name = "id_sucursal") Long idSucursal
    ) {

        return ResponseEntity.ok(ventaService.listarCanceladas(idSucursal));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener Venta por ID",
            tags = {"Ventas - Consultas"}
    )

    public ResponseEntity<VentaResponse> obtenerPorID(
            @PathVariable @Positive (message = "El ID debe ser positivo") Long id
    ){
        return ResponseEntity.ok(ventaService.obtenerPorId(id));
    }
    @PostMapping
    @Operation(
            summary = "Registar nueva venta",
            tags = {"Ventas - Consultas"}
    )
    public ResponseEntity<VentaResponse> registrar(
            @Valid @RequestBody VentaRequest request
            ){
        return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ventaService.registrar(request));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar una venta existente ",
            tags = {"Ventas - Consultas"}
    )
    public ResponseEntity<VentaResponse> actualizar(
            @PathVariable @Positive(message = "El ID debe ser positivo") Long id,
            @Valid @RequestBody VentaRequest request
    ){
        return ResponseEntity.ok(ventaService.actualizar(request, id));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar una venta existente",
            tags = {"Ventas - Operaciones"}
    )
    public ResponseEntity<Void> eliminar(
            @PathVariable @Positive(message = "El ID debe ser positivo") Long id
    ){
        ventaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/cancelar")
    @Operation(
            summary = "Cancelar una venta y devolver stock al inventario",
            tags = {"Ventas - Operaciones"}
    )
    public ResponseEntity<VentaResponse> cancelar(
            @PathVariable @Positive(message = "El ID debe ser positivo") Long id
    ) {
        return ResponseEntity.ok(ventaService.cancelarVenta(id));
    }



}
