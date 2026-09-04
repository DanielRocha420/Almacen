package com.daniel.almacen.controllers;

import com.daniel.almacen.dto.reporte.ReporteVentasSucursalResponse;
import com.daniel.almacen.services.reporte.ReporteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
@AllArgsConstructor
@Tag(name = "Reportes", description = "Endpoints para generación de reportes de rendimiento")
public class ReporteController {

    private final ReporteService reporteService;

    @GetMapping("/ventas-sucursal")
    @Operation(
            summary = "Reporte General de Ventas por Sucursal",
            description = "Devuelve el acumulado económico y total de unidades vendidas por cada sucursal (solo ventas registradas/activas).",
            tags = {"Reportes - Económicos"}
    )
    public ResponseEntity<List<ReporteVentasSucursalResponse>> obtenerReporteVentasSucursal() {
        return ResponseEntity.ok(reporteService.generarReporteVentasPorSucursal());
    }

    @GetMapping("/ventas-sucursal/{idSucursal}")
    @Operation(
            summary = "Reporte de Ventas por ID de Sucursal",
            description = "Devuelve el acumulado económico y total de unidades vendidas de una sucursal específica identificada por su ID.",
            tags = {"Reportes - Económicos"}
    )
    public ResponseEntity<ReporteVentasSucursalResponse> obtenerReporteVentasPorSucursalId(@PathVariable Long idSucursal) {
        return ResponseEntity.ok(reporteService.generarReporteVentasPorSucursalId(idSucursal));
    }
}