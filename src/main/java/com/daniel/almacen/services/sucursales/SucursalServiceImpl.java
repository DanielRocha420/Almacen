package com.daniel.almacen.services.sucursales;

import com.daniel.almacen.dto.sucursales.SucursalRequest;
import com.daniel.almacen.dto.sucursales.SucursalResponse;
import com.daniel.almacen.entities.Producto;
import com.daniel.almacen.entities.Sucursal;
import com.daniel.almacen.exceptions.RecursoNoEncontradoException;
import com.daniel.almacen.mappers.SucursalMapper;
import com.daniel.almacen.repositories.SucursalRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class SucursalServiceImpl implements SucursalService{
    private final SucursalRepository sucursalRepository;
    private final SucursalMapper sucursalMapper;

    @Override
    public List<SucursalResponse> listar() {

        log.info("Listando todas las sucursales");
        return sucursalRepository.findAll().stream().map(sucursalMapper::entidadAResponse).toList();
    }

    @Override
    public SucursalResponse obtenerPorID(Long id) {
        return  sucursalMapper.entidadAResponse(obtenerSucursalOExeption(id));
    }

    @Override
    public SucursalResponse registrar(SucursalRequest request) {

        log.info("Registrando nueva sucurssal...");

        Sucursal sucursal = sucursalMapper.requestAEntidad(request);

        validarDatosUnicos(request);

        sucursalRepository.save(sucursal);

        log.info("Nueva sucursal registrado: {}", sucursal.getNombre());
        return sucursalMapper.entidadAResponse(sucursal);
    }

    @Override
    public SucursalResponse actualizar(SucursalRequest request, Long id) {

        Sucursal sucursal = obtenerSucursalOExeption(id);

        log.info("Actualizando sucursal con id: {}", id);
        validarCambiosUnicos(request, id);
        log.info("Sucursal con id {} actualizada", id);

        return sucursalMapper.entidadAResponse(sucursal);
    }

    @Override
    public void eliminar(Long id) {

        Sucursal sucursal = obtenerSucursalOExeption(id);

        log.info("Eliminando sucursal con id: {}", id);
        sucursalRepository.delete(sucursal);

        log.info("Sucursal con id {} eliminado", id);

    }

    private Sucursal obtenerSucursalOExeption(Long id){
        return sucursalRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException( "Sucursal no encontrado con id:" + id));


    }

    private void validarDatosUnicos(SucursalRequest request){
        log.info("Validando nombre unico...");

        if (sucursalRepository.existsByNombreIgnoreCase(request.nombre()))
            throw new IllegalArgumentException("Ya existe una sucursal con el nombre de " + request.nombre());
    }

    private void validarCambiosUnicos(SucursalRequest request, Long id){
        log.info("Validando cambio en nombre unico....");

        if (sucursalRepository.existsByNombreIgnoreCaseAndIdNot(request.nombre().trim(), id))
            throw new IllegalArgumentException("Ya existe una sucursal con el nombre de " + request.nombre());
    }
}
