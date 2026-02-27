package com.inmobiliaria.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Entidad que representa un agente inmobiliario (vendedor).
 * Mapea la tabla agentes de PostgreSQL.
 *
 * @author [Tu nombre]
 * @version 1.0
 */
@Entity
@Table(name = "agentes")
public class Agente {

    /** Identificador unico generado automaticamente */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_agente")
    private Integer idAgente;

    /** Cedula de identidad del agente, debe ser unica */
    @NotBlank(message = "La cedula es obligatoria")
    @Column(name = "cedula", unique = true, nullable = false, length = 20)
    private String cedula;

    /** Nombre del agente */
    @NotBlank(message = "El nombre es obligatorio")
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    /** Apellido del agente */
    @NotBlank(message = "El apellido es obligatorio")
    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;

    /** Correo electronico del agente */
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Formato de correo invalido")
    @Column(name = "correo", unique = true, nullable = false, length = 150)
    private String correo;

    /** Numero de telefono del agente */
    @Column(name = "telefono", length = 20)
    private String telefono;

    /** Estado activo o inactivo del agente */
    @Column(name = "activo")
    private Boolean activo;

    /** Constructor vacio requerido por JPA */
    public Agente() {}

    /**
     * Metodo que se ejecuta antes de insertar en la BD.
     * Marca el agente como activo por defecto.
     */
    @PrePersist
    public void antesDeInsertar() {
        this.activo = true;
    }

    /* Getters y Setters */

    public Integer getIdAgente() {
        return idAgente;
    }

    public void setIdAgente(Integer idAgente) {
        this.idAgente = idAgente;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
