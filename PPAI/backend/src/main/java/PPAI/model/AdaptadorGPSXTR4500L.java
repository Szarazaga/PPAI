package PPAI.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;


public class AdaptadorGPSXTR4500L implements IAdaptadorGPSTracker {
    private String apiKey;
    private final ServicioGPSXTR4500L servicio;



    public AdaptadorGPSXTR4500L(String apiKey) {

        this.apiKey = apiKey; //ApiKey hardcodeada
        this.servicio = new ServicioGPSXTR4500L();
    }

    @Override
    public List<String[]> obtenerUbicacionBolsines(List<Integer> numerosBolsines, String cmOrigen, String CMDestino) {
        List<String> ubicaciones = new ArrayList<>();

        for (Integer i : numerosBolsines) {
            String ubicacionBolsin = servicio.getBolsinLocation(this.apiKey, i, cmOrigen);
            ubicaciones.add(ubicacionBolsin);
        }

        return this.adaptarDatos(ubicaciones);
    }

    public List<String[]> adaptarDatos(List<String> ubicaciones) {
        ObjectMapper mapper = new ObjectMapper();
        List<String[]> ubicacionesAdaptadas = new ArrayList<>();

        for (String json : ubicaciones) {
            if (json != null && !json.isBlank()) {
                try {
                    JsonNode root = mapper.readTree(json);
                    JsonNode items = root.get("items");

                    if (items != null && items.isArray() && !items.isEmpty()) {
                        JsonNode item = items.get(0);
                        ubicacionesAdaptadas.add(new String[]{
                                item.get("numeroBolsin").asText(),
                                item.get("latitud").asText(),
                                item.get("longitud").asText(),
                                item.get("fechaHoraUltimaActualizacion").asText()
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return ubicacionesAdaptadas;
    }

    
}
