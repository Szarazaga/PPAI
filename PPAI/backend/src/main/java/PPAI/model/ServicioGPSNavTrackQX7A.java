package PPAI.model;
import org.springframework.web.client.RestClient;

public class ServicioGPSNavTrackQX7A {
    
    private final RestClient restClient;

    public ServicioGPSNavTrackQX7A(){
        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:8080")
                .build();
    }

    public String retrieveTrackingData(String apiKey, Integer nroBolsin, String cmDestino) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/navtrack-qx-7a/retrieveTrackingData")
                        .queryParam("numeroBolsin", nroBolsin)
                        .queryParam("codigoComisionMedicaDestino", cmDestino)
                        .build())
                .header("X-API-Key", apiKey)
                .retrieve()
                .body(String.class);
    }

}
