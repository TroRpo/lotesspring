package com.inmobiliaria.repositorio;

import com.inmobiliaria.modelo.Agente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA para la entidad Agente.
 * Spring Data JPA genera la implementacion SQL automaticamente.
 *
 * @author [Tu nombre]
 * @version 1.0
 */
@Repository
public interface AgenteRepositorio extends JpaRepository<Agente, Integer> {

    /**
     * Busca todos los agentes activos ordenados por apellido.
     *
     * @return lista de agentes activos
     */
    List<Agente> findByActivoTrueOrderByApellidoAsc();

    /**
     * Busca un agente por su numero de cedula.
     *
     * @param cedula numero de identificacion
     * @return Optional con el agente o vacio si no existe
     */
    Optional<Agente> findByCedula(String cedula);

    /**
     * Verifica si ya existe un agente con esa cedula.
     *
     * @param cedula cedula a verificar
     * @return true si ya existe
     */
    boolean existsByCedula(String cedula);
}