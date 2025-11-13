package daw.jgp.adoptaunamigo.service;

import daw.jgp.adoptaunamigo.model.Mascota;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Servicio para gestionar las mascotas.
 * Permite guardar, buscar, eliminar, filtrar y editar mascotas.
 */

@Getter
@Setter
@Service
public class MascotaService {
    private final AtomicLong secuenciaID = new AtomicLong(1);
    private final List<Mascota> listaMascotas = new CopyOnWriteArrayList<>();

    /**
     * Guarda una nueva mascota en la lista.
     * Asigna un ID único a la mascota.
     * Valida los campos raza y descripción.
     *
     * @param mascota La mascota a guardar.
     * @return El ID asignado a la mascota.
     */
    public Long guardar(Mascota mascota){
        Long id = secuenciaID.getAndIncrement();
        mascota.setId(id);
        validarRaza(mascota);
        listaMascotas.add(mascota);
        return id;
    }

    /**
     * Busca una mascota por su ID.
     *
     * @param id El ID de la mascota a buscar.
     * @return La mascota encontrada o null si no existe.
     */
    public Mascota buscarPorID(Long id){
        return listaMascotas.stream()
                .filter(mascota -> Objects.equals(mascota.getId(), id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Elimina una mascota de la lista.
     *
     * @param mascota La mascota a eliminar.
     */
    public void eliminar(Mascota mascota){
        listaMascotas.remove(mascota);
    }

    /**
     * Filtra las mascotas por nombre y especie.
     *
     * @param nombre  El nombre de la mascota a filtrar (puede ser null o vacío).
     * @param especie La especie de la mascota a filtrar (puede ser null o vacío).
     * @return La lista de mascotas que coinciden con los criterios de filtrado.
     */
    public List<Mascota> filtrar(String nombre, String especie){
        if ((nombre==null || nombre.isBlank()) && (especie==null || especie.isBlank())){
            return listaMascotas;
        }
        if (nombre==null || nombre.isBlank()){
            return listaMascotas.stream()
                    .filter(mascota -> mascota.getEspecie().equalsIgnoreCase(especie))
                    .toList();
        }else if (especie==null || especie.isBlank()){
            return listaMascotas.stream()
                    .filter(mascota -> mascota.getNombre().equalsIgnoreCase(nombre))
                    .toList();
        }else{
            return listaMascotas.stream()
                    .filter(mascota -> mascota.getNombre().equalsIgnoreCase(nombre))
                    .filter(mascota -> mascota.getEspecie().equalsIgnoreCase(especie))
                    .toList();
        }
    }

    /**
     * Edita una mascota existente con los datos de otra mascota.
     * Valida los campos raza y descripción.
     *
     * @param mascotaEditada La mascota con los nuevos datos.
     * @param mascotaAEditar La mascota a editar.
     */
    public void editarMascota (Mascota mascotaEditada, Mascota mascotaAEditar){
        validarRaza(mascotaEditada);
        mascotaAEditar.setNombre(mascotaEditada.getNombre());
        mascotaAEditar.setEspecie(mascotaEditada.getEspecie());
        mascotaAEditar.setRaza(mascotaEditada.getRaza());
        mascotaAEditar.setSexo(mascotaEditada.getSexo());
        mascotaAEditar.setEdad(mascotaEditada.getEdad());
        mascotaAEditar.setDescripcion(mascotaEditada.getDescripcion());
    }

    /*VALIDACIONES*/
    /**
     * Valida el campo raza de la mascota.
     * Si está vacío o es nulo, lo establece como "No especificada".
     *
     * @param mascota La mascota a validar.
     */
    private void validarRaza(Mascota mascota){
        if (mascota.getRaza() == null || mascota.getRaza().trim().isEmpty()){
            mascota.setRaza("No especificada");
        }
    }

    /**
     * Valida que si la especie es "Otros", la descripción no esté vacía ni sea "Sin descripción".
     *
     * @param mascota La mascota a validar.
     * @return true si la validación es correcta, false en caso contrario.
     */
    public boolean validarEspecieOtros(Mascota mascota){
        if (mascota.getEspecie().equalsIgnoreCase("Otros")){
            if (mascota.getDescripcion() == null || mascota.getDescripcion().trim().isEmpty() || mascota.getDescripcion().equals("Sin descripción")){
                return false;
            }
        }
        return true;
    }
}
