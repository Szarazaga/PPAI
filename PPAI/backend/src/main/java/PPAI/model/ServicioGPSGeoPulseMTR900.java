package PPAI.model;
import PPAI.dto.XtrResponse;
import org.springframework.web.client.RestClient;


public class ServicioGPSGeoPulseMTR900 {
    private final RestClient restClient;

    public ServicioGPSGeoPulseMTR900(){
        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:8080")
                .build();
    }

    public XtrResponse.ItemBolsin fetchCargoPositions(String apiKey, Integer nroBolsin){
        XtrResponse response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/geopulse-mtr-900/fetchCargoPositions")
                        .queryParam("numeroBolsin", nroBolsin)
                        .build())
                .header("X-API-Key", apiKey)
                .retrieve()
                .body(XtrResponse.class);
        if (response != null && response.items() != null && !response.items().isEmpty()) {
            return response.items().get(0);
        }
        return null;
    }
}
