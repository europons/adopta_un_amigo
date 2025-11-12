package daw.jgp.adoptaunamigo.controller;

import daw.jgp.adoptaunamigo.model.Mascota;
import daw.jgp.adoptaunamigo.service.MascotaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class MascotaViewController {

    private final MascotaService mascotaService;

    public MascotaViewController(MascotaService mascotaService){
        this.mascotaService = mascotaService;
    }

    //Mostrar página de los detalles de cada mascota
    @GetMapping("/mascotas/{id}")
    public String mostrarDetalles (@PathVariable Long id, Model model, RedirectAttributes redirectAttributes){
        Mascota mascota = mascotaService.buscarPorID(id);

        if (mascota == null) {
            redirectAttributes.addFlashAttribute("error", "La mascota que intentas ver no existe o ha sido eliminada.");
            return "redirect:/mascotas";
        }

        model.addAttribute("mascota", mascota);

        return "detalle";
    }

    //Mostrar una lista con todas las mascotas
    @GetMapping("/mascotas")
    public String mostrarLista(Model model){
        model.addAttribute("mascotas", mascotaService.getListaMascotas());

        return "lista";
    }

    //Mostrar la página de inicio
    @GetMapping("/")
    public String mostrarInicio(){
        return "index";
    }

    //Eliminar la mascota y volver a mostrar la lista actualizada
    @GetMapping("/mascotas/eliminar/{id}")
    public String eliminarMascota(@PathVariable Long id, RedirectAttributes redirectAttributes){
        Mascota mascota = mascotaService.buscarPorID(id);

        if (mascota == null) {
            redirectAttributes.addFlashAttribute("error", "No se pudo eliminar: la mascota no existe o ya fue eliminada.");
            return "redirect:/mascotas";
        }
        mascotaService.eliminar(mascota);
        redirectAttributes.addFlashAttribute("mensajeExito", "Mascota eliminada correctamente.");

        return "redirect:/mascotas";
    }

    //Mostrar lista de mascotas filtrada por especie y nombre
    @GetMapping("/mascotas/filtrar")
    public String filtrar(@RequestParam(required = false) String nombre,
                          @RequestParam(required = false) String especie,
                          Model model) {

        List<Mascota> mascotas = mascotaService.filtrar(nombre, especie);
        model.addAttribute("mascotas", mascotas);
        return "lista";
    }

    //Mostrar la vista de edición de una mascota
    @GetMapping("/mascotas/editar/{id}")
    public String mostrarFormularioEdicionMascota (@PathVariable Long id, Model model){
        model.addAttribute("mascota", mascotaService.buscarPorID(id));
        return "editar";
    }

    //Mostrar la vista de edición de una mascota
    @PostMapping("/mascotas/editar")
    public String guadarCambiosEdicionMascota (@Valid @ModelAttribute("mascota") Mascota mascotaEditada,
                                               BindingResult bindingResult,
                                               RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()){
            return "editar";
        }

        // Validar que el id no sea nulo
        if (mascotaEditada.getId() == null) {
            redirectAttributes.addFlashAttribute("error", "No se ha recibido un ID válido para editar la mascota.");
            return "redirect:/mascotas";
        }

        Mascota mascotaAEditar = mascotaService.buscarPorID(mascotaEditada.getId());
        if (mascotaAEditar == null) {
            redirectAttributes.addFlashAttribute("error", "No se encontró la mascota que intentas editar.");
            return "redirect:/mascotas";
        }

        mascotaService.editarMascota(mascotaEditada, mascotaAEditar);

        redirectAttributes.addAttribute("id", mascotaEditada.getId());
        return "redirect:/mascotas/{id}";
    }
}
