package com.daniel.almacen.services.ventas;

import com.daniel.almacen.dto.venta.VentaRequest;
import com.daniel.almacen.dto.venta.VentaResponse;
import com.daniel.almacen.dto.venta.VentaResponse;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface VentaService {

    List<VentaResponse> listar(
            String estado, Date fecha, Long id_sucursal
            );

    List<VentaResponse> listarCanceladas(Long id_sucursal);
    VentaResponse obtenerPorId(Long id);

    VentaResponse registrar(VentaRequest request);

    VentaResponse actualizar(VentaRequest request, Long id);

    void eliminar(Long id);

    VentaResponse cancelarVenta(Long id);
}
