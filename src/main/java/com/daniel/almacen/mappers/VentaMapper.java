package com.daniel.almacen.mappers;


import com.daniel.almacen.dto.venta.DetalleVentaResponse;
import com.daniel.almacen.dto.venta.VentaRequest;
import com.daniel.almacen.dto.venta.VentaResponse;
import com.daniel.almacen.entities.DetalleVenta;
import com.daniel.almacen.entities.Venta;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@Component
public class VentaMapper {

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final SucursalMapper sucursalMapper;

    public VentaMapper(SucursalMapper sucursalMapper) {
        this.sucursalMapper = sucursalMapper;
    }

    public Venta requestAEntidad(VentaRequest request) {
        return null;
    }

    public VentaResponse entidadAResponse(Venta venta) {
        if (venta == null) return null;

        List<DetalleVentaResponse> detalles = mapearDetalles(venta.getDetalleVentas());

        return new VentaResponse(
                venta.getId(),
                venta.getFecha() != null ? venta.getFecha().format(FORMATO_FECHA) : null,
                venta.getEstadoVenta() != null ? venta.getEstadoVenta().getDescipcion() : null,
                sucursalMapper.entidadAResponse(venta.getSucursal()),
                detalles,
                calcularTotal(detalles)
        );
    }

    private List<DetalleVentaResponse> mapearDetalles(List<DetalleVenta> detalleVentas) {
        if (detalleVentas == null || detalleVentas.isEmpty()) {
            return Collections.emptyList();
        }

        return detalleVentas.stream()
                .map(this::detalleAResponse)
                .toList();
    }

    private DetalleVentaResponse detalleAResponse(DetalleVenta detalle) {
        BigDecimal subTotal = detalle.getPrecioProducto()
                .multiply(BigDecimal.valueOf(detalle.getCantidadProducto()));

        return new DetalleVentaResponse(
                detalle.getProducto().getId(),
                detalle.getProducto().getNombre(),
                detalle.getCantidadProducto(),
                detalle.getPrecioProducto(),
                subTotal
        );
    }

    private BigDecimal calcularTotal(List<DetalleVentaResponse> detalles) {
        return detalles.stream()
                .map(DetalleVentaResponse::subTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
