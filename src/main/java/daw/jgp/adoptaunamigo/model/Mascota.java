package daw.jgp.adoptaunamigo.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Mascota {
    private Long id;

    @NotBlank (message = "El nombre es obligatorio")
    private String nombre;

    @NotNull (message = "La edad es obligatoria")
    @Min(value = 0, message = "No puede ser menor que 0")
    private Integer edad;

    @NotBlank(message = "Debe seleccionar una especie")
    private String especie;

    //Sin validación. Se puede dejar vacío sin raza
    private String raza;

    @NotBlank(message = "Debe seleccionar el sexo de la mascota")
    private String sexo;

    @Size(max = 300, message = "La descripción no puede superar los 300 caracteres")
    private String descripcion;
}
