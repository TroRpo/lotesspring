package com.inmobiliaria.servicio;

import com.inmobiliaria.modelo.Lote;
import com.inmobiliaria.repositorio.LoteRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Servicio que implementa la logica de negocio para lotes inmobiliarios.
 *
 * @author [Tu nombre]
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class LoteServicio {

    /** Repositorio para acceder a los datos de lotes */
    private final LoteRepositorio loteRepositorio;

    /**
     * Registra un nuevo lote validando que la referencia sea unica.
     *
     * @param lote datos del nuevo lote
     * @return lote guardado con su ID generado
     * @throws RuntimeException si ya existe un lote con esa referencia
     */
    @Transactional
    public Lote crearLote(Lote lote) {

        /* Validacion: la referencia del lote debe ser unica */
        if (loteRepositorio.existsByReferencia(lote.getReferencia())) {
            throw new RuntimeException(
                    "Ya existe un lote con la referencia: " + lote.getReferencia());
        }

        return loteRepositorio.save(lote);
    }

    /**
     * Obtiene todos los lotes sin importar su estado.
     *
     * @return lista de todos los lotes
     */
    @Transactional(readOnly = true)
    public List<Lote> obtenerTodosLosLotes() {
        return loteRepositorio.findAll();
    }

    /**
     * Busca un lote por su identificador.
     *
     * @param idLote ID del lote a buscar
     * @return lote encontrado
     * @throws RuntimeException si el lote no existe
     */
    @Transactional(readOnly = true)
    public Lote obtenerLotePorId(Integer idLote) {
        return loteRepositorio.findById(idLote)
                .orElseThrow(() -> new RuntimeException(
                        "Lote no encontrado con ID: " + idLote));
    }

    /**
     * Filtra lotes por su estado actual.
     *
     * @param estado DISPONIBLE, RESERVADO o VENDIDO
     * @return lista de lotes con ese estado ordenados por precio
     */
    @Transactional(readOnly = true)
    public List<Lote> obtenerLotesPorEstado(String estado) {
        return loteRepositorio.findByEstadoOrderByPrecioAsc(estado.toUpperCase());
    }

    /**
     * Busca lotes disponibles dentro de un rango de precio.
     *
     * @param min precio minimo
     * @param max precio maximo
     * @return lista de lotes disponibles en ese rango
     */
    @Transactional(readOnly = true)
    public List<Lote> obtenerLotesPorRangoPrecio(BigDecimal min, BigDecimal max) {
        return loteRepositorio.buscarDisponiblesPorRangoPrecio(min, max);
    }

    /**
     * Actualiza los datos de un lote existente.
     * No permite cambiar la referencia del lote.
     *
     * @param idLote ID del lote a actualizar
     * @param datosNuevos nuevos datos del lote
     * @return lote con los datos actualizados
     */
    @Transactional
    public Lote actualizarLote(Integer idLote, Lote datosNuevos) {

        /* Verificar que el lote existe antes de actualizar */
        Lote loteExistente = obtenerLotePorId(idLote);

        /* Actualizar campos del lote */
        loteExistente.setUbicacion(datosNuevos.getUbicacion());
        loteExistente.setMunicipio(datosNuevos.getMunicipio());
        loteExistente.setDepartamento(datosNuevos.getDepartamento());
        loteExistente.setAreaM2(datosNuevos.getAreaM2());
        loteExistente.setPrecio(datosNuevos.getPrecio());
        loteExistente.setDescripcion(datosNuevos.getDescripcion());
        loteExistente.setEstado(datosNuevos.getEstado());

        return loteRepositorio.save(loteExistente);
    }

    /**
     * Cambia el estado de un lote.
     * Usado internamente cuando se registra o cancela una venta.
     *
     * @param idLote ID del lote
     * @param nuevoEstado nuevo estado a asignar
     * @return lote con el estado actualizado
     */
    @Transactional
    public Lote cambiarEstado(Integer idLote, String nuevoEstado) {
        Lote lote = obtenerLotePorId(idLote);
        lote.setEstado(nuevoEstado.toUpperCase());
        return loteRepositorio.save(lote);
    }

    /**
     * Elimina un lote fisicamente de la base de datos.
     * Solo se permite eliminar lotes en estado DISPONIBLE.
     *
     * @param idLote ID del lote a eliminar
     * @throws RuntimeException si el lote no esta disponible
     */
    @Transactional
    public void eliminarLote(Integer idLote) {
        Lote lote = obtenerLotePorId(idLote);

        /* Regla de negocio: solo se eliminan lotes disponibles */
        if (!Lote.ESTADO_DISPONIBLE.equals(lote.getEstado())) {
            throw new RuntimeException(
                    "Solo se pueden eliminar lotes en estado DISPONIBLE. " +
                            "Estado actual: " + lote.getEstado());
        }

        loteRepositorio.deleteById(idLote);
    }
}