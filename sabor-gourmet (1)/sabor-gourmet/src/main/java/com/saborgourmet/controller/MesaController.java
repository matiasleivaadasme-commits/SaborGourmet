
package com.saborgourmet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.saborgourmet.entity.Mesa;
import com.saborgourmet.service.MesaService;

// Define esta clase como controlador web de Spring MVC.
@Controller

// Establece la ruta principal para administrar las mesas.
@RequestMapping("/mesas")
public class MesaController {

    // Servicio encargado de realizar las operaciones sobre las mesas.
    private final MesaService mesaService;

    // Constructor que recibe el servicio mediante inyección de dependencias.
    public MesaController(MesaService mesaService) {
        this.mesaService = mesaService;
    }

    // Muestra todas las mesas registradas en el sistema.
    @GetMapping
    public String listar(Model model) {

        // Envía la lista de mesas a la vista mesas.html.
        model.addAttribute("mesas", mesaService.listarMesas());

        return "mesas";
    }

    // Muestra el formulario para registrar una nueva mesa.
    @GetMapping("/nueva")
    public String nueva(Model model) {

        // Crea un objeto Mesa vacío para completar el formulario.
        model.addAttribute("mesa", new Mesa());

        return "formulario-mesa";
    }

    // Recibe los datos del formulario y guarda una nueva mesa.
    @PostMapping("/guardar")
    public String guardar(Mesa mesa) {

        // Almacena la información de la mesa en la base de datos.
        mesaService.guardarMesa(mesa);

        // Regresa al listado de mesas.
        return "redirect:/mesas";
    }

    // Busca una mesa por su identificador y muestra el formulario de edición.
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model,
                         RedirectAttributes redirect) {

        // Recupera la mesa seleccionada.
        Mesa mesa = mesaService.buscarMesa(id);

        // Comprueba que la mesa exista antes de permitir su edición.
        if (mesa == null) {
            redirect.addFlashAttribute("error", "Mesa no encontrada.");
            return "redirect:/mesas";
        }

        // Envía los datos actuales de la mesa al formulario.
        model.addAttribute("mesa", mesa);

        return "formulario-mesa";
    }

    // Recibe y procesa los cambios realizados en una mesa.
    @PostMapping("/actualizar")
    public String actualizar(Mesa datosMesa,
                             RedirectAttributes redirect) {

        // Busca el registro original utilizando su identificador.
        Mesa mesa = mesaService.buscarMesa(datosMesa.getId());

        // Verifica que la mesa exista antes de actualizarla.
        if (mesa == null) {
            redirect.addFlashAttribute("error", "Mesa no encontrada.");
            return "redirect:/mesas";
        }

        // Actualiza el número, la capacidad y la disponibilidad de la mesa.
        mesa.setNumero(datosMesa.getNumero());
        mesa.setCapacidad(datosMesa.getCapacidad());
        mesa.setDisponible(datosMesa.isDisponible());

        // Guarda los cambios en la base de datos.
        mesaService.guardarMesa(mesa);

        // Informa al usuario que la actualización fue exitosa.
        redirect.addFlashAttribute("mensaje",
                "Mesa actualizada correctamente.");

        return "redirect:/mesas";
    }

}
