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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa un agente inmobiliario (vendedor).
 * Mapea la tabla 'agentes' de PostgreSQL.
 *
 * @author [Tu nombre]
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    /** Estado del agente: true = activo, false = eliminado logicamente */
    @Column(name = "activo")
    private Boolean activo;

    /**
     * Metodo que se ejecuta antes de insertar en la BD.
     * Marca el agente como activo por defecto.
     */
    @PrePersist
    public void antesDeInsertar() {
        this.activo = true;
    }
}