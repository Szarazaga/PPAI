package PPAI;

import java.time.LocalDateTime;

public class AdaptadorGPSXTR4500L implements AdaptadorGPSTracker {

    private final String api;

    public AdaptadorGPSXTR4500L(String api) {
        this.api = api;
    }

    @Override
    public Object[][] obtenerUbicacionBolsines(int[] nrosBolsin, String[] cmsOrigen, String[] cmsDestino) {
        Object[][] matrizUbicaciones = new Object[nrosBolsin.length][];

        for (int i = 0; i < nrosBolsin.length; i++) {
            String datos = GpsTrackerClient.getBolsinLocation(api, nrosBolsin[i], cmsOrigen[i]);

            if (datos == null || datos.isBlank()) {
                throw new IllegalStateException(
                        "El GPS no devolvió datos para el bolsín " + nrosBolsin[i]);
            }

            String[] datosSeparados = separarDatosString(datos);
            Object[] datosAdaptados = adaptarDatos(datosSeparados);
            matrizUbicaciones[i] = datosAdaptados;
        }

        return matrizUbicaciones;
    }
    private String[] separarDatosString(String datos) {
        String[] partes = datos.split(",");
        for (int i = 0; i < partes.length; i++) {
            partes[i] = partes[i].trim();
        }
        return partes;
    }

    private Object[] adaptarDatos(String[] datos) {
        if (datos.length < 4) {
            throw new IllegalArgumentException(
                    "Respuesta del GPS con formato inesperado: se esperaban 4 campos y llegaron " + datos.length);
        }
        Object[] datosAdaptados = new Object[4];
        datosAdaptados[0] = Integer.parseInt(datos[0]);
        datosAdaptados[1] = Float.parseFloat(datos[1]);
        datosAdaptados[2] = Float.parseFloat(datos[2]);
        datosAdaptados[3] = LocalDateTime.parse(datos[3]);
        return datosAdaptados;
    }
}