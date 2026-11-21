package PPAI.dominio;

import java.util.Date;

public class Sesion {
    private Date fechaHoraInicio;
    private Date fechaHoraFin;
    private Usuario usuario;

    public Sesion(Date fechaHoraInicio, Date fechaHoraFin, Usuario usuario){
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        this.usuario = usuario;
    }

    public Usuario getUsuario(){
        return this.usuario;
    }

    
}
