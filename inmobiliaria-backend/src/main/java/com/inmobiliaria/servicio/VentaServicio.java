package com.inmobiliaria.servicio;

import com.inmobiliaria.modelo.Lote;
import com.inmobiliaria.modelo.Venta;
import com.inmobiliaria.repositorio.VentaRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio que implementa la logica de negocio para ventas.
 * Garantiza que no se venda un lote que no este disponible.
 * Usa transacciones para mantener la integridad de los datos.
 *
 * @author [Tu nombre]
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class VentaServicio {

    /** Repositorio para acceder a los datos de ventas */
    private final VentaRepositorio ventaRepositorio;

    /** Servicio de lotes para verificar disponibilidad y cambiar estado */
    private final LoteServicio loteServicio;

    /**
     * Registra una nueva venta y actualiza el estado del lote a VENDIDO.
     * Si el lote no esta disponible, lanza excepcion y no guarda nada.
     * La anotacion @Transactional garantiza que ambas operaciones
     * (guardar venta + actualizar lote) se completen o se cancelen juntas.
     *
     * @param venta datos de la venta a registrar
     * @return venta registrada con su ID generado
     * @throws RuntimeException si el lote no esta disponible
     */
    @Transactional
    public Venta registrarVenta(Venta venta) {

        /* Obtener el lote y verificar que este disponible */
        Lote lote = loteServicio.obtenerLotePorId(
                venta.getLote().getIdLote());

        /* Regla de negocio: solo se venden lotes disponibles */
        if (!Lote.ESTADO_DISPONIBLE.equals(lote.getEstado())) {
            throw new RuntimeException(
                    "El lote '" + lote.getReferencia() +
                            "' no esta disponible. Estado actual: " + lote.getEstado());
        }

        /* Guardar la venta en la base de datos */
        Venta ventaGuardada = ventaRepositorio.save(venta);

        /* Actualizar el estado del lote a VENDIDO */
        loteServicio.cambiarEstado(lote.getIdLote(), Lote.ESTADO_VENDIDO);

        return ventaGuardada;
    }

    /**
     * Obtiene todas las ventas registradas en el sistema.
     *
     * @return lista de todas las ventas
     */
    @Transactional(readOnly = true)
    public List<Venta> obtenerTodasLasVentas() {
        return ventaRepositorio.findAll();
    }

    /**
     * Busca una venta por su identificador.
     *
     * @param idVenta ID de la venta a buscar
     * @return venta encontrada
     * @throws RuntimeException si la venta no existe
     */
    @Transactional(readOnly = true)
    public Venta obtenerVentaPorId(Integer idVenta) {
        return ventaRepositorio.findById(idVenta)
                .orElseThrow(() -> new RuntimeException(
                        "Venta no encontrada con ID: " + idVenta));
    }

    /**
     * Obtiene todas las ventas realizadas por un cliente especifico.
     *
     * @param idCliente ID del cliente
     * @return lista de ventas del cliente ordenadas por fecha
     */
    @Transactional(readOnly = true)
    public List<Venta> obtenerVentasPorCliente(Integer idCliente) {
        return ventaRepositorio
                .findByClienteIdClienteOrderByFechaVentaDesc(idCliente);
    }

    /**
     * Actualiza las observaciones de una venta existente.
     *
     * @param idVenta ID de la venta
     * @param observaciones nuevas observaciones
     * @return venta con las observaciones actualizadas
     */
    @Transactional
    public Venta actualizarObservaciones(Integer idVenta, String observaciones) {
        Venta venta = obtenerVentaPorId(idVenta);
        venta.setObservaciones(observaciones);
        return ventaRepositorio.save(venta);
    }

    /**
     * Cancela una venta y libera el lote como DISPONIBLE.
     * Ambas operaciones se realizan en una sola transaccion.
     *
     * @param idVenta ID de la venta a cancelar
     */
    @Transactional
    public void cancelarVenta(Integer idVenta) {
        Venta venta = obtenerVentaPorId(idVenta);
        Integer idLote = venta.getLote().getIdLote();

        /* Eliminar la venta de la base de datos */
        ventaRepositorio.deleteById(idVenta);

        /* Liberar el lote para que quede disponible nuevamente */
        loteServicio.cambiarEstado(idLote, Lote.ESTADO_DISPONIBLE);
    }
}