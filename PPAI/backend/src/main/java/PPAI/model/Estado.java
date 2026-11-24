package PPAI.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Estados")
public class Estado {
    @Id
    @Column(nullable = false, unique = true, length = 100)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    //Constructor para JPA
    protected Estado(){}

    public Estado(String descripcion, String nombre){
        this.descripcion = descripcion;
        this.nombre = nombre;
    }

    public boolean sosEnviado(){
        return "Enviado".equals(this.nombre);
    }
}
