package daw.jgp.adoptaunamigo.controller;


import daw.jgp.adoptaunamigo.model.Mascota;
import daw.jgp.adoptaunamigo.service.MascotaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MascotaFormController {

    private final MascotaService mascotaService;

    public MascotaFormController(MascotaService mascotaService){
        this.mascotaService = mascotaService;
    }

    @GetMapping("/formulario")
    public String mostrarFormulario (Model model){
        model.addAttribute("mascota", new Mascota());
        return "formulario";
    }

    public String procesarFormulario(@Valid @ModelAttribute("mascota") Mascota mascota,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()){
            return "formulario";
        }

        Long id = mascotaService.guardar(mascota);

        redirectAttributes.addAttribute("id", id);
        return "redirect:/detalle/{id}";
    }

}
