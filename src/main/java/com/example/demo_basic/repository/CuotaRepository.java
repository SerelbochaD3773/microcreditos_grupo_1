package com.example.demo_basic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo_basic.model.entity.Cuota;
import java.time.LocalDate;

@Repository
public interface CuotaRepository extends JpaRepository<Cuota, Long> {

    long countByPrestamoClienteIdAndPagadaFalseAndFechaVencimientoBefore(Long clienteId, LocalDate date);

}
