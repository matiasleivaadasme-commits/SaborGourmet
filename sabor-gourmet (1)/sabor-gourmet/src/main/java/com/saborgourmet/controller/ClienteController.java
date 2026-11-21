
package com.saborgourmet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.saborgourmet.entity.Cliente;
import com.saborgourmet.service.ClienteService;

// Indica que esta clase es un controlador web de Spring MVC.
@Controller

// Define la ruta principal para las operaciones de clientes.
@RequestMapping("/clientes")
public class ClienteController {

    // Servicio encargado de administrar los datos de los clientes.
    private final ClienteService clienteService;

    // Constructor que recibe el servicio mediante inyección de dependencias.
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // Muestra el listado de clientes registrados en el sistema.
    @GetMapping
    public String listar(Model model) {

        // Envía la lista de clientes a la vista clientes.html.
        model.addAttribute("clientes", clienteService.listarClientes());

        return "clientes";
    }

    // Muestra el formulario para registrar un nuevo cliente.
    @GetMapping("/nuevo")
    public String nuevo(Model model) {

        // Crea un objeto Cliente vacío para completar el formulario.
        model.addAttribute("cliente", new Cliente());

        return "formulario-cliente";
    }

    // Recibe los datos del formulario y registra un cliente.
    @PostMapping("/guardar")
    public String guardar(Cliente cliente) {

        // Guarda la información del cliente en la base de datos.
        clienteService.guardarCliente(cliente);

        // Redirige al listado de clientes después de guardar.
        return "redirect:/clientes";
    }

}
