package PPAI.services;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioGoogleMaps {

    public String mostrarMapa(List<String[]> matrizUbicaciones) {
        // Construye una URL con los pins de cada bolsín (ej. Google Maps con marcadores)
        StringBuilder markers = new StringBuilder();
        for (String[] fila : matrizUbicaciones) {
            // asumiendo fila[0] = nroBolsin, fila[1] = latitud, fila[2] = longitud
            markers.append("&markers=label:").append(fila[0])
                    .append("%7C").append(fila[1]).append(",").append(fila[2]);
        }
        return "https://maps.googleapis.com/maps/api/staticmap?size=800x500" + markers;
    }
}
