package PPAI.model;

import PPAI.dto.XtrResponse;
import java.util.ArrayList;
import java.util.List;


public class AdaptadorGPSXTR4500L implements IAdaptadorGPSTracker {
    private String apiKey;
    private final ServicioGPSXTR4500L servicio;
    private List<String[]> matrizUbicaciones;


    public AdaptadorGPSXTR4500L(String apiKey) {

        this.apiKey = "sk_xtr_9F3a7C21bE6d4A10f2c8e5b7d913"; //ApiKey hardcodeada
        this.servicio = new ServicioGPSXTR4500L();
    }

    @Override
    public List<String[]> obtenerUbicacionBolsines(List<Integer> numerosBolsines, String cmOrigen, String CMDestino) {

        List<XtrResponse.ItemBolsin> ubicaciones = new ArrayList<>();
        XtrResponse.ItemBolsin ubicacionBolsin = null;

        for ( Integer i: numerosBolsines ) {
            ubicacionBolsin = servicio.getBolsinLocation(this.apiKey, i , cmOrigen);
            ubicaciones.add(ubicacionBolsin);
        }

        return this.adaptarDatos(ubicaciones);
    }

    public List<String[]> adaptarDatos(List<XtrResponse.ItemBolsin> ubicaciones){

        List<String[]> ubicacionesAdaptadas = new ArrayList<>();

        for ( XtrResponse.ItemBolsin i : ubicaciones ) {
            if (i != null) {
                ubicacionesAdaptadas.add(new String[]{
                        String.valueOf(i.numeroBolsin()),
                        String.valueOf(i.latitud()),
                        String.valueOf(i.longitud()),
                        i.fechaHoraUltimaActualizacion()
                });
            }
        }
        return ubicacionesAdaptadas;
    }

    
}
