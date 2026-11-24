package PPAI.model;
import PPAI.dto.XtrResponse;
import org.springframework.web.client.RestClient;

public class ServicioGPSXTR4500L {
    private final RestClient restClient;

    public ServicioGPSXTR4500L(){
        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:8080")
                .build();
    }

    public XtrResponse.ItemBolsin getBolsinLocation(String apiKey, Integer nroBolsin, String cmOrigen){
        XtrResponse response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/xtr-4500l/getBolsinLocation")
                        .queryParam("numeroBolsin", nroBolsin)
                        .queryParam("codigoComisionMedicaOrigen", cmOrigen)
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
