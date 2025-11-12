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

/**
 * Controlador encargado de gestionar las vistas relacionadas con las mascotas.
 * Proporciona métodos para mostrar detalles, listas, formularios de edición y
 * manejar acciones como eliminar y filtrar mascotas.
 */
@Controller
public class MascotaViewController {

    private final MascotaService mascotaService;

    public MascotaViewController(MascotaService mascotaService){
        this.mascotaService = mascotaService;
    }

    /** Mostrar los detalles de una mascota específica
     *
     * @param id ID de la mascota a mostrar
     * @param model Modelo para pasar datos a la vista
     * @param redirectAttributes Atributos para redirección con mensajes
     * @return Nombre de la vista a renderizar o redirección en caso de error
     */
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

    /** Mostrar la lista completa de mascotas
     *
     * @param model Modelo para pasar datos a la vista
     * @return Nombre de la vista a renderizar
     */
    @GetMapping("/mascotas")
    public String mostrarLista(Model model){
        model.addAttribute("mascotas", mascotaService.getListaMascotas());

        return "lista";
    }

    /** Mostrar la página de inicio
     *
     * @return Nombre de la vista de inicio
     */
    @GetMapping("/")
    public String mostrarInicio(){
        return "index";
    }

    /** Eliminar una mascota por su ID
     *
     * @param id ID de la mascota a eliminar
     * @param redirectAttributes Atributos para redirección con mensajes
     * @return Redirección a la lista de mascotas
     */
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

    /** Filtrar la lista de mascotas por nombre y especie
     *
     * @param nombre Nombre para filtrar (opcional)
     * @param especie Especie para filtrar (opcional)
     * @param model Modelo para pasar datos a la vista
     * @return Nombre de la vista con la lista filtrada
     */
    @GetMapping("/mascotas/filtrar")
    public String filtrar(@RequestParam(required = false) String nombre,
                          @RequestParam(required = false) String especie,
                          Model model) {

        List<Mascota> mascotas = mascotaService.filtrar(nombre, especie);
        model.addAttribute("mascotas", mascotas);
        return "lista";
    }

    /** Mostrar el formulario de edición para una mascota específica
     *
     * @param id ID de la mascota a editar
     * @param model Modelo para pasar datos a la vista
     * @return Nombre de la vista del formulario de edición
     */
    @GetMapping("/mascotas/editar/{id}")
    public String mostrarFormularioEdicionMascota (@PathVariable Long id, Model model){
        model.addAttribute("mascota", mascotaService.buscarPorID(id));
        return "editar";
    }

    /** Guardar los cambios realizados en la edición de una mascota
     *
     * @param mascotaActualizada Objeto Mascota con los datos editados
     * @param bindingResult Resultado de la validación de los datos
     * @param redirectAttributes Atributos para redirección con mensajes
     * @return Redirección a los detalles de la mascota o vuelta al formulario en caso de error
     */
    @PostMapping("/mascotas/editar")
    public String guadarCambiosEdicionMascota (@Valid @ModelAttribute("mascota") Mascota mascotaActualizada,
                                               BindingResult bindingResult,
                                               RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()){
            return "editar";
        }

        // Validar que el id no sea nulo
        if (mascotaActualizada.getId() == null) {
            redirectAttributes.addFlashAttribute("error", "No se ha recibido un ID válido para editar la mascota.");
            return "redirect:/mascotas";
        }

        Mascota mascotaAEditar = mascotaService.buscarPorID(mascotaActualizada.getId());
        // Validar que la mascota a editar exista
        if (mascotaAEditar == null) {
            redirectAttributes.addFlashAttribute("error", "No se encontró la mascota que intentas editar.");
            return "redirect:/mascotas";
        }

        mascotaService.editarMascota(mascotaActualizada, mascotaAEditar);

        redirectAttributes.addAttribute("id", mascotaActualizada.getId());
        return "redirect:/mascotas/{id}";
    }
}
