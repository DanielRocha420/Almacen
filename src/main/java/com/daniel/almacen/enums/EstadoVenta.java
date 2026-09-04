package com.daniel.almacen.enums;

import com.daniel.almacen.exceptions.RecursoNoEncontradoException;
import com.daniel.almacen.utils.StringCustomUtils;
import com.daniel.almacen.utils.ValoresNumericosUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EstadoVenta {

    REGISTRADA(1L, "Registrada"),
    CANCELADA(0L, "Cancelada");

    private final Long codigo;
    private final String descipcion;

    public static EstadoVenta obtenerEstadoVentaPorDescripcion(String descipcion) {
        StringCustomUtils.validarNoVacio(descipcion, "La descripcion es requerida");

        String descripcionNormalizada = StringCustomUtils.quitarAcentos(descipcion);
        for (EstadoVenta estadoVenta : values()){
            if (StringCustomUtils.quitarAcentos(estadoVenta.descipcion).equalsIgnoreCase(descripcionNormalizada))

                return estadoVenta;
        }
        throw new RecursoNoEncontradoException("No existe un estado de venta con la descripcion " + descipcion);
    }

    public static EstadoVenta obtenerEstadoCentaPorCodigo(Long codigo){
        ValoresNumericosUtils.validarNumeroRequerido(codigo);

        for (EstadoVenta estadoVenta : values()) {
            if (estadoVenta.codigo.equals(codigo))
                return estadoVenta;
        }

        throw new RecursoNoEncontradoException("No existe un estado de venta con el codigo: " + codigo);
    }
}
