package daw.jgp.adoptaunamigo.controller;

import daw.jgp.adoptaunamigo.model.Mascota;
import daw.jgp.adoptaunamigo.service.MascotaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MascotaViewController {

    private final MascotaService mascotaService;

    public MascotaViewController(MascotaService mascotaService){
        this.mascotaService = mascotaService;
    }

    //No se que hago mal pero peta al intentar mostrar la vista detalle.html
    @GetMapping("/mascotas/{id}")
    public String mostrarDetalles (@PathVariable Long id, Model model){
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


}
