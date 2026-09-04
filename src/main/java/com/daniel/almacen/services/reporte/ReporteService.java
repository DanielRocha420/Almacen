package com.daniel.almacen.services.reporte;

import com.daniel.almacen.dto.reporte.ReporteVentasSucursalResponse;
import java.util.List;

public interface ReporteService {

    List<ReporteVentasSucursalResponse> generarReporteVentasPorSucursal();

    ReporteVentasSucursalResponse generarReporteVentasPorSucursalId(Long idSucursal);

}