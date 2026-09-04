package com.daniel.almacen.entities;

import com.daniel.almacen.enums.Categoria;
import com.daniel.almacen.utils.StringCustomUtils;
import com.daniel.almacen.utils.ValoresNumericosUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "PRODUCTOS")
@NoArgsConstructor
@AllArgsConstructor
@Builder @Getter
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PRODUCTO")
    private Long id;

    @Column(name = "NOMBRE", length = 30, nullable = false)
    private String nombre;

    @Column(name = "CATEGORIA", nullable = false)
    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    @Column(name = "PRECIO", nullable = false)
    private BigDecimal precio;

    @Column(name = "CANTIDAD", nullable = false)
    private Integer cantidad;

    public void aumentarCantidad(int cantidad) {
        ValoresNumericosUtils.validarEnteroPositivo(cantidad, "La cantidad debe ser positiva");

        this.cantidad += cantidad;
    }
//
    public void descontarCantidad(int cantidad) {
        ValoresNumericosUtils.validarEnteroPositivo(cantidad, "La cantidad a descontar debe ser positiva");

        //Validación de Existencias
        if (cantidad > this.cantidad) {

            throw new IllegalArgumentException(
                    "Stock insuficiente para el producto '" + this.nombre +
                            "'. Stock disponible: " + this.cantidad + ", solicitado: " + cantidad
            );
        }

        this.cantidad -= cantidad;
    }

    public void validarDatos(String nombre, Categoria categoria, BigDecimal precio, Integer cantidad) {
        StringCustomUtils.validarTamanio(nombre, 5, 30,
                "El nombre es requerido y debe tener entre 5 y 30 caracteres");

        if (categoria == null)
            throw new IllegalArgumentException("La categoría es requerida");

        ValoresNumericosUtils.validarBigDecimalPositivo(precio, "El precio es requerido y debe ser positivo");

        ValoresNumericosUtils.validarEnteroPositivo(cantidad, "La cantidad es requerida y debe ser positiva");
    }

    public void actualizar(String nombre, Categoria categoria, BigDecimal precio, Integer cantidad) {
        validarDatos(nombre, categoria, precio, cantidad);

        this.nombre = nombre.trim();
        this.categoria = categoria;
        this.precio = precio;
        this.cantidad = cantidad;
    }
}