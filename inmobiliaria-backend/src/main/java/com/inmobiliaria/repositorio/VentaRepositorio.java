package com.inmobiliaria.repositorio;

import com.inmobiliaria.modelo.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para la entidad Venta.
 * Spring Data JPA genera la implementacion SQL automaticamente.
 *
 * @author [Tu nombre]
 * @version 1.0
 */
@Repository
public interface VentaRepositorio extends JpaRepository<Venta, Integer> {

    /**
     * Obtiene todas las ventas de un cliente ordenadas por fecha descendente.
     *
     * @param idCliente identificador del cliente
     * @return lista de ventas del cliente
     */
    List<Venta> findByClienteIdClienteOrderByFechaVentaDesc(Integer idCliente);

    /**
     * Obtiene todas las ventas gestionadas por un agente ordenadas por fecha.
     *
     * @param idAgente identificador del agente
     * @return lista de ventas del agente
     */
    List<Venta> findByAgenteIdAgenteOrderByFechaVentaDesc(Integer idAgente);

    /**
     * Verifica si un lote ya tiene una venta registrada.
     *
     * @param idLote identificador del lote
     * @return true si el lote ya fue vendido
     */
    boolean existsByLoteIdLote(Integer idLote);

    /**
     * Consulta el resumen de ventas agrupado por agente.
     * Retorna nombre, apellido, cantidad de ventas y total vendido.
     *
     * @return lista de arrays con los datos del resumen
     */
    @Query("SELECT a.nombre, a.apellido, COUNT(v), SUM(v.precioFinal) " +
           "FROM Venta v " +
           "JOIN v.agente a " +
           "GROUP BY a.nombre, a.apellido " +
           "ORDER BY SUM(v.precioFinal) DESC")
    List<Object[]> obtenerResumenVentasPorAgente();
}
