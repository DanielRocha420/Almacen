package com.daniel.almacen.dto.reporte;

import java.math.BigDecimal;

public record ReporteVentasSucursalesResponse(
        Long idSucursal,
        String nombreSucursal,
        BigDecimal totalFacturado,
        Long cantidadProductosVendidos
) {

}
