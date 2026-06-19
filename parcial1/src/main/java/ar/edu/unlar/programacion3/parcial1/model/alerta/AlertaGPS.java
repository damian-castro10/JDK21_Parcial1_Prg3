package ar.edu.unlar.programacion3.parcial1.model.alerta;

import java.util.Objects;

/**
 * Representa un reporte de geolocalización (GPS) emitido por un vehículo.
 *
 * <p>Debido a fallas intermitentes en las antenas de los dispositivos de
 * geolocalización, es común recibir reportes duplicados: el mismo vehículo
 * reportando exactamente la misma posición más de una vez. Esta clase define
 * {@link #equals(Object)} y {@link #hashCode()} en base a la patente del
 * vehículo y sus coordenadas, lo que permite deduplicar grandes colecciones
 * de alertas en una única pasada utilizando una {@link java.util.HashSet}
 * o {@link java.util.LinkedHashSet}, evitando el uso de bucles anidados.</p>
 *
 * @author Programación III - UNLaR
 * @since 1.0
 */
public class AlertaGPS {

    private final String patente;
    private final double latitud;
    private final double longitud;

    /**
     * Construye un reporte de geolocalización.
     *
     * @param patente número de patente del vehículo que emite el reporte.
     * @param latitud coordenada de latitud reportada.
     * @param longitud coordenada de longitud reportada.
     */
    public AlertaGPS(String patente, double latitud, double longitud) {
        this.patente = patente;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getPatente() {
        return patente;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    /**
     * Dos alertas se consideran duplicadas si pertenecen a la misma patente
     * y reportan exactamente las mismas coordenadas.
     *
     * @param obj el objeto a comparar.
     * @return {@code true} si representan el mismo reporte de posición.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AlertaGPS)) {
            return false;
        }
        AlertaGPS otra = (AlertaGPS) obj;
        return Double.compare(latitud, otra.latitud) == 0
            && Double.compare(longitud, otra.longitud) == 0
            && Objects.equals(patente, otra.patente);
    }

    /**
     * Calcula el código hash en base a los mismos campos utilizados en
     * {@link #equals(Object)} (patente, latitud y longitud), garantizando
     * el contrato {@code equals}/{@code hashCode} requerido por las
     * colecciones basadas en tablas de hash.
     *
     * @return el código hash de la alerta.
     */
    @Override
    public int hashCode() {
        return Objects.hash(patente, latitud, longitud);
    }

    @Override
    public String toString() {
        return "AlertaGPS{patente='" + patente + "', latitud=" + latitud
            + ", longitud=" + longitud + '}';
    }
}