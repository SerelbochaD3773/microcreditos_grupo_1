package com.example.demo_basic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.demo_basic.model.entity.Cuota;
import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

@Repository
public interface CuotaRepository extends JpaRepository<Cuota, Long> {

    long countByPrestamoClienteIdAndPagadaFalseAndFechaVencimientoBefore(Long clienteId, LocalDate date);

    // Buscar cuota por ID y validar que pertenezca a cliente específico
    @Query("SELECT c FROM Cuota c WHERE c.id = :cuotaId AND c.prestamo.cliente.id = :clienteId")
    Optional<Cuota> findByIdAndClienteId(@Param("cuotaId") Long cuotaId, @Param("clienteId") Long clienteId);

    // Buscar todas las cuotas de un cliente
    @Query("SELECT c FROM Cuota c WHERE c.prestamo.cliente.id = :clienteId ORDER BY c.prestamo.id, c.numeroCuota")
    List<Cuota> findAllByClienteId(@Param("clienteId") Long clienteId);

    // Buscar cuotas de un préstamo específico
    @Query("SELECT c FROM Cuota c WHERE c.prestamo.id = :prestamoId ORDER BY c.numeroCuota")
    List<Cuota> findByPrestamoId(@Param("prestamoId") Long prestamoId);

}
