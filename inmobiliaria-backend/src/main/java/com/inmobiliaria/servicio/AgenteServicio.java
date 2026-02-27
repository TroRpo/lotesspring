package com.inmobiliaria.servicio;

import com.inmobiliaria.modelo.Agente;
import com.inmobiliaria.repositorio.AgenteRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio que implementa la logica de negocio para agentes inmobiliarios.
 *
 * @author [Tu nombre]
 * @version 1.0
 */
@Service
public class AgenteServicio {

    /** Repositorio para acceder a los datos de agentes */
    private final AgenteRepositorio agenteRepositorio;

    /**
     * Constructor con inyeccion de dependencias.
     *
     * @param agenteRepositorio repositorio de agentes
     */
    public AgenteServicio(AgenteRepositorio agenteRepositorio) {
        this.agenteRepositorio = agenteRepositorio;
    }

    /**
     * Registra un nuevo agente validando que la cedula sea unica.
     *
     * @param agente datos del nuevo agente
     * @return agente guardado con su ID generado
     * @throws RuntimeException si ya existe un agente con esa cedula
     */
    @Transactional
    public Agente crearAgente(Agente agente) {

        /* Validacion: la cedula del agente debe ser unica */
        if (agenteRepositorio.existsByCedula(agente.getCedula())) {
            throw new RuntimeException(
                "Ya existe un agente con la cedula: " + agente.getCedula());
        }

        return agenteRepositorio.save(agente);
    }

    /**
     * Obtiene todos los agentes activos ordenados por apellido.
     *
     * @return lista de agentes activos
     */
    @Transactional(readOnly = true)
    public List<Agente> obtenerTodosLosAgentes() {
        return agenteRepositorio.findByActivoTrueOrderByApellidoAsc();
    }

    /**
     * Busca un agente por su identificador.
     *
     * @param idAgente ID del agente a buscar
     * @return agente encontrado
     * @throws RuntimeException si el agente no existe
     */
    @Transactional(readOnly = true)
    public Agente obtenerAgentePorId(Integer idAgente) {
        return agenteRepositorio.findById(idAgente)
            .orElseThrow(() -> new RuntimeException(
                "Agente no encontrado con ID: " + idAgente));
    }

    /**
     * Actualiza los datos de un agente existente.
     *
     * @param idAgente ID del agente a actualizar
     * @param datosNuevos nuevos datos del agente
     * @return agente con los datos actualizados
     */
    @Transactional
    public Agente actualizarAgente(Integer idAgente, Agente datosNuevos) {

        /* Verificar que el agente existe antes de actualizar */
        Agente agenteExistente = obtenerAgentePorId(idAgente);

        /* Actualizar los campos del agente */
        agenteExistente.setNombre(datosNuevos.getNombre());
        agenteExistente.setApellido(datosNuevos.getApellido());
        agenteExistente.setCorreo(datosNuevos.getCorreo());
        agenteExistente.setTelefono(datosNuevos.getTelefono());

        return agenteRepositorio.save(agenteExistente);
    }

    /**
     * Desactiva un agente sin borrar su registro (eliminacion logica).
     *
     * @param idAgente ID del agente a desactivar
     */
    @Transactional
    public void eliminarAgente(Integer idAgente) {
        Agente agente = obtenerAgentePorId(idAgente);
        agente.setActivo(false);
        agenteRepositorio.save(agente);
    }
}
