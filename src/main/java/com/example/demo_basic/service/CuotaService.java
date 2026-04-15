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
import com.example.demo_basic.repository.ClienteRepository;

@Service
public class CuotaService {

    @Autowired
    private CuotaRepository cuotaRepository;

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

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

    // ===== MÉTODOS MEJORADOS CON VALIDACIÓN DE CLIENTE =====

    /**
     * Obtener cuota de un cliente específico
     * @param clienteId ID del cliente
     * @param cuotaId ID de la cuota
     * @return Cuota si pertenece al cliente
     */
    public Cuota findByIdAndClienteId(Long clienteId, Long cuotaId) {
        // Validar que cliente existe
        clienteRepository.findById(clienteId)
                .orElseThrow(() -> new NoSuchElementException("Cliente no encontrado con id: " + clienteId));

        return cuotaRepository.findByIdAndClienteId(cuotaId, clienteId)
                .orElseThrow(() -> new NoSuchElementException(
                        "Cuota id: " + cuotaId + " no encontrada para cliente id: " + clienteId));
    }

    /**
     * Actualizar cuota validando que pertenezca a cliente específico
     * @param clienteId ID del cliente
     * @param cuotaId ID de la cuota
     * @param request Datos a actualizar
     * @return Cuota actualizada
     */
    @Transactional
    public Cuota updateCuotaByCliente(Long clienteId, Long cuotaId, Cuota request) {
        Cuota cuota = findByIdAndClienteId(clienteId, cuotaId);
        
        cuota.setValor(request.getValor());
        cuota.setNumeroCuota(request.getNumeroCuota());
        cuota.setPagada(request.isPagada());
        cuota.setFechaVencimiento(request.getFechaVencimiento());

        Cuota saved = cuotaRepository.save(cuota);
        verificarYActualizarEstadoPrestamo(saved.getPrestamo());
        return saved;
    }

    /**
     * Obtener todas las cuotas de un cliente
     */
    public List<Cuota> findAllByClienteId(Long clienteId) {
        clienteRepository.findById(clienteId)
                .orElseThrow(() -> new NoSuchElementException("Cliente no encontrado con id: " + clienteId));
        
        return cuotaRepository.findAllByClienteId(clienteId);
    }

    /**
     * Obtener cuotas de un préstamo específico
     */
    public List<Cuota> findByPrestamoId(Long prestamoId) {
        prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new NoSuchElementException("Préstamo no encontrado con id: " + prestamoId));
        
        return cuotaRepository.findByPrestamoId(prestamoId);
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
