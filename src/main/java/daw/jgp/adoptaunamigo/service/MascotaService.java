package daw.jgp.adoptaunamigo.service;

import daw.jgp.adoptaunamigo.model.Mascota;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Getter
@Setter
@Service
public class MascotaService {
    private final AtomicLong secuenciaID = new AtomicLong(1);
    private final List<Mascota> listaMascotas = new CopyOnWriteArrayList<>();


    public Long guardar(Mascota mascota){
        Long id = secuenciaID.getAndIncrement();
        mascota.setId(id);
        validarRaza(mascota);
        validarDescripcion(mascota);
        listaMascotas.add(mascota);
        return id;
    }

    public Mascota buscarPorID(Long id){
        return listaMascotas.stream()
                .filter(mascota -> Objects.equals(mascota.getId(), id))
                .findFirst()
                .orElse(null);
    }

    public void eliminar(Mascota mascota){
        listaMascotas.remove(mascota);
    }

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

    public void editarMascota (Mascota mascotaEditada, Mascota mascotaAEditar){
        validarRaza(mascotaEditada);
        validarDescripcion(mascotaEditada);
        mascotaAEditar.setNombre(mascotaEditada.getNombre());
        mascotaAEditar.setEspecie(mascotaEditada.getEspecie());
        mascotaAEditar.setRaza(mascotaEditada.getRaza());
        mascotaAEditar.setSexo(mascotaEditada.getSexo());
        mascotaAEditar.setEdad(mascotaEditada.getEdad());
        mascotaAEditar.setDescripcion(mascotaEditada.getDescripcion());
    }

    /*VALIDACIONES*/
    private void validarRaza(Mascota mascota){
        if (mascota.getRaza() == null || mascota.getRaza().trim().isEmpty()){
            mascota.setRaza("No especificada");
        }
    }

    private void validarDescripcion(Mascota mascota){
        if (mascota.getDescripcion() == null || mascota.getDescripcion().trim().isEmpty()){
            mascota.setDescripcion("Sin descripción");
        }
    }

    public boolean validarEspecieOtros(Mascota mascota){
        if (mascota.getEspecie().equalsIgnoreCase("Otros")){
            if (mascota.getDescripcion() == null || mascota.getDescripcion().trim().isEmpty() || mascota.getDescripcion().equals("Sin descripción")){
                return false;
            }
        }
        return true;
    }
}
