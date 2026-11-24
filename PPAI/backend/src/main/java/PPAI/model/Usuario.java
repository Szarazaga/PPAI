package PPAI.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Usuarios")
public class Usuario {
    @Id
    @Column(nullable = false, unique = true, length = 100)
    private String nombre;

    @Column(nullable = false)
    private String contrasena;

    //Construtor para JPA
    protected Usuario(){}

    public Usuario(String nombre, String contrasena){
        this.nombre = nombre;
        this.contrasena = contrasena;
    }
    
}
