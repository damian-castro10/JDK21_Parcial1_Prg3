package ar.edu.unlar.programacion3.parcial1.model.estado;

import ar.edu.unlar.programacion3.parcial1.exception.TransicionEstadoInvalidaException;
import ar.edu.unlar.programacion3.parcial1.model.Vehiculo;

/**
 * Representa la fase "En Espera" del ciclo de vida de un {@link Vehiculo}.
 *
 * <p>En esta fase, el vehículo descansa en la estación de anclaje, listo para
 * ser retirado por un usuario o enviado a reparación si presenta fallas.</p>
 *
 * <p>Transiciones válidas desde esta fase:</p>
 * <ul>
 *   <li>{@link #iniciarViaje(Vehiculo)} → pasa a {@link EstadoEnViaje}.</li>
 *   <li>{@link #enviarAReparacion(Vehiculo)} → pasa a {@link EstadoEnReparacion}.</li>
 * </ul>
 *
 * @author Programación III - UNLaR
 * @since 1.0
 */
public class EstadoEnEspera implements EstadoVehiculo {

    /** Instancia única compartida, dado que este estado no posee atributos propios. */
    public static final EstadoEnEspera INSTANCIA = new EstadoEnEspera();

    private static final String NOMBRE = "EN_ESPERA";

    private EstadoEnEspera() {
    }

    /**
     * {@inheritDoc}
     *
     * <p>Transición permitida: el vehículo pasa a {@code EN_VIAJE}.</p>
     */
    @Override
    public void iniciarViaje(Vehiculo vehiculo) {
        vehiculo.setEstadoActual(EstadoEnViaje.INSTANCIA);
    }

    /**
     * {@inheritDoc}
     *
     * @throws TransicionEstadoInvalidaException siempre, ya que un vehículo en espera
     *         no tiene un viaje en curso para finalizar.
     */
    @Override
    public void finalizarViaje(Vehiculo vehiculo) {
        throw new TransicionEstadoInvalidaException(
            "No se puede finalizar un viaje: el vehículo " + vehiculo.getNumeroDePatente()
                + " está EN_ESPERA y no tiene un viaje en curso.");
    }

    /**
     * {@inheritDoc}
     *
     * <p>Transición permitida: el vehículo pasa a {@code EN_REPARACION}.</p>
     */
    @Override
    public void enviarAReparacion(Vehiculo vehiculo) {
        vehiculo.setEstadoActual(EstadoEnReparacion.INSTANCIA);
    }

    /**
     * {@inheritDoc}
     *
     * @throws TransicionEstadoInvalidaException siempre, ya que un vehículo en espera
     *         no se encuentra en reparación.
     */
    @Override
    public void finalizarReparacion(Vehiculo vehiculo) {
        throw new TransicionEstadoInvalidaException(
            "No se puede finalizar una reparación: el vehículo " + vehiculo.getNumeroDePatente()
                + " está EN_ESPERA y no se encuentra en reparación.");
    }

    /**
     * {@inheritDoc}
     *
     * @return {@code true}, ya que un vehículo en espera es el único estado que permite retiro.
     */
    @Override
    public boolean permiteRetiro() {
        return true;
    }

    @Override
    public String getNombre() {
        return NOMBRE;
    }
}