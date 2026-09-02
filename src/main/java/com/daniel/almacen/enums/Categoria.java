package com.daniel.almacen.enums;

import com.daniel.almacen.utils.StringCustomUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Categoria {

    ALIMENTO("Alimento"),
    HIGIENE( "Higiene"),
    JUGUETE(  "Juguete"),
    ELECTRONICA(  "Electromica"),
    ROPA(  "Ropa"),
    ACCESORIO(  "Accesorio"),
    FARMACIA(  "Farmacia");

    private final String descripcion;

    public static Categoria obtenerCategoriaPorDescrippcion(String descripcion) {

        StringCustomUtils.validarNoVacio(descripcion, "La descripcion requerida");

        String descripcionNormalizada = StringCustomUtils.quitarAcentos(descripcion);

        for (Categoria categoria : values()){
            if(StringCustomUtils.quitarAcentos(categoria.descripcion).equalsIgnoreCase(descripcionNormalizada))
                return categoria;
        }

        throw new RuntimeException("No existe una categoria con la descripcion" + descripcion);
    }
}
