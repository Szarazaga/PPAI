package PPAI;

public class AdaptadorGPSXTR4500L implements AdaptadorGPSTracker {
    private String api;
    private Object[][] matrizUbicaciones;
    @Override
    public Object[][] obtenerUbicacionBolsines(int[] nroBolsin, String[] CMOrigen, String[] CMDestino) {

        return new Object[0][0]; 
    }
    public AdaptadorGPSXTR4500L(String api) {
        this.api = api;
    }
    
}
