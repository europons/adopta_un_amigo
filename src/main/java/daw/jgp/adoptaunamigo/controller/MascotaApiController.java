package daw.jgp.adoptaunamigo.controller;

import daw.jgp.adoptaunamigo.model.Mascota;
import daw.jgp.adoptaunamigo.service.MascotaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * API REST encargada de gestionar las operaciones CRUD
 * relacionadas con las mascotas.
 */
@RestController
@RequestMapping("/api/mascotas")
public class MascotaApiController {

    private final MascotaService mascotaService;

    public MascotaApiController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    /** Crear una nueva mascota
     *
     * @param mascota Objeto Mascota a crear
     * @return Mascota creada
     */
    @PostMapping
    public Mascota crearMascota(@Valid @RequestBody Mascota mascota){

        mascotaService.guardar(mascota);
        return mascota;
    }

    /** Listar todas las mascotas
     *
     * @return Lista de mascotas
     */
    @GetMapping
    public List<Mascota> listarMascotas(){
        return mascotaService.getListaMascotas();
    }

    /** Obtener una mascota por su ID
     *
     * @param id ID de la mascota a obtener
     * @return Mascota encontrada
     */
    @GetMapping("/{id}")
    public Mascota obtenerMascota(@PathVariable Long id) {
        return mascotaService.buscarPorID(id);
    }

    /** Eliminar una mascota por su ID
     *
     * @param id ID de la mascota a eliminar
     */
    @DeleteMapping ("/{id}")
    public void eliminarMascota(@PathVariable Long id) {
        Mascota mascota = mascotaService.buscarPorID(id);
        if (mascota != null) {
            mascotaService.eliminar(mascota);
        }
    }

    /** Actualizar una mascota existente
     *
     * @param id ID de la mascota a actualizar
     * @param mascotaActualizada Objeto Mascota con los datos actualizados
     * @return Mascota actualizada
     */
    @PutMapping("/{id}")
    public Mascota actualizarMascota(@PathVariable Long id, @Valid @RequestBody Mascota mascotaActualizada) {

        Mascota mascotaAEditar = mascotaService.buscarPorID(id);

        mascotaService.editarMascota(mascotaActualizada, mascotaAEditar);

        return mascotaActualizada;
    }

    /** Filtrar la lista de mascotas por nombre y especie
     *
     * @param nombre Nombre para filtrar (opcional)
     * @param especie Especie para filtrar (opcional)
     * @return Lista de mascotas filtradas
     */
    @GetMapping("/filtrar")
    public List<Mascota> filtrar(@RequestParam(required = false) String nombre,
                                 @RequestParam(required = false) String especie) {

        return mascotaService.filtrar(nombre, especie);
    }
}
