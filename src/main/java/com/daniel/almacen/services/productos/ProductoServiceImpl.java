package com.daniel.almacen.services.productos;

import com.daniel.almacen.dto.productos.ProductoRequest;
import com.daniel.almacen.dto.productos.ProductoResponse;
import com.daniel.almacen.entities.Producto;
import com.daniel.almacen.enums.Categoria;
import com.daniel.almacen.exceptions.RecursoNoEncontradoException;
import com.daniel.almacen.mappers.ProductoMapper;
import com.daniel.almacen.repositories.ProductoRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class ProductoServiceImpl implements ProductoService {
    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponse> listar(String nombre, String categoria, BigDecimal precioMin, BigDecimal precioMax) {
        log.info("Listando todos los productos");

        return productoRepository.findAll().stream()
                .map(productoMapper::entidadAResponse).toList();
    }

    @Override
    public ProductoResponse obtenerPorId(Long id) {
        return  productoMapper.entidadAResponse(obtenerProductoOExeption(id));
    }
    @Override
    public ProductoResponse registrar(ProductoRequest request) {
        log.info("Registrando nuevo producto...");

        Producto producto = productoMapper.requestAEntidad(request, Categoria.obtenerCategoriaPorDescrippcion(request.categoria()));

        productoRepository.save(producto);

        log.info("Nuevo producto {} Registrado", producto.getNombre());
        return productoMapper.entidadAResponse(producto);
    }

    @Override
    public ProductoResponse actualizar(ProductoRequest request, Long id) {

        Producto producto = obtenerProductoOExeption(id);

        log.info("Actualizando producto con id: {}",  id);

        producto.actualizar(
                request.nombre(),
                Categoria.obtenerCategoriaPorDescrippcion(request.categoria()),
                request.precio(),
                request.cantidad());
        //productoRepository.save(producto); No necesario

        log.info("Producto con id {} actualizado", id);
        return productoMapper.entidadAResponse(producto);
    }

    @Override
    public void eliminar(Long id) {

        Producto producto = obtenerProductoOExeption(id);

        log.info("Eliminando producto con id: {}", id);
        productoRepository.delete(producto);

        log.info("Producto con id {} eliminado", id);

    }

    private Producto obtenerProductoOExeption(Long id) {

        log.info("buscando producto con id: {}", id);
        return productoRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException("Producto no encontrado con id:" + id));

    }


}
