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
        listaMascotas.add(mascota);
        return id;
    }

    private void validarRaza(Mascota mascota){
        if (mascota.getRaza() == null || mascota.getRaza().trim().isEmpty()){
            mascota.setRaza("No especificada");
        }
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

    public List<Mascota> filtrarPorEspecie(String especie){
        return listaMascotas.stream()
                .filter(m -> m.getEspecie().equalsIgnoreCase(especie))
                .toList();
    }

    public Mascota filtrarPorNombre(String nombre){
        return listaMascotas.stream()
                .filter(mascota -> mascota.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
    }
}
