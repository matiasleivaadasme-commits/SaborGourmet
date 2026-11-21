
package com.saborgourmet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// Indica que esta clase funciona como controlador web de Spring MVC.
@Controller
public class HomeController {

    // Atiende las solicitudes realizadas a la dirección principal del sistema.
    @GetMapping("/")
    public String inicio() {

        // Muestra la página principal index.html.
        return "index";
    }

}
