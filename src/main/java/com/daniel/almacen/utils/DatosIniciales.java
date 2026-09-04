package com.daniel.almacen.utils;

import com.daniel.almacen.entities.Producto;
import com.daniel.almacen.entities.Sucursal;
import com.daniel.almacen.enums.Categoria;
import com.daniel.almacen.repositories.ProductoRepository;
import com.daniel.almacen.repositories.SucursalRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@Slf4j
@AllArgsConstructor
public class DatosIniciales implements CommandLineRunner {
    private final ProductoRepository productoRepository;

    private final SucursalRepository sucursalRepository;

    @Override
    public void run(String... args) throws Exception {
        if(productoRepository.count() == 0){
            productoRepository.saveAll(List.of(
                    new Producto(null, "Laptop Gamer", Categoria.ELECTRONICA, BigDecimal.valueOf(1500.99), 10),
                    new Producto(null, "Mouse Inalámbricor", Categoria.ELECTRONICA, BigDecimal.valueOf(25), 50),
                    new Producto(null, "Camiseta Deportiva", Categoria.ROPA, BigDecimal.valueOf(80.01), 100)
            ));

            log.info("Productos de prueba cargados correctamente");
        }

        if(sucursalRepository.count() == 0){
            sucursalRepository.saveAll(List.of(
                    new Sucursal(null, "Sucursal Central", "Av. Principal 123"),
                    new Sucursal(null, "Sucursal Norte", "Calle norte 457"),
                    new Sucursal(null, "Sucursal Sur", "Calle Sur 789")
            ));

            log.info("Sucursales de prueba cargados correctamente");
        }
    }

}
