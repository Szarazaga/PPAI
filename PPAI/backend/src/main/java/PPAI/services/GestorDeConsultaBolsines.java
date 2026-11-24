package PPAI.services;

import PPAI.dto.UbicacionBolsinesResponse;
import PPAI.model.*;
import PPAI.repository.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service

public class GestorDeConsultaBolsines {
    //Repositorios
    private final IBolsinRepository bolsinRepository;
    private final IEmpleadoRepository empleadoRepository;
    private final IComisionMedicaRepository comisionMedicaRepository;
    private final ISesionRepository sesionRepository;
    private final ServicioGoogleMaps servicioGoogleMaps;

    private String codigoCM;
    @Value("${gps.api.key}")
    private String apiKey;
    private List<String[]> matrizUbicaciones;
    private List<Integer> numerosBolsines;
    private List<Empleado> empleados;  //Cambiar cuando configuremos la base de datos
    private List<Bolsin> bolsines; //idem
    private Bolsin bolsinSeleccionado;
    private Sesion sesionActiva;
    private UbicacionBolsinesResponse ubicacionBolsinesResponse;
    private String emailGCMDestino;

    public GestorDeConsultaBolsines(
            IBolsinRepository bolsinRepository,
            IEmpleadoRepository empleadoRepository,
            IComisionMedicaRepository comisionMedicaRepository,
            ISesionRepository sesionRepository,
            ServicioGoogleMaps servicioGoogleMaps){
        this.bolsinRepository = bolsinRepository;
        this.empleadoRepository = empleadoRepository;
        this.comisionMedicaRepository = comisionMedicaRepository;
        this.sesionRepository = sesionRepository;
        this.servicioGoogleMaps = servicioGoogleMaps;
    }

    public UbicacionBolsinesResponse consultarUbicacionDeBolsines(){
        //Sesion activa
        this.sesionActiva = this.sesionRepository.findFirstByFechaHoraFinIsNull();

        //Empleados
        this.empleados = this.empleadoRepository.findAll();

        //Bolsines
        this.bolsines = this.bolsinRepository.findAll();

        buscarCMUsuarioLogueado();
        buscarBolsinesDeCMEnEstadoEnviado();
        pedirDatosLocalizacion();
        pedirMapa();
        return this.ubicacionBolsinesResponse;
    }

    public void buscarCMUsuarioLogueado(){
        Usuario usuarioLogueado = sesionActiva.getUsuario();
        String codigoCMEmpleado = null;


        for ( Empleado e: empleados ) {
            if (e.esTuUsuario(usuarioLogueado)) {
                codigoCMEmpleado = e.obtenerCodigoCM();

                break;
            }
        }

        this.codigoCM = codigoCMEmpleado;

    }

    public void buscarBolsinesDeCMEnEstadoEnviado(){
        List<Integer> numerosBolsines = new ArrayList<>();

        for ( Bolsin b: bolsines ) {
            if (b.esTuCMOrigen(codigoCM) && (b.sosEnviado())){
                numerosBolsines.add(b.getNroBolsin());
            }
        }

        this.numerosBolsines = numerosBolsines;

    }

    //Patron Adapter
    public void pedirDatosLocalizacion(){
        IAdaptadorGPSTracker adaptadorConcreto = new AdaptadorGPSXTR4500L(this.apiKey);
        this.matrizUbicaciones = adaptadorConcreto.obtenerUbicacionBolsines(numerosBolsines, codigoCM, null);
    }

    public void pedirMapa(){
        String urlMapa = this.servicioGoogleMaps.mostrarMapa(this.matrizUbicaciones);
        ComisionMedica cm = this.comisionMedicaRepository.findById(this.codigoCM).orElse(null);
        String nombreCM = (cm != null) ? cm.getNombre() : "";

        this.ubicacionBolsinesResponse = new UbicacionBolsinesResponse(
                this.codigoCM,
                nombreCM,
                this.matrizUbicaciones,
                urlMapa
        );

    }

    public UbicacionBolsinesResponse filtrarBolsines(Integer numeroPrecinto, String codigoCmDestino) {

        // Si todavía no se ejecutó la consulta inicial, la carga automáticamente
        if (this.matrizUbicaciones == null || this.bolsines == null) {
            consultarUbicacionDeBolsines();
        }

        List<String[]> ubicacionesFiltradas = new ArrayList<>();

        for (String[] fila : this.matrizUbicaciones) {
            int nroBolsin = Integer.parseInt(fila[0]);

            Bolsin bolsin = this.bolsines.stream()
                    .filter(b -> b.getNroBolsin() == nroBolsin)
                    .findFirst()
                    .orElse(null);

            if (bolsin != null) {
                boolean coincidePrecinto = (numeroPrecinto == null) || (bolsin.getNroPrecinto() == numeroPrecinto);
                boolean coincideDestino = (codigoCmDestino == null || codigoCmDestino.isBlank())
                        || bolsin.esTuCMDestino(codigoCmDestino);

                if (coincidePrecinto && coincideDestino) {
                    ubicacionesFiltradas.add(fila);
                }
            }
        }

        String urlMapa = ubicacionesFiltradas.isEmpty()
                ? ""
                : this.servicioGoogleMaps.mostrarMapa(ubicacionesFiltradas);

        ComisionMedica cm = this.comisionMedicaRepository.findById(this.codigoCM).orElse(null);
        String nombreCM = (cm != null) ? cm.getNombre() : "";

        return new UbicacionBolsinesResponse(this.codigoCM, nombreCM, ubicacionesFiltradas, urlMapa);
    }

    // Paso 7: Guarda el bolsín seleccionado del mapa
    public void tomarSeleccionBolsin(int numeroBolsin) {
        if (this.bolsines == null || this.matrizUbicaciones == null){
            consultarUbicacionDeBolsines();
        }

        this.bolsinSeleccionado = this.bolsines.stream()
                .filter(b -> b.getNroBolsin() == numeroBolsin)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el bolsín seleccionado: " + numeroBolsin));
    }

    // Pasos 9, 10 y 11: Busca el correo del GCM destino y llama al CU 31 incluido

    public Map<String, Object> tomarConfirmacionEnvioCorreo() {
        // Paso 10: Busca la dirección de correo electrónico del GCM destino
        buscarCorreoGCMDestino(this.bolsinSeleccionado.getCMDestino().getCodigo());

        // Busca las coordenadas y fecha/hora de última actualización del bolsín seleccionado
        String[] ubicacion = this.matrizUbicaciones.stream()
                .filter(u -> Integer.parseInt(u[0]) == this.bolsinSeleccionado.getNroBolsin())
                .findFirst()
                .orElse(new String[]{String.valueOf(this.bolsinSeleccionado.getNroBolsin()), "", "", ""});

        int nroBolsin = this.bolsinSeleccionado.getNroBolsin();
        String latitud = ubicacion[1];
        String longitud = ubicacion[2];
        String fechaHoraUltimaActualizacion = ubicacion[3];

        // Paso 11: Incluye al Caso de Uso 31 Notificar ubicación de bolsín
        notificarUbicacionBolsinCU31(this.emailGCMDestino, nroBolsin, fechaHoraUltimaActualizacion, latitud, longitud);

        finCU();

        return Map.of(
                "enviado", true,
                "emailGcmDestino", emailGCMDestino,
                "numeroBolsin", nroBolsin,
                "latitud", latitud,
                "longitud", longitud,
                "fechaHoraUltimaActualizacion", fechaHoraUltimaActualizacion,
                "mensaje", "Ejecución exitosa del Caso de Uso 31: Notificar ubicación de bolsín."
        );
    }

    public void buscarCorreoGCMDestino(String codigoCMDestino){
        for ( Empleado e: empleados ) {
            if (e.sosGCM(codigoCMDestino)) {
                this.emailGCMDestino = e.getEmail();

                break;
            }
        }
    };
    // Stub del Caso de Uso incluido (CU 31: Notificar ubicación de bolsín)
    private void notificarUbicacionBolsinCU31(String emailGcmDestino, int numeroBolsin,
                                              String fechaHoraUltimaActualizacion, String latitud, String longitud) {
        // Se delega la notificación al Caso de Uso 31 (fuera del alcance de este CU)
    }

    private void finCU() {
    }
}
