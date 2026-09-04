package com.daniel.almacen.dto.productos;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Schema(description = "Datos necesarios para crear o actualizar un producto")
public record ProductoRequest(

        @Schema(
                description = "Nombre del producto",
                example = "Laptop gamer"
        )
        @NotBlank(message = "El nombre es requerido")
        @Size(min = 5, max = 30, message = "El nombre debe tener 5 y 30 caracteres")
        String nombre,


        @Schema(
                description = "Categoria del producto",
                example = "Electronicos"
        )
        @NotBlank(message = "La categoria es requerida")
        String categoria,


        @Schema(
                description = "Precio del producto",
                example = "197900.00"
        )
        @NotBlank(message = "El precio s requerido")
        @Positive(message = "El precio debe ser positivo")
        BigDecimal precio,


        @Schema(
                description = "Cantidad disponible del producto",
                example = "300"
        )
        @NotBlank(message = "La cantidad es requerido")
        @Positive(message = "La cantidad debe ser positivo")
        Integer cantidad
) {
}