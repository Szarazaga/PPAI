package PPAI.dto;

public class NotificacionBolsinResponse {

    private boolean enviado;
    private String emailGcmDestino;
    private int numeroBolsin;
    private String latitud;
    private String longitud;
    private String fechaHoraUltimaActualizacion;
    private String mensaje;

    public NotificacionBolsinResponse(boolean enviado, String emailGcmDestino, int numeroBolsin,
                                      String latitud, String longitud, String fechaHoraUltimaActualizacion,
                                      String mensaje) {
        this.enviado = enviado;
        this.emailGcmDestino = emailGcmDestino;
        this.numeroBolsin = numeroBolsin;
        this.latitud = latitud;
        this.longitud = longitud;
        this.fechaHoraUltimaActualizacion = fechaHoraUltimaActualizacion;
        this.mensaje = mensaje;
    }

    public boolean isEnviado() { return enviado; }
    public void setEnviado(boolean enviado) { this.enviado = enviado; }

    public String getEmailGcmDestino() { return emailGcmDestino; }
    public void setEmailGcmDestino(String emailGcmDestino) { this.emailGcmDestino = emailGcmDestino; }

    public int getNumeroBolsin() { return numeroBolsin; }
    public void setNumeroBolsin(int numeroBolsin) { this.numeroBolsin = numeroBolsin; }

    public String getLatitud() { return latitud; }
    public void setLatitud(String latitud) { this.latitud = latitud; }

    public String getLongitud() { return longitud; }
    public void setLongitud(String longitud) { this.longitud = longitud; }

    public String getFechaHoraUltimaActualizacion() { return fechaHoraUltimaActualizacion; }
    public void setFechaHoraUltimaActualizacion(String fechaHoraUltimaActualizacion) { this.fechaHoraUltimaActualizacion = fechaHoraUltimaActualizacion; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}
