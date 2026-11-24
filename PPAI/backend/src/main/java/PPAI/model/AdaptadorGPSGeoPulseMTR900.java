package PPAI.model;

import PPAI.dto.XtrResponse;
import java.util.ArrayList;
import java.util.List;

public class AdaptadorGPSGeoPulseMTR900 implements IAdaptadorGPSTracker {
  
    private String apiKey;
    private final ServicioGPSGeoPulseMTR900 servicio;


    public AdaptadorGPSGeoPulseMTR900(String apiKey) {

        this.apiKey = apiKey; //ApiKey hardcodeada
        this.servicio = new ServicioGPSGeoPulseMTR900();
    }

    @Override
    public List<String[]> obtenerUbicacionBolsines(List<Integer> numerosBolsines, String cmOrigen, String CMDestino) {
        return null;
    }
}
