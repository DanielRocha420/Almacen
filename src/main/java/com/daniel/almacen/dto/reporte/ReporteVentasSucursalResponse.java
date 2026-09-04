package com.daniel.almacen.dto.reporte;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ReporteVentasSucursalResponse(

        @Schema(description = "Id de la sucursal", example = "1")
        @NotNull(message = "El Id de la sucursal es requerido")
        @Positive(message = "El Id de la sucursal debe ser positivo")
        Long idSucursal,

        @Schema(description = "Nombre de la sucursal", example = "1")
        @NotNull(message = "El Nombre de la sucursal es requerido")
        String nombreSucursal,

        @Schema(description = "Precio del producto", example = "1500.00")
        BigDecimal totalFacturado,

        @Schema(description = "La cantidad del producto", example = "100")
        @NotNull(message = "La cantidad del producto es requerida")
        @Positive(message = "La cantidad del producto debe ser positiva")
        Long cantidadProductosVendidos
) {

}
