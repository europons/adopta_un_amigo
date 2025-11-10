package daw.jgp.adoptaunamigo.controller;

import daw.jgp.adoptaunamigo.model.Mascota;
import daw.jgp.adoptaunamigo.service.MascotaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MascotaDetalleController {

    private final MascotaService mascotaService;

    public MascotaDetalleController(MascotaService mascotaService){
        this.mascotaService = mascotaService;
    }

    @GetMapping("/detalle/{id}")
    public String mostrarDetalles (@PathVariable Long id, Model model){
        Mascota mascota = mascotaService.buscarPorID(id);

        model.addAttribute("mascota", mascota);

        return "detalle";
    }

}
