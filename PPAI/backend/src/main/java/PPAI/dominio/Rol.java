package PPAI.dominio;

public class Rol {
    private String descripcion;
    private String nombre;

    public Rol(String descripcion, String nombre){
        this.descripcion = descripcion;
        this.nombre = nombre;
    }

    // Verificar si es gerente de comisión médica
    public boolean esGCM(){
        return "Gerente de Comision Medica".equals(this.nombre);
    }


}
