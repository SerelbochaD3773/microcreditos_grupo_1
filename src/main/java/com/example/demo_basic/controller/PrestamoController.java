package com.example.demo_basic.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo_basic.model.entity.Prestamo;
import com.example.demo_basic.service.PrestamoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/prestamos")
@Tag(name = "Prestamos", description = "Gestión de microcréditos")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    @Operation(summary = "Obtener todos los préstamos")
    @GetMapping
    public ResponseEntity<List<Prestamo>> findAll() {
        return ResponseEntity.ok(prestamoService.findAll());
    }

    @Operation(summary = "Obtener un préstamo por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Prestamo> findById(@PathVariable Long id) {
        return ResponseEntity.ok(prestamoService.findById(id));
    }

    @Operation(summary = "Crear un nuevo préstamo (con validación de negocio)")
    @PostMapping
    public ResponseEntity<Prestamo> save(@RequestBody Prestamo request) {
        return new ResponseEntity<>(prestamoService.crearPrestamo(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar un préstamo")
    @PutMapping("/{id}")
    public ResponseEntity<Prestamo> update(@PathVariable Long id, @RequestBody Prestamo request) {
        return ResponseEntity.ok(prestamoService.update(id, request));
    }

    @Operation(summary = "Eliminar un préstamo")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        prestamoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
