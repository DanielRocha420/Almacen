package com.daniel.almacen.services.reporte;

import com.daniel.almacen.dto.reporte.ReporteVentasSucursalResponse;
import com.daniel.almacen.repositories.VentaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ReporteServiceImp implements ReporteService {

    private final VentaRepository ventaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ReporteVentasSucursalResponse> generarReporteVentasPorSucursal() {
        return ventaRepository.generarReporteVentasPorSucursal();
    }

    @Override
    @Transactional(readOnly = true)
    public ReporteVentasSucursalResponse generarReporteVentasPorSucursalId(Long idSucursal) {
        return ventaRepository.generarReporteVentasPorSucursalId(idSucursal)
                .orElseThrow(() -> new RuntimeException("No se encontraron ventas registradas para la sucursal con ID: " + idSucursal));
    }
}