package PPAI;

public class Empleado {
    private String nombre;
    private String apellido;
    private String email;
    private Rol rol;
    private ComisionMedica comisionMedica;
    private Usuario usuario;

    public Empleado(String nombre, String apellido, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
    }

    public boolean sosGCM(){
        return this.rol.esGCM();
    }
    public int obtenerCodigoCM(){
        return this.comisionMedica.getCodigo();
    }
    public boolean esTuUsuario(Usuario usuario){
        return this.usuario.equals(usuario);
    }
}
