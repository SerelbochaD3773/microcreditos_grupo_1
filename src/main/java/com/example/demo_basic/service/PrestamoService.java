package com.example.demo_basic.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo_basic.model.entity.Cliente;
import com.example.demo_basic.model.entity.Cuota;
import com.example.demo_basic.model.entity.Prestamo;
import com.example.demo_basic.model.enums.EstadoCredito;
import com.example.demo_basic.model.enums.PuntajeCredito;
import com.example.demo_basic.repository.CuotaRepository;
import com.example.demo_basic.repository.PrestamoRepository;

@Service
public class PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private CuotaRepository cuotaRepository;

    public List<Prestamo> findAll() {
        return prestamoRepository.findAll();
    }

    public Prestamo findById(Long id) {
        return findEntity(id);
    }

    @Transactional
    public Prestamo crearPrestamo(Prestamo request) {
        Cliente cliente = clienteService.findEntity(request.getCliente().getId());

        validarMontoMaximo(request.getMonto(), cliente.getSalario());
        validarSinCuotasVencidas(cliente.getId());

        double tasaFinal = calcularTasaConRecargo(request.getTasaInteres(), cliente.getPuntajeCredito());
        double totalInteres = request.getMonto() * tasaFinal;
        double valorCuota = (request.getMonto() + totalInteres) / request.getCuotasPactadas();

        request.setCliente(cliente);
        request.setTasaInteres(tasaFinal);
        request.setEstadoCredito(EstadoCredito.APROBADO);
        
        generarCuotas(request, valorCuota);

        return prestamoRepository.save(request);
    }

    private void validarMontoMaximo(double monto, double salario) {
        if (monto > salario * 3) {
            throw new IllegalArgumentException("El monto del préstamo no puede superar 3 veces el salario del cliente.");
        }
    }

    private void validarSinCuotasVencidas(Long clienteId) {
        long vencidas = cuotaRepository.countByPrestamoClienteIdAndPagadaFalseAndFechaVencimientoBefore(clienteId, LocalDate.now());
        if (vencidas > 0) {
            throw new IllegalArgumentException("No se puede aprobar el préstamo porque el cliente tiene cuotas vencidas.");
        }
    }

    private double calcularTasaConRecargo(double tasaBase, PuntajeCredito puntaje) {
        if (puntaje == PuntajeCredito.BAJO) {
            return tasaBase + 0.02; // 2% de recargo
        }
        return tasaBase;
    }

    private void generarCuotas(Prestamo prestamo, double valorCuota) {
        List<Cuota> cuotas = new ArrayList<>();
        for (int i = 1; i <= prestamo.getCuotasPactadas(); i++) {
            Cuota cuota = new Cuota();
            cuota.setNumeroCuota(i);
            cuota.setValor(valorCuota);
            cuota.setFechaVencimiento(LocalDate.now().plusMonths(i));
            cuota.setPagada(false);
            cuota.setPrestamo(prestamo);
            cuotas.add(cuota);
        }
        prestamo.setCuotas(cuotas);
    }

    @Transactional
    public Prestamo save(Prestamo request) {
        return prestamoRepository.save(request);
    }

    @Transactional
    public Prestamo update(Long id, Prestamo request) {
        Prestamo prestamo = findEntity(id);
        prestamo.setTasaInteres(request.getTasaInteres());
        prestamo.setCuotasPactadas(request.getCuotasPactadas());
        return prestamoRepository.save(prestamo);
    }

    @Transactional
    public void delete(Long id) {
        findEntity(id);
        prestamoRepository.deleteById(id);
    }

    public Prestamo findEntity(Long id) {
        return prestamoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Prestamo no encontrado con id: " + id));
    }
}
