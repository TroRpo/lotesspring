package com.inmobiliaria.controlador;

import com.inmobiliaria.modelo.Venta;
import com.inmobiliaria.servicio.VentaServicio;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador REST para operaciones CRUD de ventas.
 * Expone los endpoints de la API en la ruta base: /api/ventas
 *
 * @author [Tu nombre]
 * @version 1.0
 */
@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class VentaControlador {

    /** Servicio con la logica de negocio de ventas */
    private final VentaServicio ventaServicio;

    /**
     * POST /api/ventas
     * Registra una nueva venta y marca el lote como VENDIDO.
     * Si el lote no esta disponible, retorna error.
     *
     * @param venta datos de la venta a registrar
     * @return venta registrada con estado HTTP 201
     */
    @PostMapping
    public ResponseEntity<Venta> registrarVenta(@Valid @RequestBody Venta venta) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ventaServicio.registrarVenta(venta));
    }

    /**
     * GET /api/ventas
     * Retorna todas las ventas registradas en el sistema.
     *
     * @return lista de ventas con estado HTTP 200
     */
    @GetMapping
    public ResponseEntity<List<Venta>> obtenerTodas() {
        return ResponseEntity.ok(ventaServicio.obtenerTodasLasVentas());
    }

    /**
     * GET /api/ventas/{id}
     * Retorna una venta especifica por su ID.
     *
     * @param id identificador de la venta
     * @return venta encontrada con estado HTTP 200
     */
    @GetMapping("/{id}")
    public ResponseEntity<Venta> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(ventaServicio.obtenerVentaPorId(id));
    }

    /**
     * GET /api/ventas/cliente/{idCliente}
     * Retorna todas las ventas de un cliente especifico.
     *
     * @param idCliente identificador del cliente
     * @return lista de ventas del cliente
     */
    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<Venta>> obtenerPorCliente(
            @PathVariable Integer idCliente) {
        return ResponseEntity.ok(
                ventaServicio.obtenerVentasPorCliente(idCliente));
    }

    /**
     * PATCH /api/ventas/{id}/observaciones?observaciones=texto
     * Actualiza unicamente las observaciones de una venta.
     *
     * @param id identificador de la venta
     * @param observaciones nuevas observaciones
     * @return venta con observaciones actualizadas
     */
    @PatchMapping("/{id}/observaciones")
    public ResponseEntity<Venta> actualizarObservaciones(
            @PathVariable Integer id,
            @RequestParam String observaciones) {
        return ResponseEntity.ok(
                ventaServicio.actualizarObservaciones(id, observaciones));
    }

    /**
     * DELETE /api/ventas/{id}
     * Cancela una venta y deja el lote como DISPONIBLE nuevamente.
     *
     * @param id identificador de la venta a cancelar
     * @return mensaje de confirmacion con estado HTTP 200
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancelarVenta(@PathVariable Integer id) {
        ventaServicio.cancelarVenta(id);
        return ResponseEntity.ok("Venta cancelada y lote liberado correctamente.");
    }
}