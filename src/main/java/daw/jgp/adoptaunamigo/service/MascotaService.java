package daw.jgp.adoptaunamigo.service;

import daw.jgp.adoptaunamigo.model.Mascota;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class MascotaService {
    private final AtomicLong secuenciaID = new AtomicLong(1);
    private final List<Mascota> listaMascotas = new CopyOnWriteArrayList<>();

    public Long guardar(Mascota mascota){
        Long id = secuenciaID.getAndIncrement();
        mascota.setId(id);
        listaMascotas.add(mascota);
        return id;
    }

    public Mascota buscarPorID(Long id){
        return listaMascotas.stream()
                .filter(mascota -> Objects.equals(mascota.getId(), id))
                .findFirst()
                .orElse(null);
    }
}
