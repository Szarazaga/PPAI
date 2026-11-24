package PPAI.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "sesiones")
public class Sesion {
    @Id
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date fechaHoraInicio;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraFin;

    @ManyToOne
    @JoinColumn(name = "usuario_nombre", nullable = false)
    private Usuario usuario;

    //Construtor para JPA
    protected Sesion(){}

    public Sesion(Date fechaHoraInicio, Date fechaHoraFin, Usuario usuario){
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        this.usuario = usuario;
    }

    public Usuario getUsuario(){
        return this.usuario;
    }
    public void finalizar(Date fechaHoraFin){
        this.fechaHoraFin = fechaHoraFin;
    }

    
}
