package PPAI.model;

import PPAI.dto.XtrResponse;
import java.util.ArrayList;
import java.util.List;

public class AdaptadorGPSGeoPulseMTR900 {
  
    private String apiKey;
    private final ServicioGPSXTR4500L servicio;


    public AdaptadorGPSGeoPulseMTR900(String apiKey) {

        this.apiKey = apiKey; //ApiKey hardcodeada
        this.servicio = new ServicioGPSXTR4500L();
    }

    @Override
    public List<String[]> obtenerUbicacionBolsines(List<Integer> numerosBolsines, String cmOrigen, String CMDestino) {

    }
}
