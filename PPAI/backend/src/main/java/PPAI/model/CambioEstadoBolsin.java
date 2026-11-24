package PPAI.model;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "CambiosEstadosBolsin")
public class CambioEstadoBolsin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date fechaHoraInicio;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraFin;

    @ManyToOne
    @JoinColumn(name = "estado_id")
    private Estado estado;

    //Construtor para JPA
    protected CambioEstadoBolsin(){}

    public CambioEstadoBolsin(Date fechaHoraInicio, Estado estado) {
        this.fechaHoraInicio = fechaHoraInicio;
        this.estado = estado;
    }
    public boolean sosEnviado() {
        return this.estado.sosEnviado();
    }
    public boolean sosActual() {
        return this.fechaHoraFin == null;
    }
    public void setFechaHoraFin(Date fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }
}
