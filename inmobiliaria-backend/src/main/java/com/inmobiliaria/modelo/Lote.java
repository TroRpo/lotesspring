package com.inmobiliaria.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
 * Entidad que representa un lote inmobiliario disponible para la venta.
 * Mapea la tabla 'lotes' de PostgreSQL.
 *
 * @author [Tu nombre]
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "lotes")
public class Lote {

    /** Estado: lote disponible para la venta */
    public static final String ESTADO_DISPONIBLE = "DISPONIBLE";

    /** Estado: lote reservado por un cliente */
    public static final String ESTADO_RESERVADO = "RESERVADO";

    /** Estado: lote ya vendido */
    public static final String ESTADO_VENDIDO = "VENDIDO";

    /** Identificador unico generado automaticamente */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lote")
    private Integer idLote;

    /** Codigo de referencia unico del lote (ej: LOT-001) */
    @NotBlank(message = "La referencia es obligatoria")
    @Column(name = "referencia", unique = true, nullable = false, length = 50)
    private String referencia;

    /** Direccion o descripcion de la ubicacion fisica del lote */
    @NotBlank(message = "La ubicacion es obligatoria")
    @Column(name = "ubicacion", nullable = false, length = 200)
    private String ubicacion;

    /** Municipio donde se encuentra el lote */
    @NotBlank(message = "El municipio es obligatorio")
    @Column(name = "municipio", nullable = false, length = 100)
    private String municipio;

    /** Departamento donde se encuentra el lote */
    @NotBlank(message = "El departamento es obligatorio")
    @Column(name = "departamento", nullable = false, length = 100)
    private String departamento;

    /** Area del lote en metros cuadrados */
    @NotNull(message = "El area es obligatoria")
    @Positive(message = "El area debe ser un valor positivo")
    @Column(name = "area_m2", nullable = false, precision = 10, scale = 2)
    private BigDecimal areaM2;

    /** Precio de venta del lote en pesos colombianos */
    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser un valor positivo")
    @Column(name = "precio", nullable = false, precision = 15, scale = 2)
    private BigDecimal precio;

    /** Descripcion detallada del lote */
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    /** Estado actual del lote: DISPONIBLE, RESERVADO o VENDIDO */
    @Column(name = "estado", length = 20)
    private String estado;

    /** Fecha en que se registro el lote, asignada automaticamente */
    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;

    /**
     * Metodo que se ejecuta antes de insertar en la BD.
     * Asigna la fecha actual y el estado DISPONIBLE si no se especifica.
     */
    @PrePersist
    public void antesDeInsertar() {
        this.fechaRegistro = LocalDate.now();
        if (this.estado == null) {
            this.estado = ESTADO_DISPONIBLE;
        }
    }
}