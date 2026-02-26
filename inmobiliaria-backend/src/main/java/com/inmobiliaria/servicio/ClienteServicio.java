package com.inmobiliaria.servicio;

import com.inmobiliaria.modelo.Cliente;
import com.inmobiliaria.repositorio.ClienteRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio que implementa la logica de negocio para clientes.
 * Transactional garantiza integridad en operaciones de escritura.
 * readOnly = true optimiza las consultas que no modifican datos.
 *
 * @author [Tu nombre]
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class ClienteServicio {

    /** Repositorio para acceder a los datos de clientes */
    private final ClienteRepositorio clienteRepositorio;

    /**
     * Registra un nuevo cliente validando cedula y correo unicos.
     *
     * @param cliente datos del nuevo cliente
     * @return cliente guardado con su ID generado
     * @throws RuntimeException si la cedula o correo ya existen
     */
    @Transactional
    public Cliente crearCliente(Cliente cliente) {

        /* Validacion de negocio: la cedula debe ser unica */
        if (clienteRepositorio.existsByCedula(cliente.getCedula())) {
            throw new RuntimeException(
                    "Ya existe un cliente con la cedula: " + cliente.getCedula());
        }

        /* Validacion de negocio: el correo debe ser unico */
        if (clienteRepositorio.existsByCorreo(cliente.getCorreo())) {
            throw new RuntimeException(
                    "Ya existe un cliente con el correo: " + cliente.getCorreo());
        }

        /* Guardar el cliente en la base de datos */
        return clienteRepositorio.save(cliente);
    }

    /**
     * Obtiene todos los clientes activos ordenados por apellido.
     *
     * @return lista de clientes activos
     */
    @Transactional(readOnly = true)
    public List<Cliente> obtenerTodosLosClientes() {
        return clienteRepositorio.findByActivoTrueOrderByApellidoAscNombreAsc();
    }

    /**
     * Busca un cliente por su identificador.
     *
     * @param idCliente ID del cliente a buscar
     * @return cliente encontrado
     * @throws RuntimeException si el cliente no existe
     */
    @Transactional(readOnly = true)
    public Cliente obtenerClientePorId(Integer idCliente) {
        return clienteRepositorio.findById(idCliente)
                .orElseThrow(() -> new RuntimeException(
                        "Cliente no encontrado con ID: " + idCliente));
    }

    /**
     * Busca clientes por nombre o apellido usando busqueda parcial.
     *
     * @param texto texto a buscar en nombre o apellido
     * @return lista de clientes que coinciden
     */
    @Transactional(readOnly = true)
    public List<Cliente> buscarPorNombre(String texto) {
        return clienteRepositorio.buscarPorNombreOApellido(texto);
    }

    /**
     * Actualiza los datos de un cliente existente.
     * No permite modificar la cedula (identificador unico del cliente).
     *
     * @param idCliente ID del cliente a actualizar
     * @param datosNuevos objeto con los datos actualizados
     * @return cliente con los datos actualizados
     */
    @Transactional
    public Cliente actualizarCliente(Integer idCliente, Cliente datosNuevos) {

        /* Verificar que el cliente existe antes de actualizar */
        Cliente clienteExistente = obtenerClientePorId(idCliente);

        /* Actualizar solo los campos permitidos */
        clienteExistente.setNombre(datosNuevos.getNombre());
        clienteExistente.setApellido(datosNuevos.getApellido());
        clienteExistente.setCorreo(datosNuevos.getCorreo());
        clienteExistente.setTelefono(datosNuevos.getTelefono());
        clienteExistente.setDireccion(datosNuevos.getDireccion());

        return clienteRepositorio.save(clienteExistente);
    }

    /**
     * Desactiva un cliente sin borrar su registro (eliminacion logica).
     * El cliente queda con activo = false y no aparece en las consultas.
     *
     * @param idCliente ID del cliente a desactivar
     */
    @Transactional
    public void eliminarCliente(Integer idCliente) {
        Cliente cliente = obtenerClientePorId(idCliente);
        cliente.setActivo(false);
        clienteRepositorio.save(cliente);
    }
}