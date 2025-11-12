package daw.jgp.adoptaunamigo.controller;

import daw.jgp.adoptaunamigo.model.Mascota;
import daw.jgp.adoptaunamigo.service.MascotaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MascotaViewController {

    private final MascotaService mascotaService;

    public MascotaViewController(MascotaService mascotaService){
        this.mascotaService = mascotaService;
    }

    //NO FUNCIONA
    @GetMapping("/mascotas/{id}")
    public String mostrarDetalles (Long id, Model model){
        Mascota mascota = mascotaService.buscarPorID(id);

        model.addAttribute("mascota", mascota);

        return "detalle";
    }


    @GetMapping("/mascotas")
    public String mostrarLista (Model model){
        model.addAttribute("mascotas", mascotaService.getListaMascotas());

        return "lista";
    }

    @GetMapping("/")
    public String mostrarLista (){
        return "index";
    }

    //ESTO TAMPOCO FUNCIONA
    @PostMapping("/mascotas")
    public String eliminarMascota(Long id, Model model){
        Mascota mascota = mascotaService.buscarPorID(id);
        mascotaService.eliminar(mascota);

        model.addAttribute("mascota", mascota);

        return "redirect:/mascotas";
    }


}
