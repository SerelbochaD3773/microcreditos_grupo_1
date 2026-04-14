package com.example.demo_basic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo_basic.model.entity.Cliente;
import com.example.demo_basic.service.ClienteService;

@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "Gestión de clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @Operation(summary = "Obtener todos los clientes")
    @GetMapping
    public ResponseEntity<List<Cliente>> findAll() {
        return ResponseEntity.ok(clienteService.findAll());
    }

    @Operation(summary = "Obtener un cliente por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> findById(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.findById(id));
    }

    @Operation(summary = "Crear un cliente")
    @PostMapping
    public ResponseEntity<Cliente> save(@RequestBody Cliente request) {
        return new ResponseEntity<>(clienteService.save(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar un cliente")
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> update(@PathVariable Long id, @RequestBody Cliente request) {
        return ResponseEntity.ok(clienteService.update(id, request));
    }

    @Operation(summary = "Eliminar un cliente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

