package PPAI.dominio;

public class ComisionMedica {
    private int codigo;
    private String direccion;
    private String email;
    private String telefono;
    private String nombre;

    public ComisionMedica(int codigo, String direccion, String email, String telefono, String nombre){
        this.codigo = codigo;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
        this.nombre = nombre;
    }
    public int getCodigo() {
        return codigo;
    }
}
