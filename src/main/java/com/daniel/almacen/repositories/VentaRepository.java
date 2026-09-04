package com.daniel.almacen.repositories;

import com.daniel.almacen.dto.reporte.ReporteVentasSucursalResponse;
import com.daniel.almacen.entities.Venta;
import com.daniel.almacen.enums.EstadoVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VentaRepository  extends JpaRepository<Venta, Long> {

    List<Venta> findByEstadoVenta(EstadoVenta estadoVenta);
    List<Venta> findByEstadoVentaAndSucursalId(EstadoVenta estadoVenta, Long SucursalId);

}
