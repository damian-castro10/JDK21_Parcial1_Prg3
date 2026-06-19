package ar.edu.unlar.programacion3.parcial1.exception;

/**
 * Excepción lanzada cuando se solicita una transición de estado no válida
 * sobre un vehículo, de acuerdo a las reglas de su ciclo de vida (patrón State).
 *
 * <p>Ejemplos de transiciones inválidas: iniciar un viaje sobre un vehículo
 * que está {@code EN_REPARACION}, o enviar a reparación un vehículo que
 * está {@code EN_VIAJE}.</p>
 *
 * @author Programación III - UNLaR
 * @since 1.0
 */
public class TransicionEstadoInvalidaException extends RuntimeException {

    /**
     * Construye la excepción con un mensaje descriptivo de la transición
     * inválida solicitada.
     *
     * @param message detalle de la transición rechazada.
     */
    public TransicionEstadoInvalidaException(String message) {
        super(message);
    }
}