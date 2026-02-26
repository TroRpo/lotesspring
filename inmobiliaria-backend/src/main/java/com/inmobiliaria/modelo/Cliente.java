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
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Entidad que representa un cliente del sistema inmobiliario.
 * Mapea la tabla 'clientes' de PostgreSQL.
 * Usa anotaciones JPA para mapeo objeto-relacional.
 *
 * @author [Tu nombre]
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clientes")
public class Cliente {

    /** Identificador unico generado automaticamente */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Integer idCliente;

    /** Cedula de identidad del cliente, debe ser unica */
    @NotBlank(message = "La cedula es obligatoria")
    @Size(max = 20, message = "La cedula no puede tener mas de 20 caracteres")
    @Column(name = "cedula", unique = true, nullable = false, length = 20)
    private String cedula;

    /** Nombre del cliente */
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede tener mas de 100 caracteres")
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    /** Apellido del cliente */
    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 100, message = "El apellido no puede tener mas de 100 caracteres")
    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;

    /** Correo electronico, debe ser unico y tener formato valido */
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo no tiene un formato valido")
    @Column(name = "correo", unique = true, nullable = false, length = 150)
    private String correo;

    /** Numero de telefono del cliente */
    @Column(name = "telefono", length = 20)
    private String telefono;

    /** Direccion de residencia del cliente */
    @Column(name = "direccion", length = 200)
    private String direccion;

    /** Fecha en que se registro el cliente, se asigna automaticamente */
    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;

    /** Estado del cliente: true = activo, false = eliminado logicamente */
    @Column(name = "activo")
    private Boolean activo;

    /**
     * Metodo que se ejecuta automaticamente antes de insertar en la BD.
     * Asigna la fecha actual y marca el cliente como activo.
     */
    @PrePersist
    public void antesDeInsertar() {
        this.fechaRegistro = LocalDate.now();
        this.activo = true;
    }
}