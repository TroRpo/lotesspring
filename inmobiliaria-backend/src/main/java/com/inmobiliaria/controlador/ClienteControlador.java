package com.inmobiliaria.controlador;

import com.inmobiliaria.modelo.Cliente;
import com.inmobiliaria.servicio.ClienteServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador REST para operaciones CRUD de clientes.
 * Expone los endpoints de la API en la ruta base: /api/clientes
 * Todos los endpoints retornan JSON automaticamente.
 *
 * @author [Tu nombre]
 * @version 1.0
 */
@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ClienteControlador {

    /** Servicio con la logica de negocio de clientes */
    private final ClienteServicio clienteServicio;

    /**
     * POST /api/clientes
     * Crea un nuevo cliente en el sistema.
     * @Valid activa las validaciones del modelo.
     *
     * @param cliente datos del cliente a crear
     * @return cliente creado con estado HTTP 201
     */
    @PostMapping
    public ResponseEntity<Cliente> crearCliente(@Valid @RequestBody Cliente cliente) {
        Cliente clienteCreado = clienteServicio.crearCliente(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteCreado);
    }

    /**
     * GET /api/clientes
     * Retorna la lista de todos los clientes activos.
     *
     * @return lista de clientes con estado HTTP 200
     */
    @GetMapping
    public ResponseEntity<List<Cliente>> obtenerTodos() {
        return ResponseEntity.ok(clienteServicio.obtenerTodosLosClientes());
    }

    /**
     * GET /api/clientes/{id}
     * Retorna un cliente especifico por su ID.
     *
     * @param id identificador del cliente
     * @return cliente encontrado con estado HTTP 200
     */
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(clienteServicio.obtenerClientePorId(id));
    }

    /**
     * GET /api/clientes/buscar?texto=carlos
     * Busca clientes cuyo nombre o apellido contenga el texto.
     *
     * @param texto texto a buscar
     * @return lista de clientes que coinciden
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<Cliente>> buscarPorNombre(
            @RequestParam String texto) {
        return ResponseEntity.ok(clienteServicio.buscarPorNombre(texto));
    }

    /**
     * PUT /api/clientes/{id}
     * Actualiza los datos de un cliente existente.
     *
     * @param id identificador del cliente a actualizar
     * @param cliente nuevos datos del cliente
     * @return cliente actualizado con estado HTTP 200
     */
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(
            @PathVariable Integer id,
            @Valid @RequestBody Cliente cliente) {
        return ResponseEntity.ok(clienteServicio.actualizarCliente(id, cliente));
    }

    /**
     * DELETE /api/clientes/{id}
     * Desactiva un cliente (eliminacion logica, no borra el registro).
     *
     * @param id identificador del cliente a desactivar
     * @return mensaje de confirmacion con estado HTTP 200
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCliente(@PathVariable Integer id) {
        clienteServicio.eliminarCliente(id);
        return ResponseEntity.ok("Cliente desactivado correctamente.");
    }
}