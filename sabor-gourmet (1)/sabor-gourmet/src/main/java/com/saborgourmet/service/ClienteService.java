
package com.saborgourmet.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.saborgourmet.entity.Cliente;
import com.saborgourmet.repository.ClienteRepository;

// Indica que esta clase contiene la lógica de servicio de los clientes.
@Service
public class ClienteService {

    // Repositorio encargado de comunicarse con la base de datos.
    private final ClienteRepository clienteRepository;

    // Constructor que recibe el repositorio mediante inyección de dependencias.
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // Obtiene y devuelve todos los clientes registrados.
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    // Guarda un cliente nuevo o actualiza uno existente.
    public Cliente guardarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    // Busca un cliente utilizando su identificador.
    // Si no existe, devuelve null.
    public Cliente buscarCliente(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }

    // Elimina un cliente de la base de datos mediante su identificador.
    public void eliminarCliente(Long id) {
        clienteRepository.deleteById(id);
    }

}
