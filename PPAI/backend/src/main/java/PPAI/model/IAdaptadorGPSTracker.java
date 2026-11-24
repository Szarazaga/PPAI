package PPAI.model;

import java.util.List;

public interface IAdaptadorGPSTracker {
   public List<String[]> obtenerUbicacionBolsines(List<Integer> nroBolsin, String CMOrigen, String CMDestino);
}