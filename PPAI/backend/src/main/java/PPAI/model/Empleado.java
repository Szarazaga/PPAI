package PPAI.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Empleados")
@IdClass(EmpleadoId.class)
public class Empleado {

    @Id
    @Column(nullable = false, length = 100)
    private String nombre;

    @Id
    @Column(nullable = false, length = 100)
    private String apellido;

    @Column(nullable = false)
    private String email;

    @ManyToOne
    @JoinColumn(name = "rol_nombre")
    private Rol rol;

    @ManyToOne
    @JoinColumn(name = "comision_medica_codigo")
    private ComisionMedica comisionMedica;

    @OneToOne
    @JoinColumn(name = "usuario_nombre")
    private Usuario usuario;

    //Construtor para JPA
    protected Empleado(){}

    public Empleado(String nombre, String apellido, String email, Rol rol, ComisionMedica comisionMedica, Usuario usuario) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.rol = rol;
        this.comisionMedica = comisionMedica;
        this.usuario = usuario;
    }

    public boolean sosGCM(){
        return this.rol.esGCM();
    }
    public String obtenerCodigoCM(){
        return this.comisionMedica.getCodigo();
    }
    public boolean esTuUsuario(Usuario usuario){
        return this.usuario.equals(usuario);
    }
}
