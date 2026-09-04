package com.daniel.almacen.dto.venta;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DetalleVentaRequest(
        @Schema(description = "Id del producto", example = "1")
        @NotNull(message = "El Id del producto es requerido")
        @Positive(message = "El Id del producto debe ser positivo")
        Long idProducto,

        @Schema(description = "La cantidad del producto", example = "100")
        @NotNull(message = "La cantidad del producto es requerida")
        @Positive(message = "La cantidad del producto debe ser positiva")
        Integer cantidadProducto
) {
}
