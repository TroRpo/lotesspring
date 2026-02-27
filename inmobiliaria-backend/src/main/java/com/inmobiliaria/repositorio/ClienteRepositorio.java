package com.inmobiliaria.repositorio;

import com.inmobiliaria.modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA para la entidad Cliente.
 * Spring Data JPA genera la implementacion SQL automaticamente.
 * JpaRepository provee: save, findById, findAll, delete, count, etc.
 *
 * @author [Tu nombre]
 * @version 1.0
 */
@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, Integer> {

    /**
     * Busca todos los clientes activos ordenados por apellido y nombre.
     *
     * @return lista de clientes activos
     */
    List<Cliente> findByActivoTrueOrderByApellidoAscNombreAsc();

    /**
     * Busca un cliente por su numero de cedula.
     *
     * @param cedula numero de identificacion
     * @return Optional con el cliente o vacio si no existe
     */
    Optional<Cliente> findByCedula(String cedula);

    /**
     * Verifica si ya existe un cliente con esa cedula.
     *
     * @param cedula cedula a verificar
     * @return true si ya existe
     */
    boolean existsByCedula(String cedula);

    /**
     * Verifica si ya existe un cliente con ese correo.
     *
     * @param correo correo a verificar
     * @return true si ya existe
     */
    boolean existsByCorreo(String correo);

    /**
     * Busca clientes cuyo nombre o apellido contenga el texto dado.
     *
     * @param texto texto a buscar
     * @return lista de clientes que coinciden
     */
    @Query("SELECT c FROM Cliente c " +
           "WHERE (LOWER(c.nombre) LIKE LOWER(CONCAT('%', :texto, '%')) " +
           "OR LOWER(c.apellido) LIKE LOWER(CONCAT('%', :texto, '%'))) " +
           "AND c.activo = TRUE " +
           "ORDER BY c.apellido")
    List<Cliente> buscarPorNombreOApellido(@Param("texto") String texto);
}
