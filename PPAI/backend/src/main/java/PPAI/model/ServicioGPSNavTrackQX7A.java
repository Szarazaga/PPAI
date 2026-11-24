package PPAI.model;
import PPAI.dto.XtrResponse;
import org.springframework.web.client.RestClient;

public class ServicioGPSNavTrackQX7A {
    
    private final RestClient restClient;

    public ServicioGPSNavTrackQX7A(){
        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:8080")
                .build();
    }

    public XtrResponse.ItemBolsin retrieveTrackingData(String apiKey, Integer nroBolsin, String cmDestino){
        XtrResponse response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/navtrack-qx-7a/retrieveTrackingData")
                        .queryParam("numeroBolsin", nroBolsin)
                        .queryParam("codigoComisionMedicaDestino", cmDestino)
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
