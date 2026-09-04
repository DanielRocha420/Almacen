package com.daniel.almacen.repositories;

import com.daniel.almacen.dto.reporte.ReporteVentasSucursalResponse;
import com.daniel.almacen.entities.Venta;
import com.daniel.almacen.enums.EstadoVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {

    List<Venta> findByEstadoVenta(EstadoVenta estadoVenta);

    List<Venta> findByEstadoVentaAndSucursalId(EstadoVenta estadoVenta, Long sucursalId);

    // Reporte de TODAS las sucursales
    @Query("""
        SELECT new com.daniel.almacen.dto.reporte.ReporteVentasSucursalResponse(
            s.id, 
            s.nombre, 
            COALESCE(SUM(d.cantidadProducto * d.precioProducto), 0.0), 
            COALESCE(SUM(d.cantidadProducto), 0L)
        ) 
        FROM Venta v 
        JOIN v.sucursal s 
        INNER JOIN v.detalleVentas d 
        WHERE v.estadoVenta = 'REGISTRADA' 
        GROUP BY s.id, s.nombre
    """)
    List<ReporteVentasSucursalResponse> generarReporteVentasPorSucursal();

    // Reporte por ID de sucursal específica
    @Query("""
        SELECT new com.daniel.almacen.dto.reporte.ReporteVentasSucursalResponse(
            s.id, 
            s.nombre, 
            COALESCE(SUM(d.cantidadProducto * d.precioProducto), 0.0), 
            COALESCE(SUM(d.cantidadProducto), 0L)
        ) 
        FROM Venta v 
        JOIN v.sucursal s 
        INNER JOIN v.detalleVentas d 
        WHERE v.estadoVenta = 'REGISTRADA' 
          AND s.id = :idSucursal
        GROUP BY s.id, s.nombre
    """)
    Optional<ReporteVentasSucursalResponse> generarReporteVentasPorSucursalId(@Param("idSucursal") Long idSucursal);

}