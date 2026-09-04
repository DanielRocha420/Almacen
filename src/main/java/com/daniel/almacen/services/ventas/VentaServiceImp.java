package com.daniel.almacen.services.ventas;

import com.daniel.almacen.dto.venta.DetalleVentaRequest;
import com.daniel.almacen.dto.venta.VentaRequest;
import com.daniel.almacen.dto.venta.VentaResponse;
import com.daniel.almacen.entities.DetalleVenta;
import com.daniel.almacen.entities.Producto;
import com.daniel.almacen.entities.Sucursal;
import com.daniel.almacen.entities.Venta;
import com.daniel.almacen.enums.EstadoVenta;
import com.daniel.almacen.exceptions.RecursoNoEncontradoException;
import com.daniel.almacen.mappers.VentaMapper;
import com.daniel.almacen.repositories.ProductoRepository;
import com.daniel.almacen.repositories.SucursalRepository;
import com.daniel.almacen.repositories.VentaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class VentaServiceImp implements VentaService {

    private final VentaRepository ventaRepository;
    private final SucursalRepository sucursalRepository;
    private final ProductoRepository productoRepository;
    private final VentaMapper ventaMapper;




    //Listado de Ventas Separado:
    @Override
    @Transactional(readOnly = true)
    public List<VentaResponse> listar(String estado, Date fecha, Long id_sucursal) {
        log.info("Listando todas las ventas Registradas");

        EstadoVenta estadoFiltro = EstadoVenta.REGISTRADA;

        List<Venta> ventas;
        if (id_sucursal != null) {
            ventas = ventaRepository.findByEstadoVentaAndSucursalId(estadoFiltro, id_sucursal);
        } else {
            ventas = ventaRepository.findByEstadoVenta(estadoFiltro);
        }

        return ventas.stream()
                .map(ventaMapper::entidadAResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<VentaResponse> listarCanceladas(Long id_sucursal){
        log.info("Listando todas las ventas Registradas");

        List<Venta> ventasCanceladas;
        if (id_sucursal != null) {
            ventasCanceladas = ventaRepository.findByEstadoVentaAndSucursalId(EstadoVenta.CANCELADA, id_sucursal);
        }else {
                ventasCanceladas = ventaRepository.findByEstadoVenta(EstadoVenta.CANCELADA);
            }
        return ventasCanceladas.stream()
                .map(ventaMapper::entidadAResponse)
                .toList();

        }



    @Override

    public VentaResponse obtenerPorId(Long id) {
        return ventaMapper.entidadAResponse(obtenerVentaOExeption(id));
    }



    //Registra una nueva venta de forma atómica.
    @Transactional
    @Override
    public VentaResponse registrar(VentaRequest request) {
        Sucursal sucursal = sucursalRepository.findById(request.idSucursal())
                        .orElseThrow(() -> new EntityNotFoundException("La sucursal no existe"));

        Venta venta = Venta.builder()
                .sucursal(sucursal)
                .estadoVenta(EstadoVenta.REGISTRADA)
                .build();

        for (DetalleVentaRequest detalleReq : request.productos()){

            Producto producto = productoRepository.findById(detalleReq.idProducto())
                    .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con ID: " + detalleReq.idProducto()));


            //Descuento de inventario
            producto.descontarCantidad(detalleReq.cantidadProducto());

            //Fotografía del Precio
            BigDecimal precioCapturado = producto.getPrecio();

            DetalleVenta detalleVenta= DetalleVenta.builder()
                    .producto(producto)
                    .cantidadProducto(detalleReq.cantidadProducto())
                    .precioProducto(precioCapturado) //Precio congelado
                    .build();

            venta.agregarDetalle(detalleVenta);
        }

        log.info("Registrando nueva venta");

        Venta ventaGuardada = ventaRepository.save(venta);
        return ventaMapper.entidadAResponse(ventaGuardada);
    }

    @Transactional
    //Cambio de Estado y Devolución de Stock
    public VentaResponse cancelarVenta(Long id) {
        log.info("Cancelando venta con ID: {}", id);

        Venta venta = obtenerVentaOExeption(id);

        venta.Cancelar();
        for (DetalleVenta detalle : venta.getDetalleVentas()) {
            Producto producto = detalle.getProducto();
            producto.aumentarCantidad(detalle.getCantidadProducto());
        }

        Venta ventaCancelada = ventaRepository.save(venta);
        return ventaMapper.entidadAResponse(ventaCancelada);
    }

    @Override
    public VentaResponse actualizar(VentaRequest request, Long id) {
        log.info("Actualizando venta con ID: {}", id);
        Venta ventaExistente = obtenerVentaOExeption(id);

        return ventaMapper.entidadAResponse(ventaRepository.save(ventaExistente));
    }

    @Override
    public void eliminar(Long id) {
        log.info("Eliminando venta con ID: {}", id);
        Venta venta = obtenerVentaOExeption(id);

        for (DetalleVenta detalle : venta.getDetalleVentas()){
            detalle.getProducto().aumentarCantidad(detalle.getCantidadProducto());
        }
        ventaRepository.deleteById(id);
    }

    private  Venta obtenerVentaOExeption(Long id){
        log.info("Buscando venta con ID: {}", id);
            return ventaRepository.findById(id).orElseThrow(
                    () -> new RecursoNoEncontradoException("Venta no encontrada con ID:" + id));
    }
}