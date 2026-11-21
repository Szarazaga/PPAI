package PPAI.dominio;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Bolsin {
    private int numeroBolsin ;
    private int numeroPrecinto;
    private Date fecha;
    private double peso;
    private List<CambioEstadoBolsin> cambioEstadoBolsin;
    private ComisionMedica origen;
    private ComisionMedica destino;

    public Bolsin(int numeroBolsin, int numeroPrecinto, Date fecha, int peso, Estado estadoInicial, ComisionMedica origen, ComisionMedica destino) {
        this.numeroBolsin = numeroBolsin;
        this.numeroPrecinto = numeroPrecinto;
        this.fecha = fecha;
        this.peso = peso;
        this.cambioEstadoBolsin = new ArrayList<>();
        this.cambioEstadoBolsin.add(new CambioEstadoBolsin(fecha, estadoInicial));
        this.origen = origen;
        this.destino = destino;
    }

    public boolean sosEnviado() {
        CambioEstadoBolsin cambioEstadoActual = null;
        //Buscar el CE actual
        for (CambioEstadoBolsin ce : cambioEstadoBolsin){
            if (ce.sosActual()){
                cambioEstadoActual = ce;
                break;
            }
        }
        //Averiguar si es enviado
        return cambioEstadoActual != null && cambioEstadoActual.sosEnviado();
    }

    public int getNroBolsin() {
        return this.numeroBolsin;
    }

    public boolean esTuCMOrigen(ComisionMedica cm) {
        return this.origen.equals(cm);
    }


}
