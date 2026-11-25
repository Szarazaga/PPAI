package PPAI.model;
import org.springframework.web.client.RestClient;

public class ServicioGPSXTR4500L {
    private final RestClient restClient;

    public ServicioGPSXTR4500L(){
        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:8080")
                .build();
    }

    public String getBolsinLocation(String apiKey, Integer nroBolsin, String cmOrigen) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/xtr-4500l/getBolsinLocation")
                        .queryParam("numeroBolsin", nroBolsin)
                        .queryParam("codigoComisionMedicaOrigen", cmOrigen)
                        .build())
                .header("X-API-Key", apiKey)
                .retrieve()
                .body(String.class);
    }
    
    
}
