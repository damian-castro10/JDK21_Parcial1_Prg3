package ar.edu.unlar.programacion3.parcial1.dto;

/**
 * Representación pública e inmutable del resultado de un desbloqueo de vehículo,
 * expuesta por el endpoint {@code GET /api/alquileres/desbloquear}.
 *
 * @param patente número de patente del vehículo desbloqueado.
 * @param tipoVehiculo tipo de rodado ({@code "Monopatín"} o {@code "Bicicleta Eléctrica"}).
 * @param montoCobrado importe cobrado al iniciar el desbloqueo.
 * @param mensaje detalle legible del resultado de la operación.
 *
 * @author Programación III - UNLaR
 * @since 1.0
 */
public record DesbloqueoResponse(
    String patente,
    String tipoVehiculo,
    double montoCobrado,
    String mensaje
) {
}