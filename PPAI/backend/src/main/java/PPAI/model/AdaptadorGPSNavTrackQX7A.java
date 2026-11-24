package PPAI.model;

import PPAI.dto.XtrResponse;
import java.util.ArrayList;
import java.util.List;

public class AdaptadorGPSNavTrackQX7A implements IAdaptadorGPSTracker {
    private String apiKey;
    private final ServicioGPSNavTrackQX7A servicio;


    public AdaptadorGPSNavTrackQX7A(String apiKey) {

        this.apiKey = apiKey; //ApiKey hardcodeada
        this.servicio = new ServicioGPSNavTrackQX7A();
    }

    @Override
    public List<String[]> obtenerUbicacionBolsines(List<Integer> numerosBolsines, String cmOrigen, String CMDestino) {
        return null;
    }
}
