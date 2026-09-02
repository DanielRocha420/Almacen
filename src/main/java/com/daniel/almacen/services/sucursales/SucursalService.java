package com.daniel.almacen.services.sucursales;

import com.daniel.almacen.dto.sucursales.SucursalRequest;
import com.daniel.almacen.dto.sucursales.SucursalResponse;

import java.util.List;

public interface SucursalService {

    List<SucursalResponse> listar();
    SucursalResponse obtenerPorID(Long id);
    SucursalResponse registrar(SucursalRequest request);
    SucursalResponse actualizar(SucursalRequest request, Long id);

    void eliminar(Long id);
}
