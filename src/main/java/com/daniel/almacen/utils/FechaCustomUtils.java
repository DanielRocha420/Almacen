package com.daniel.almacen.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class FechaCustomUtils {

    private FechaCustomUtils() {}


    public static void validarFechaPasadaOPresente(Date fecha, String mensaje) {
        if (fecha == null) {
            throw new IllegalArgumentException(mensaje);
        }
        if (fecha.after(new Date())) {
            throw new IllegalArgumentException(mensaje);
        }
    }


    public static void validarFechaPasadaOPresente(LocalDate fecha, String mensaje) {
        if (fecha == null) {
            throw new IllegalArgumentException(mensaje);
        }
        if (fecha.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(mensaje);
        }
    }


    public static void validarNoNulo(Object fecha, String mensaje) {
        if (fecha == null) {
            throw new IllegalArgumentException(mensaje);
        }
    }
}