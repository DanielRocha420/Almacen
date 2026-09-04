package com.daniel.almacen.entities;


import com.daniel.almacen.enums.EstadoVenta;
import com.daniel.almacen.utils.FechaCustomUtils;
import com.daniel.almacen.utils.StringCustomUtils;
import com.daniel.almacen.utils.ValoresNumericosUtils;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "VENTAS")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_VENTA")
    private Long id;

    @Column(name = "ESTADO", nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoVenta estadoVenta;

    @Column(name = "FECHA", nullable = false)
    private LocalDate fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SUCURSAL", nullable = false)
    private Sucursal sucursal;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<DetalleVenta> detalleVentas = new ArrayList<>();

    @PrePersist
    public void prePersist(){
        if (this.fecha == null){
            this.fecha = LocalDate.now();
        }
    }

    public void agregarDetalle(DetalleVenta detalleVenta){

        if(detalleVenta == null){
            throw new IllegalArgumentException("El detalle de venta es requerido");
        }
        if(this.detalleVentas == null){
            this.detalleVentas = new ArrayList<>();
        }
        this.detalleVentas.add(detalleVenta);
        detalleVenta.asignarVenta(this);
    }


    public void Cancelar(){


        if(estadoVenta == EstadoVenta.CANCELADA) {
            throw new IllegalArgumentException("La venta ya esta cancelada");
        }
        this.estadoVenta = EstadoVenta.CANCELADA;
    }

    public void validarDatos(String estado, Date fecha, Long id_sucursal){
        StringCustomUtils.validarTamanio(estado, 5, 20, "El estado de la venta es requerido debe tener entre 5 y 20 caracteres");
        FechaCustomUtils.validarFechaPasadaOPresente(fecha,
                "La fecha es requerida y no puede ser una fecha futura");
        ValoresNumericosUtils.validarEnteroPositivo(id_sucursal != null ? id_sucursal.intValue() : null, "El ID de la sucursal es requerido y debe ser positivo");
    }

    public void actualizar(String estado, Date fecha, Long id_sucursal){

    }
}

