package PPAI.dto;
import java.util.List;

public record XtrResponse(List<ItemBolsin> items) {
    public record ItemBolsin(
            int numeroBolsin,
            double latitud,
            double longitud,
            String fechaHoraUltimaActualizacion
    ) {}
}
