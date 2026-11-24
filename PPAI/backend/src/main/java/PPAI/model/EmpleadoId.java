package PPAI.model;

import java.io.Serializable;
import java.util.Objects;

public class EmpleadoId implements Serializable {
    private String nombre;
    private String apellido;

    public EmpleadoId() {
    }

    public EmpleadoId(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmpleadoId that)) return false;
        return Objects.equals(nombre, that.nombre) && Objects.equals(apellido, that.apellido);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, apellido);
    }
}
