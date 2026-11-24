package PPAI;

import org.springframework.web.client.RestClient;

public class GpsTrackerClient {

    private static final String BASE_URL = "http://localhost:8080";

    private static final RestClient restClient = RestClient.builder()
            .baseUrl(BASE_URL)
            .build();

    public static String getBolsinLocation(String api, int numeroBolsin, String codigoComisionMedicaOrigen) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/xtr-4500l/getBolsinLocation")
                        .queryParam("numeroBolsin", numeroBolsin)
                        .queryParam("codigoComisionMedicaOrigen", codigoComisionMedicaOrigen)
                        .build())
                .header("X-API-Key", api)
                .retrieve()
                .body(String.class);
    }
    public static String retrieveTrackingData(String api, int numeroBolsin, String codigoComisionMedicaDestino) {


        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/navtrack-qx-7a/retrieveTrackingData")
                        .queryParam("numeroBolsin", numeroBolsin)
                        .queryParam("codigoComisionMedicaDestino", codigoComisionMedicaDestino)
                        .build())
                .header("X-API-Key", api)
                .retrieve()
                .body(String.class);
    }
    public static String fetchCargoPositions(String api, int numeroBolsin) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/xtr-4500l//api/geopulse-mtr-900/fetchCargoPositions")
                        .queryParam("numeroBolsin", numeroBolsin)
                        .queryParam("codigoComisionMedicaOrigen", codigoComisionMedicaOrigen)
                        .build())
                .header("X-API-Key", api)
                .retrieve()
                .body(String.class);
    }
}
