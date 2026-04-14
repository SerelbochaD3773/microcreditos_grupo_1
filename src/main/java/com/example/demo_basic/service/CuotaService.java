package com.example.demo_basic.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo_basic.model.entity.Cuota;
import com.example.demo_basic.model.entity.Prestamo;
import com.example.demo_basic.model.enums.EstadoCredito;
import com.example.demo_basic.repository.CuotaRepository;
import com.example.demo_basic.repository.PrestamoRepository;

@Service
public class CuotaService {

    @Autowired
    private CuotaRepository cuotaRepository;

    @Autowired
    private PrestamoRepository prestamoRepository;

    public List<Cuota> findAll() {
        return cuotaRepository.findAll();
    }

    public Cuota findById(Long id) {
        return findEntity(id);
    }

    @Transactional
    public Cuota save(Cuota request) {
        return cuotaRepository.save(request);
    }

    @Transactional
    public Cuota registrarPago(Long id) {
        Cuota cuota = findEntity(id);
        cuota.setPagada(true);
        Cuota savedCuota = cuotaRepository.save(cuota);

        verificarYActualizarEstadoPrestamo(cuota.getPrestamo());

        return savedCuota;
    }

    private void verificarYActualizarEstadoPrestamo(Prestamo prestamo) {
        boolean todasPagadas = prestamo.getCuotas().stream()
                .allMatch(Cuota::isPagada);

        if (todasPagadas) {
            prestamo.setEstadoCredito(EstadoCredito.PAGADO);
            prestamoRepository.save(prestamo);
        }
    }

    @Transactional
    public Cuota update(Long id, Cuota request) {
        Cuota cuota = findEntity(id);
        cuota.setValor(request.getValor());
        cuota.setNumeroCuota(request.getNumeroCuota());
        cuota.setPagada(request.isPagada());
        cuota.setFechaVencimiento(request.getFechaVencimiento());

        Cuota saved = cuotaRepository.save(cuota);
        verificarYActualizarEstadoPrestamo(saved.getPrestamo());
        return saved;
    }

    @Transactional
    public void delete(Long id) {
        findEntity(id);
        cuotaRepository.deleteById(id);
    }

    public Cuota findEntity(Long id) {
        return cuotaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cuota no encontrada con id: " + id));
    }

}
