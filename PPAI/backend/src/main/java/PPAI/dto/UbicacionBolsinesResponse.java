package PPAI.dto;
import java.util.List;

public class UbicacionBolsinesResponse {

    private String codigoCM;
    private String nombreCM;
    private List<String[]> ubicaciones;
    private String urlMapa;

    public UbicacionBolsinesResponse() {
    }

    public UbicacionBolsinesResponse(String codigoCM, String nombreCM, List<String[]> ubicaciones, String urlMapa) {

        this.codigoCM = codigoCM;
        this.nombreCM = nombreCM;
        this.ubicaciones = ubicaciones;
        this.urlMapa = urlMapa;
    }

    public List<String[]> getUbicaciones() {
        return ubicaciones;
    }

    public String getCodigoCM(){return this.codigoCM;}
    public String getNombreCM(){return this.nombreCM;}

    public void setUbicaciones(List<String[]> ubicaciones) {
        this.ubicaciones = ubicaciones;
    }

    public String getUrlMapa() {
        return urlMapa;
    }

    public void setUrlMapa(String urlMapa) {
        this.urlMapa = urlMapa;
    }
}



