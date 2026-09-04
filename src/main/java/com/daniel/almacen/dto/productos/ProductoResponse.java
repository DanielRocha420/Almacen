package com.daniel.almacen.dto.productos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Informacion de un producto")
public record ProductoResponse(

        @Schema(description = "Identificador de un producto",example = "1")
        Long id,

        @Schema(description = "Nombre del producto", example = "Lapto gamer")
        String nombre,

        @Schema(description = "Categoria del producto", example = "Electronicos")
        String categoria,

        @Schema(description = "Precio del producto", example = "197900.00")
        BigDecimal precio,

        @Schema(description = "Cantidad disponible del producto", example = "300")
        Integer cantidad


) {
}
