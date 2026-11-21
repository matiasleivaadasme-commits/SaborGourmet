
package com.saborgourmet.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.saborgourmet.entity.Mesa;
import com.saborgourmet.repository.MesaRepository;

// Indica que esta clase contiene la lógica de servicio para administrar las mesas.
@Service
public class MesaService {

    // Repositorio encargado de acceder a los datos de las mesas.
    private final MesaRepository mesaRepository;

    // Constructor que recibe el repositorio mediante inyección de dependencias.
    public MesaService(MesaRepository mesaRepository) {
        this.mesaRepository = mesaRepository;
    }

    // Obtiene y devuelve todas las mesas registradas.
    public List<Mesa> listarMesas() {
        return mesaRepository.findAll();
    }

    // Guarda una mesa nueva o actualiza una mesa existente.
    public Mesa guardarMesa(Mesa mesa) {
        return mesaRepository.save(mesa);
    }

    // Busca una mesa por su identificador.
    // Si no existe, devuelve null.
    public Mesa buscarMesa(Long id) {
        return mesaRepository.findById(id).orElse(null);
    }

    // Elimina una mesa de la base de datos mediante su identificador.
    public void eliminarMesa(Long id) {
        mesaRepository.deleteById(id);
    }

}
