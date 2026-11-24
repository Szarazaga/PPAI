package PPAI;
import java.util.Date;

public class Bolsin {
    private int numeroBolsin ;
    private int numeroPrecinto;
    private Date fecha;
    private int peso;
    private CambioEstadoBolsin[] cambioEstadoBolsin;
    private ComisionMedica origen;
    private ComisionMedica destino;

    public Bolsin(int numeroBolsin, int numeroPrecinto, Date fecha, int peso) {
        this.numeroBolsin = numeroBolsin;
        this.numeroPrecinto = numeroPrecinto;
        this.fecha = fecha;
        this.peso = peso;
    }
    public boolean sosEnviado() {
        for (CambioEstadoBolsin cambio : this.cambioEstadoBolsin) {
            if (cambio.sosActual() && cambio.sosEnviado()) {
                return true;
            }
        }
        return false;
    }
    public int getNroBolsin() {
        return this.numeroBolsin;
    }
    public boolean esTuCMOrigen(ComisionMedica cm) {
        return this.origen.equals(cm);
    }


}
