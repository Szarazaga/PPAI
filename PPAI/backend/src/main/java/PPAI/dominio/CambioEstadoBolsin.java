package PPAI.dominio;
import java.util.Date;
public class CambioEstadoBolsin {
    
    private Date fechaHoraInicio;
    private Date fechaHoraFin;
    private Estado estado;

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
