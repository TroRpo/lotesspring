package com.inmobiliaria.controlador;

import com.inmobiliaria.modelo.Lote;
import com.inmobiliaria.servicio.LoteServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * Controlador REST para operaciones CRUD de lotes inmobiliarios.
 * Expone los endpoints de la API en la ruta base: /api/lotes
 *
 * @author [Tu nombre]
 * @version 1.0
 */
@RestController
@RequestMapping("/api/lotes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class LoteControlador {

    /** Servicio con la logica de negocio de lotes */
    private final LoteServicio loteServicio;

    /**
     * POST /api/lotes
     * Crea un nuevo lote en el sistema.
     *
     * @param lote datos del lote a crear
     * @return lote creado con estado HTTP 201
     */
    @PostMapping
    public ResponseEntity<Lote> crearLote(@Valid @RequestBody Lote lote) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(loteServicio.crearLote(lote));
    }

    /**
     * GET /api/lotes
     * Retorna todos los lotes sin importar su estado.
     *
     * @return lista de lotes con estado HTTP 200
     */
    @GetMapping
    public ResponseEntity<List<Lote>> obtenerTodos() {
        return ResponseEntity.ok(loteServicio.obtenerTodosLosLotes());
    }

    /**
     * GET /api/lotes/{id}
     * Retorna un lote especifico por su ID.
     *
     * @param id identificador del lote
     * @return lote encontrado con estado HTTP 200
     */
    @GetMapping("/{id}")
    public ResponseEntity<Lote> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(loteServicio.obtenerLotePorId(id));
    }

    /**
     * GET /api/lotes/estado/{estado}
     * Retorna los lotes filtrados por estado.
     * Ejemplo: /api/lotes/estado/DISPONIBLE
     *
     * @param estado DISPONIBLE, RESERVADO o VENDIDO
     * @return lista de lotes con ese estado
     */
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Lote>> obtenerPorEstado(
            @PathVariable String estado) {
        return ResponseEntity.ok(loteServicio.obtenerLotesPorEstado(estado));
    }

    /**
     * GET /api/lotes/precio?min=50000000&max=90000000
     * Retorna lotes disponibles dentro de un rango de precio.
     *
     * @param min precio minimo de busqueda
     * @param max precio maximo de busqueda
     * @return lista de lotes en ese rango de precio
     */
    @GetMapping("/precio")
    public ResponseEntity<List<Lote>> obtenerPorRangoPrecio(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max) {
        return ResponseEntity.ok(
                loteServicio.obtenerLotesPorRangoPrecio(min, max));
    }

    /**
     * PUT /api/lotes/{id}
     * Actualiza todos los datos de un lote existente.
     *
     * @param id identificador del lote a actualizar
     * @param lote nuevos datos del lote
     * @return lote actualizado con estado HTTP 200
     */
    @PutMapping("/{id}")
    public ResponseEntity<Lote> actualizarLote(
            @PathVariable Integer id,
            @Valid @RequestBody Lote lote) {
        return ResponseEntity.ok(loteServicio.actualizarLote(id, lote));
    }

    /**
     * PATCH /api/lotes/{id}/estado?nuevoEstado=RESERVADO
     * Cambia unicamente el estado de un lote.
     *
     * @param id identificador del lote
     * @param nuevoEstado nuevo estado a asignar
     * @return lote con el estado actualizado
     */
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Lote> cambiarEstado(
            @PathVariable Integer id,
            @RequestParam String nuevoEstado) {
        return ResponseEntity.ok(loteServicio.cambiarEstado(id, nuevoEstado));
    }

    /**
     * DELETE /api/lotes/{id}
     * Elimina un lote fisicamente (solo si esta DISPONIBLE).
     *
     * @param id identificador del lote a eliminar
     * @return mensaje de confirmacion con estado HTTP 200
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarLote(@PathVariable Integer id) {
        loteServicio.eliminarLote(id);
        return ResponseEntity.ok("Lote eliminado correctamente.");
    }
}