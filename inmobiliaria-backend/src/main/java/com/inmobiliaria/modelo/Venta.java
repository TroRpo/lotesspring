package com.inmobiliaria.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entidad que representa una venta de lote.
 * Relaciona las entidades Cliente, Lote y Agente.
 * Mapea la tabla 'ventas' de PostgreSQL.
 *
 * @author [Tu nombre]
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ventas")
public class Venta {

    /** Forma de pago: pago total al contado */
    public static final String PAGO_CONTADO = "CONTADO";

    /** Forma de pago: pago a credito */
    public static final String PAGO_CREDITO = "CREDITO";

    /** Forma de pago: pago financiado con entidad bancaria */
    public static final String PAGO_FINANCIADO = "FINANCIADO";

    /** Identificador unico generado automaticamente */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_venta")
    private Integer idVenta;

    /**
     * Cliente que realiza la compra.
     * ManyToOne: muchas ventas pueden pertenecer a un cliente.
     * JoinColumn: columna de clave foranea en la tabla ventas.
     */
    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    /**
     * Lote que se vende.
     * ManyToOne: un lote puede aparecer en una venta.
     */
    @ManyToOne
    @JoinColumn(name = "id_lote", nullable = false)
    private Lote lote;

    /**
     * Agente que gestiona la venta.
     * ManyToOne: un agente puede gestionar muchas ventas.
     */
    @ManyToOne
    @JoinColumn(name = "id_agente", nullable = false)
    private Agente agente;

    /** Fecha en que se realizo la venta, asignada automaticamente */
    @Column(name = "fecha_venta")
    private LocalDate fechaVenta;

    /** Precio final negociado de la venta */
    @NotNull(message = "El precio final es obligatorio")
    @Positive(message = "El precio debe ser positivo")
    @Column(name = "precio_final", nullable = false, precision = 15, scale = 2)
    private BigDecimal precioFinal;

    /** Forma de pago: CONTADO, CREDITO o FINANCIADO */
    @NotBlank(message = "La forma de pago es obligatoria")
    @Column(name = "forma_pago", nullable = false, length = 20)
    private String formaPago;

    /** Observaciones adicionales sobre la venta */
    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;

    /**
     * Metodo que se ejecuta antes de insertar en la BD.
     * Asigna la fecha actual como fecha de la venta.
     */
    @PrePersist
    public void antesDeInsertar() {
        this.fechaVenta = LocalDate.now();
    }
}