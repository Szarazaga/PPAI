package PPAI.model;
import org.springframework.web.client.RestClient;


public class ServicioGPSGeoPulseMTR900 {
    private final RestClient restClient;

    public ServicioGPSGeoPulseMTR900(){
        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:8080")
                .build();
    }

    public String[][] fetchCargoPositions(String apiKey, Integer nroBolsin) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/geopulse-mtr-900/fetchCargoPositions")
                        .queryParam("numeroBolsin", nroBolsin)
                        .build())
                .header("X-API-Key", apiKey)
                .retrieve()
                .body(String[][].class);
    }
}
