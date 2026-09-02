package com.daniel.almacen.utils;

import com.daniel.almacen.entities.Producto;
import com.daniel.almacen.enums.Categoria;
import com.daniel.almacen.repositories.ProductoRepository;
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
    }

}
