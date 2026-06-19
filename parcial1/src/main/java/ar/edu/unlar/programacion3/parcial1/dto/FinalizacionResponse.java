package ar.edu.unlar.programacion3.parcial1.dto;

/**
 * Representación pública e inmutable del resultado de la finalización de un
 * viaje, expuesta por el endpoint {@code GET /api/alquileres/finalizar}.
 *
 * @param patente número de patente del vehículo devuelto.
 * @param minutosTranscurridos duración total del viaje, en minutos.
 * @param costoFinal costo final calculado según la estrategia de tarifa vigente
 *        al momento de finalizar el viaje.
 * @param criterioTarifaAplicado nombre del criterio de tarifa utilizado en el cálculo
 *        (ej. {@code "HORA_PICO"}).
 *
 * @author Programación III - UNLaR
 * @since 1.0
 */
public record FinalizacionResponse(
    String patente,
    double minutosTranscurridos,
    double costoFinal,
    String criterioTarifaAplicado
) {
}