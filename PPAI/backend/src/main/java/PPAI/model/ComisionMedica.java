package PPAI.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ComisionesMedicas")
public class ComisionMedica {
    @Id
    @Column(nullable = false, unique = true, length = 50)
    private String codigo;

    private String direccion;
    private String email;
    private String telefono;
    private String nombre;

    //Constructor para JPA
    protected ComisionMedica(){}

    public ComisionMedica(String codigo, String direccion, String email, String telefono, String nombre){
        this.codigo = codigo;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
        this.nombre = nombre;
    }
    public String getCodigo(){
        return this.codigo;
    }

    public boolean esTuCodigo(String codigo){
        return this.codigo.equals(codigo);
    }
}
