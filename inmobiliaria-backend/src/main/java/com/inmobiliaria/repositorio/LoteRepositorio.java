package com.inmobiliaria.repositorio;

import com.inmobiliaria.modelo.Lote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA para la entidad Lote.
 * Spring Data JPA genera la implementacion SQL automaticamente.
 *
 * @author [Tu nombre]
 * @version 1.0
 */
@Repository
public interface LoteRepositorio extends JpaRepository<Lote, Integer> {

    /**
     * Busca lotes por su estado ordenados por precio ascendente.
     *
     * @param estado DISPONIBLE, RESERVADO o VENDIDO
     * @return lista de lotes con ese estado
     */
    List<Lote> findByEstadoOrderByPrecioAsc(String estado);

    /**
     * Busca un lote por su referencia unica.
     *
     * @param referencia codigo de referencia del lote
     * @return Optional con el lote o vacio si no existe
     */
    Optional<Lote> findByReferencia(String referencia);

    /**
     * Verifica si ya existe un lote con esa referencia.
     *
     * @param referencia referencia a verificar
     * @return true si ya existe
     */
    boolean existsByReferencia(String referencia);

    /**
     * Busca lotes disponibles dentro de un rango de precio.
     *
     * @param precioMin precio minimo de busqueda
     * @param precioMax precio maximo de busqueda
     * @return lista de lotes disponibles en ese rango
     */
    @Query("SELECT l FROM Lote l " +
            "WHERE l.precio BETWEEN :precioMin AND :precioMax " +
            "AND l.estado = 'DISPONIBLE' " +
            "ORDER BY l.precio ASC")
    List<Lote> buscarDisponiblesPorRangoPrecio(
            @Param("precioMin") BigDecimal precioMin,
            @Param("precioMax") BigDecimal precioMax);

    /**
     * Busca lotes por municipio y estado (sin importar mayusculas).
     *
     * @param municipio nombre del municipio
     * @param estado estado del lote
     * @return lista de lotes que coinciden
     */
    List<Lote> findByMunicipioIgnoreCaseAndEstado(String municipio, String estado);
}