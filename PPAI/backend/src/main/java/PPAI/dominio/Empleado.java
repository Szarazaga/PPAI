package PPAI.dominio;

public class Empleado {
    private String nombre;
    private String apellido;
    private String email;
    private Rol rol;
    private ComisionMedica comisionMedica;
    private Usuario usuario;

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
    public int obtenerCodigoCM(){
        return this.comisionMedica.getCodigo();
    }
    public boolean esTuUsuario(Usuario usuario){
        return this.usuario.equals(usuario);
    }
}
