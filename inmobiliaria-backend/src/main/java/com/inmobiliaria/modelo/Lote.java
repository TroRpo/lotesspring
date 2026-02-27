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

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entidad que representa un lote inmobiliario disponible para la venta.
 * Mapea la tabla lotes de PostgreSQL.
 *
 * @author [Tu nombre]
 * @version 1.0
 */
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

    /** Codigo de referencia unico del lote */
    @NotBlank(message = "La referencia es obligatoria")
    @Column(name = "referencia", unique = true, nullable = false, length = 50)
    private String referencia;

    /** Ubicacion fisica del lote */
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

    /** Precio de venta del lote */
    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser un valor positivo")
    @Column(name = "precio", nullable = false, precision = 15, scale = 2)
    private BigDecimal precio;

    /** Descripcion detallada del lote */
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    /** Estado actual: DISPONIBLE, RESERVADO o VENDIDO */
    @Column(name = "estado", length = 20)
    private String estado;

    /** Fecha de registro del lote */
    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;

    /** Constructor vacio requerido por JPA */
    public Lote() {}

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

    /* Getters y Setters */

    public Integer getIdLote() {
        return idLote;
    }

    public void setIdLote(Integer idLote) {
        this.idLote = idLote;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public BigDecimal getAreaM2() {
        return areaM2;
    }

    public void setAreaM2(BigDecimal areaM2) {
        this.areaM2 = areaM2;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
