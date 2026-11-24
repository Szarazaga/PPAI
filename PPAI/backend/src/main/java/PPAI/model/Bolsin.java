package PPAI.model;
import jakarta.persistence.*;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bolsines")
public class Bolsin {

    @Id
    @Column(nullable = false, unique = true)
    private int numeroBolsin ;

    @Column(nullable = false)
    private Integer numeroPrecinto;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date fecha;

    @Column(nullable = false)
    private Double peso;

    // Bolsin -> CambioEstadoBolsin (1..*)
    // CambioEstadoBolsin NO conoce su Bolsin
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "bolsin_numero")
    private List<CambioEstadoBolsin> cambioEstadoBolsin = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "cm_origen_codigo")
    private ComisionMedica origen;

    @ManyToOne
    @JoinColumn(name = "cm_destino_codigo")
    private ComisionMedica destino;

    //Construtor para JPA
    protected Bolsin(){}

    public Bolsin(int numeroBolsin, int numeroPrecinto, Date fecha, double peso, Estado estadoInicial, ComisionMedica origen, ComisionMedica destino) {
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

    public boolean esTuCMOrigen(String codigoCM) {

        return this.origen.esTuCodigo(codigoCM);
    }


}
