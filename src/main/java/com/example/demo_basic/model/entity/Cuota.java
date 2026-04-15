package com.example.demo_basic.model.entity;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cuotas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cuota extends BaseEntity {

    @Column(name = "numero_cuota", nullable = false)
    private int numeroCuota;

    @Column(name = "valor", nullable = false)
    private Double valor;

    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento;

    @Column(name = "pagada", nullable = false)
    private boolean pagada;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prestamo_id", nullable = false)
    private Prestamo prestamo;

}
