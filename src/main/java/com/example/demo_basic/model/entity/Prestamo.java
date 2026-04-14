package com.example.demo_basic.model.entity;

import java.util.ArrayList;
import com.example.demo_basic.model.enums.EstadoCredito;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "prestamos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Prestamo extends BaseEntity {

    @Column(name = "monto", nullable = false)
    private double monto;

    @Column(name = "tasa_interes", nullable = false)
    private double tasaInteres;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private EstadoCredito estadoCredito;

    @Column(name = "cuotas_pactadas", nullable = false)
    private Integer cuotasPactadas;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "prestamo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Cuota> cuotas = new ArrayList<>();

}
