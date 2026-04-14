package com.example.demo_basic.model.entity;

import com.example.demo_basic.model.enums.PuntajeCredito;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Cliente extends BaseEntity {

    @Column(name = "nombre", nullable = false, length = 80)
    private String nombre;

    @Column(name = "salario", nullable = false)
    private double salario;

    @Enumerated(EnumType.STRING)
    @Column(name = "puntaje_credito", nullable = false)
    private PuntajeCredito puntajeCredito;

}
