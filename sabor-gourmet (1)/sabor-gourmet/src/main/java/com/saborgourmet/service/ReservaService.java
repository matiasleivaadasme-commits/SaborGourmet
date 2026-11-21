
package com.saborgourmet.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.saborgourmet.entity.Reserva;
import com.saborgourmet.repository.ReservaRepository;

// Indica que esta clase contiene la lógica de servicio para administrar las reservas.
@Service
public class ReservaService {

    // Repositorio encargado de acceder a los datos de las reservas.
    private final ReservaRepository reservaRepository;

    // Constructor que recibe el repositorio mediante inyección de dependencias.
    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    // Obtiene y devuelve todas las reservas registradas.
    public List<Reserva> listarReservas() {
        return reservaRepository.findAll();
    }

    // Guarda una reserva nueva o actualiza una reserva existente.
    public Reserva guardarReserva(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    // Busca una reserva utilizando su identificador.
    // Si no existe, devuelve null.
    public Reserva buscarReserva(Long id) {
        return reservaRepository.findById(id).orElse(null);
    }

    // Elimina una reserva de la base de datos mediante su identificador.
    public void eliminarReserva(Long id) {
        reservaRepository.deleteById(id);
    }

}
