package daw.jgp.adoptaunamigo.controller;

import daw.jgp.adoptaunamigo.model.Mascota;
import daw.jgp.adoptaunamigo.service.MascotaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador para manejar el formulario de creación de nuevas mascotas.
 */
@Controller
public class MascotaFormController {

    private final MascotaService mascotaService;

    public MascotaFormController(MascotaService mascotaService){
        this.mascotaService = mascotaService;
    }

    /**
     * Muestra el formulario para crear una nueva mascota.
     *
     * @param model el modelo para pasar datos a la vista
     * @return el nombre de la vista del formulario
     */
    @GetMapping("/mascotas/nueva")
    public String mostrarFormulario (Model model){
        model.addAttribute("mascota", new Mascota());
        return "formulario";
    }

    /**
     * Procesa el formulario de creación de una nueva mascota.
     *
     * @param mascota el objeto Mascota vinculado al formulario
     * @param bindingResult el resultado de la validación del formulario
     * @param redirectAttributes atributos para redirección
     * @return la vista a mostrar después de procesar el formulario
     */
    @PostMapping("/mascotas/nueva")
    public String procesarFormulario(@Valid @ModelAttribute("mascota") Mascota mascota,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()){
            return "formulario";
        }

        // Validar que si la especie es "Otros", se pide descripción obligatoriamente y se muestra un mensaje debajo del campo Descripción
        if (!mascotaService.validarEspecieOtros(mascota)){
            bindingResult.rejectValue("descripcion", "error especie otros", "Si la especie es 'Otros', debe escribirse una descripción.");
            return "formulario";
        }

        Long id = mascotaService.guardar(mascota);

        redirectAttributes.addAttribute("id", id);
        return "redirect:/mascotas/{id}";
    }

}
