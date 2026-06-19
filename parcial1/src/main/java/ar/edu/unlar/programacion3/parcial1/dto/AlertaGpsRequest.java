package ar.edu.unlar.programacion3.parcial1.dto;

/**
 * Representa un reporte de geolocalización tal como llega en el cuerpo de
 * la petición HTTP {@code POST /api/alertas/deduplicar}.
 *
 * <p>A diferencia de {@link ar.edu.unlar.programacion3.parcial1.model.alerta.AlertaGPS}
 * (inmutable, con campos {@code final}), esta clase es mutable y expone un
 * constructor vacío, requisito de Jackson para deserializar JSON. Una vez
 * recibido, cada {@code AlertaGpsRequest} se traduce a un {@code AlertaGPS}
 * inmutable antes de procesarse.</p>
 *
 * @author Programación III - UNLaR
 * @since 1.0
 */
public class AlertaGpsRequest {

    private String patente;
    private double latitud;
    private double longitud;

    /**
     * Constructor vacío requerido por Jackson para la deserialización de JSON.
     */
    public AlertaGpsRequest() {
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}