package PPAI.dominio;

public class Estado {
    private String descripcion;
    private String nombre;

    public Estado(String descripcion, String nombre){
        this.descripcion = descripcion;
        this.nombre = nombre;
    }

    public boolean sosEnviado(){
        return "Enviado".equals(this.nombre);
    }
}
