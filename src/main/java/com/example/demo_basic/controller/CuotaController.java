package com.example.demo_basic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo_basic.model.entity.Cuota;
import com.example.demo_basic.service.CuotaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/cuotas")
@Tag(name = "Cuotas", description = "Gestión de cuotas")
public class CuotaController {
    @Autowired
    private CuotaService cuotaService;

    @Operation(summary = "Obtener todas las cuotas")
    @GetMapping
    public ResponseEntity<List<Cuota>> findAll() {
        return ResponseEntity.ok(cuotaService.findAll());
    }

    @Operation(summary = "Obtener cuota por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Cuota> findById(@PathVariable Long id) {
        return ResponseEntity.ok(cuotaService.findById(id));
    }

    @Operation(summary = "Crear nueva cuota")
    @PostMapping
    public ResponseEntity<Cuota> save(@RequestBody Cuota cuota) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cuotaService.save(cuota));
    }

    @Operation(summary = "Registrar pago de una cuota")
    @PatchMapping("/{id}/pagar")
    public ResponseEntity<Cuota> registrarPago(@PathVariable Long id) {
        return ResponseEntity.ok(cuotaService.registrarPago(id));
    }

    @Operation(summary = "Actualizar cuota")
    @PutMapping("/{id}")
    public ResponseEntity<Cuota> update(@PathVariable Long id, @RequestBody Cuota cuota) {
        return ResponseEntity.ok(cuotaService.update(id, cuota));
    }

    @Operation(summary = "Eliminar cuota")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cuotaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ========== ENDPOINTS CON VALIDACIÓN DE CLIENTE ==========

    @Operation(summary = "Obtener todas las cuotas de un cliente")
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Cuota>> findAllByClienteId(@PathVariable Long clienteId) {
        return ResponseEntity.ok(cuotaService.findAllByClienteId(clienteId));
    }

    @Operation(summary = "Obtener cuota específica de un cliente")
    @GetMapping("/cliente/{clienteId}/cuota/{cuotaId}")
    public ResponseEntity<Cuota> findByIdAndClienteId(
            @PathVariable Long clienteId,
            @PathVariable Long cuotaId) {
        return ResponseEntity.ok(cuotaService.findByIdAndClienteId(clienteId, cuotaId));
    }

    @Operation(summary = "Actualizar cuota de un cliente específico")
    @PutMapping("/cliente/{clienteId}/cuota/{cuotaId}")
    public ResponseEntity<Cuota> updateCuotaByCliente(
            @PathVariable Long clienteId,
            @PathVariable Long cuotaId,
            @RequestBody Cuota cuota) {
        return ResponseEntity.ok(cuotaService.updateCuotaByCliente(clienteId, cuotaId, cuota));
    }

    @Operation(summary = "Obtener todas las cuotas de un préstamo")
    @GetMapping("/prestamo/{prestamoId}")
    public ResponseEntity<List<Cuota>> findByPrestamoId(@PathVariable Long prestamoId) {
        return ResponseEntity.ok(cuotaService.findByPrestamoId(prestamoId));
    }
}
