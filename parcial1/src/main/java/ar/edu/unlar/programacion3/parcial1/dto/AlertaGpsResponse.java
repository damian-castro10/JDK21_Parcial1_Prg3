package ar.edu.unlar.programacion3.parcial1.dto;

/**
 * Representación pública e inmutable de un reporte GPS ya deduplicado,
 * expuesta en la respuesta de {@code POST /api/alertas/deduplicar}.
 *
 * @param patente número de patente del vehículo.
 * @param latitud coordenada de latitud del reporte.
 * @param longitud coordenada de longitud del reporte.
 *
 * @author Programación III - UNLaR
 * @since 1.0
 */
public record AlertaGpsResponse(
    String patente,
    double latitud,
    double longitud
) {
}