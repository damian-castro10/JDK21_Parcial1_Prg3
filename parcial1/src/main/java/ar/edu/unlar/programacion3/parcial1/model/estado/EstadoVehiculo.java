package ar.edu.unlar.programacion3.parcial1.model.estado;

import ar.edu.unlar.programacion3.parcial1.exception.TransicionEstadoInvalidaException;
import ar.edu.unlar.programacion3.parcial1.model.Vehiculo;

/**
 * Representa el comportamiento asociado a una fase del ciclo de vida de un {@link Vehiculo}.
 *
 * <p>Aplica el patrón de diseño <b>State</b>: cada implementación concreta encapsula
 * únicamente las transiciones que son válidas desde esa fase puntual. De esta forma,
 * {@code Vehiculo} delega el control de su ciclo de vida en el estado actual, evitando
 * estructuras condicionales gigantescas (if/else anidados o switch) centralizadas en
 * un único punto.</p>
 *
 * <p>Cada nueva fase del ciclo de vida que se desee agregar en el futuro implica
 * únicamente crear una nueva clase que implemente esta interfaz, sin modificar las
 * fases existentes (principio Abierto/Cerrado - OCP).</p>
 *
 * @author Programación III - UNLaR
 * @since 1.0
 */
public interface EstadoVehiculo {

    /**
     * Intenta iniciar un viaje sobre el vehículo dado.
     *
     * @param vehiculo el vehículo sobre el cual se solicita la transición.
     * @throws TransicionEstadoInvalidaException si la fase actual no permite iniciar un viaje.
     */
    void iniciarViaje(Vehiculo vehiculo);

    /**
     * Intenta finalizar el viaje en curso sobre el vehículo dado.
     *
     * @param vehiculo el vehículo sobre el cual se solicita la transición.
     * @throws TransicionEstadoInvalidaException si la fase actual no permite finalizar un viaje.
     */
    void finalizarViaje(Vehiculo vehiculo);

    /**
     * Intenta enviar el vehículo a reparación.
     *
     * @param vehiculo el vehículo sobre el cual se solicita la transición.
     * @throws TransicionEstadoInvalidaException si la fase actual no permite pasar a reparación.
     */
    void enviarAReparacion(Vehiculo vehiculo);

    /**
     * Intenta finalizar la reparación en curso, devolviendo el vehículo a disponibilidad.
     *
     * @param vehiculo el vehículo sobre el cual se solicita la transición.
     * @throws TransicionEstadoInvalidaException si la fase actual no permite finalizar la reparación.
     */
    void finalizarReparacion(Vehiculo vehiculo);

    /**
     * Indica si, en la fase actual, el vehículo puede ser retirado por un usuario.
     *
     * <p>Se utiliza como condición previa al desbloqueo: solo los vehículos
     * {@code EN_ESPERA} pueden alquilarse.</p>
     *
     * @return {@code true} si el vehículo puede retirarse en esta fase; {@code false} en caso contrario.
     */
    boolean permiteRetiro();

    /**
     * Devuelve el nombre legible de la fase, utilizado para reportes y respuestas de API.
     *
     * @return el nombre de la fase (ej. {@code "EN_ESPERA"}).
     */
    String getNombre();
}