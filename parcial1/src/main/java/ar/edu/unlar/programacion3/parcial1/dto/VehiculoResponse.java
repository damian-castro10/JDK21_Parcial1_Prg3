package ar.edu.unlar.programacion3.parcial1.dto;

/**
 * Representación pública e inmutable de un {@link ar.edu.unlar.programacion3.parcial1.model.Vehiculo}
 * para exponer en respuestas de la API REST.
 *
 * <p>Evita exponer directamente la entidad de dominio (que incluye el estado
 * interno del patrón State), mostrando únicamente la información relevante
 * para el cliente.</p>
 *
 * @param patente número de patente del vehículo.
 * @param tipo tipo de rodado ({@code "Monopatín"} o {@code "Bicicleta Eléctrica"}).
 * @param porcentajeDeBateria nivel de batería actual, de 0 a 100.
 * @param tarifa tarifa base por minuto.
 * @param estado nombre de la fase actual del ciclo de vida (ej. {@code "EN_ESPERA"}).
 *
 * @author Programación III - UNLaR
 * @since 1.0
 */
public record VehiculoResponse(
    String patente,
    String tipo,
    int porcentajeDeBateria,
    double tarifa,
    String estado
) {
}