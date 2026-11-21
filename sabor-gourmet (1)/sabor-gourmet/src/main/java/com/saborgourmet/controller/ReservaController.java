
package com.saborgourmet.controller;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.saborgourmet.entity.Cliente;
import com.saborgourmet.entity.Mesa;
import com.saborgourmet.entity.Reserva;

import com.saborgourmet.service.ClienteService;
import com.saborgourmet.service.MesaService;
import com.saborgourmet.service.ReservaService;

// Indica que esta clase funciona como controlador web de Spring MVC.
@Controller

// Define la ruta principal para todas las operaciones relacionadas con reservas.
@RequestMapping("/reservas")
public class ReservaController {

    // Servicios utilizados para administrar reservas, clientes y mesas.
    private final ReservaService reservaService;
    private final ClienteService clienteService;
    private final MesaService mesaService;

    // Constructor que recibe los servicios mediante inyección de dependencias.
    public ReservaController(
            ReservaService reservaService,
            ClienteService clienteService,
            MesaService mesaService) {

        this.reservaService = reservaService;
        this.clienteService = clienteService;
        this.mesaService = mesaService;
    }

    // Muestra el listado de todas las reservas registradas.
    @GetMapping
    public String listar(Model model) {

        model.addAttribute("reservas", reservaService.listarReservas());

        return "reservas";
    }

    // Muestra el formulario para registrar una nueva reserva.
    @GetMapping("/nueva")
    public String nueva(Model model) {

        model.addAttribute("reserva", new Reserva());
        model.addAttribute("clientes", clienteService.listarClientes());
        model.addAttribute("mesas", mesaService.listarMesas());

        return "formulario-reserva";
    }

    // Recibe los datos del formulario y registra una nueva reserva.
    @PostMapping("/guardar")
    public String guardar(
            @RequestParam Long clienteId,
            @RequestParam Long mesaId,
            @RequestParam LocalDate fecha,
            @RequestParam LocalTime hora,
            @RequestParam Integer cantidadPersonas,
            RedirectAttributes redirect) {

        // Busca el cliente y la mesa seleccionados.
        Cliente cliente = clienteService.buscarCliente(clienteId);
        Mesa mesa = mesaService.buscarMesa(mesaId);

        // Comprueba que ambos registros existan.
        if (cliente == null || mesa == null) {
            redirect.addFlashAttribute("error", "Cliente o mesa no encontrados.");
            return "redirect:/reservas/nueva";
        }

        // Verifica que la cantidad de personas no supere la capacidad de la mesa.
        if (cantidadPersonas < 1 || cantidadPersonas > mesa.getCapacidad()) {
            redirect.addFlashAttribute("error",
                    "La cantidad de personas supera la capacidad de la mesa.");
            return "redirect:/reservas/nueva";
        }

        // Comprueba si existe otra reserva activa para la misma mesa, fecha y hora.
        boolean ocupada = reservaService.listarReservas().stream()
                .anyMatch(r -> r.getMesa().getId().equals(mesaId)
                        && r.getFecha().equals(fecha)
                        && r.getHora().equals(hora)
                        && !"CANCELADA".equals(r.getEstado()));

        // Si la mesa está ocupada, impide registrar la reserva.
        if (ocupada) {
            redirect.addFlashAttribute("error",
                    "Esta mesa ya está reservada para esa fecha y hora.");
            return "redirect:/reservas/nueva";
        }

        // Crea una nueva reserva y asigna los datos recibidos.
        Reserva reserva = new Reserva();

        reserva.setCliente(cliente);
        reserva.setMesa(mesa);
        reserva.setFecha(fecha);
        reserva.setHora(hora);
        reserva.setCantidadPersonas(cantidadPersonas);
        reserva.setEstado("CONFIRMADA");

        // Guarda la reserva en la base de datos.
        reservaService.guardarReserva(reserva);

        redirect.addFlashAttribute("mensaje", "Reserva creada correctamente.");

        return "redirect:/reservas";
    }

    // Busca una reserva y muestra sus datos en el formulario de edición.
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model,
                         RedirectAttributes redirect) {

        Reserva reserva = reservaService.buscarReserva(id);

        // Impide editar reservas inexistentes o que ya fueron canceladas.
        if (reserva == null || "CANCELADA".equals(reserva.getEstado())) {
            redirect.addFlashAttribute("error", "No se puede editar esta reserva.");
            return "redirect:/reservas";
        }

        // Envía los datos necesarios a la vista.
        model.addAttribute("reserva", reserva);
        model.addAttribute("clientes", clienteService.listarClientes());
        model.addAttribute("mesas", mesaService.listarMesas());

        return "formulario-reserva";
    }

    // Recibe y guarda las modificaciones realizadas en una reserva.
    @PostMapping("/actualizar")
    public String actualizar(
            @RequestParam Long id,
            @RequestParam Long clienteId,
            @RequestParam Long mesaId,
            @RequestParam LocalDate fecha,
            @RequestParam LocalTime hora,
            @RequestParam Integer cantidadPersonas,
            RedirectAttributes redirect) {

        // Recupera los registros seleccionados.
        Reserva reserva = reservaService.buscarReserva(id);
        Cliente cliente = clienteService.buscarCliente(clienteId);
        Mesa mesa = mesaService.buscarMesa(mesaId);

        // Comprueba que los registros existan y que la reserva siga activa.
        if (reserva == null || cliente == null || mesa == null
                || "CANCELADA".equals(reserva.getEstado())) {

            redirect.addFlashAttribute("error", "No se pudo actualizar la reserva.");
            return "redirect:/reservas";
        }

        // Valida que la cantidad de personas respete la capacidad de la mesa.
        if (cantidadPersonas < 1 || cantidadPersonas > mesa.getCapacidad()) {
            redirect.addFlashAttribute("error",
                    "La cantidad de personas supera la capacidad de la mesa.");
            return "redirect:/reservas/editar/" + id;
        }

        // Verifica que otra reserva no esté utilizando la misma mesa,
        // fecha y hora. Se excluye la reserva que se está modificando.
        boolean ocupada = reservaService.listarReservas().stream()
                .anyMatch(r -> !r.getId().equals(id)
                        && r.getMesa().getId().equals(mesaId)
                        && r.getFecha().equals(fecha)
                        && r.getHora().equals(hora)
                        && !"CANCELADA".equals(r.getEstado()));

        // Si existe un conflicto, no permite guardar los cambios.
        if (ocupada) {
            redirect.addFlashAttribute("error",
                    "Esta mesa ya está reservada para esa fecha y hora.");
            return "redirect:/reservas/editar/" + id;
        }

        // Actualiza los datos de la reserva seleccionada.
        reserva.setCliente(cliente);
        reserva.setMesa(mesa);
        reserva.setFecha(fecha);
        reserva.setHora(hora);
        reserva.setCantidadPersonas(cantidadPersonas);

        // Guarda los cambios en la base de datos.
        reservaService.guardarReserva(reserva);

        redirect.addFlashAttribute("mensaje", "Reserva actualizada correctamente.");

        return "redirect:/reservas";
    }

    // Cancela una reserva cambiando su estado, sin eliminarla de la base de datos.
    @PostMapping("/cancelar")
    public String cancelar(
            @RequestParam Long id,
            RedirectAttributes redirect) {

        Reserva reserva = reservaService.buscarReserva(id);

        // Si la reserva existe, cambia su estado a CANCELADA.
        if (reserva != null) {
            reserva.setEstado("CANCELADA");

            // Guarda el nuevo estado de la reserva.
            reservaService.guardarReserva(reserva);

            redirect.addFlashAttribute("mensaje", "Reserva cancelada correctamente.");
        }

        return "redirect:/reservas";
    }
}
