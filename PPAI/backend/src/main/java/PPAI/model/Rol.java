package PPAI.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Roles")
public class Rol {
    @Id
    @Column(nullable = false, unique = true, length = 100)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    //Construtor para JPA
    protected Rol(){}

    public Rol(String descripcion, String nombre){
        this.descripcion = descripcion;
        this.nombre = nombre;
    }

    // Verificar si es gerente de comisión médica
    public boolean esGCM(){
        return "Gerente de Comision Medica".equals(this.nombre);
    }


}
