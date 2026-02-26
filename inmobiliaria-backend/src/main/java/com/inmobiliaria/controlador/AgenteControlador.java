package com.inmobiliaria.controlador;

import com.inmobiliaria.modelo.Agente;
import com.inmobiliaria.servicio.AgenteServicio;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador REST para operaciones CRUD de agentes inmobiliarios.
 * Expone los endpoints de la API en la ruta base: /api/agentes
 *
 * @author [Tu nombre]
 * @version 1.0
 */
@RestController
@RequestMapping("/api/agentes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AgenteControlador {

    /** Servicio con la logica de negocio de agentes */
    private final AgenteServicio agenteServicio;

    /**
     * POST /api/agentes
     * Crea un nuevo agente en el sistema.
     *
     * @param agente datos del agente a crear
     * @return agente creado con estado HTTP 201
     */
    @PostMapping
    public ResponseEntity<Agente> crearAgente(@Valid @RequestBody Agente agente) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(agenteServicio.crearAgente(agente));
    }

    /**
     * GET /api/agentes
     * Retorna la lista de todos los agentes activos.
     *
     * @return lista de agentes con estado HTTP 200
     */
    @GetMapping
    public ResponseEntity<List<Agente>> obtenerTodos() {
        return ResponseEntity.ok(agenteServicio.obtenerTodosLosAgentes());
    }

    /**
     * GET /api/agentes/{id}
     * Retorna un agente especifico por su ID.
     *
     * @param id identificador del agente
     * @return agente encontrado con estado HTTP 200
     */
    @GetMapping("/{id}")
    public ResponseEntity<Agente> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(agenteServicio.obtenerAgentePorId(id));
    }

    /**
     * PUT /api/agentes/{id}
     * Actualiza los datos de un agente existente.
     *
     * @param id identificador del agente a actualizar
     * @param agente nuevos datos del agente
     * @return agente actualizado con estado HTTP 200
     */
    @PutMapping("/{id}")
    public ResponseEntity<Agente> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody Agente agente) {
        return ResponseEntity.ok(agenteServicio.actualizarAgente(id, agente));
    }

    /**
     * DELETE /api/agentes/{id}
     * Desactiva un agente (eliminacion logica).
     *
     * @param id identificador del agente a desactivar
     * @return mensaje de confirmacion con estado HTTP 200
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        agenteServicio.eliminarAgente(id);
        return ResponseEntity.ok("Agente desactivado correctamente.");
    }
}