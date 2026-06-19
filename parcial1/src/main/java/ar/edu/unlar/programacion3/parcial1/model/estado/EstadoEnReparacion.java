package ar.edu.unlar.programacion3.parcial1.model.estado;

import ar.edu.unlar.programacion3.parcial1.exception.TransicionEstadoInvalidaException;
import ar.edu.unlar.programacion3.parcial1.model.Vehiculo;

/**
 * Representa la fase "En Reparación" del ciclo de vida de un {@link Vehiculo}.
 *
 * <p>En esta fase, el vehículo presenta fallas de batería o mecánicas. Mientras
 * se encuentre en este estado, el sistema no debe permitir bajo ninguna
 * circunstancia que se inicie un viaje con él — regla de negocio explícita
 * y de máxima prioridad del dominio.</p>
 *
 * <p>Transición válida desde esta fase:</p>
 * <ul>
 *   <li>{@link #finalizarReparacion(Vehiculo)} → pasa a {@link EstadoEnEspera}.</li>
 * </ul>
 *
 * @author Programación III - UNLaR
 * @since 1.0
 */
public class EstadoEnReparacion implements EstadoVehiculo {

    /** Instancia única compartida, dado que este estado no posee atributos propios. */
    public static final EstadoEnReparacion INSTANCIA = new EstadoEnReparacion();

    private static final String NOMBRE = "EN_REPARACION";

    private EstadoEnReparacion() {
    }

    /**
     * {@inheritDoc}
     *
     * @throws TransicionEstadoInvalidaException siempre — regla de negocio de máxima
     *         prioridad: un vehículo en reparación jamás puede iniciar un viaje.
     */
    @Override
    public void iniciarViaje(Vehiculo vehiculo) {
        throw new TransicionEstadoInvalidaException(
            "No se puede iniciar un viaje: el vehículo " + vehiculo.getNumeroDePatente()
                + " está EN_REPARACION y no puede utilizarse bajo ninguna circunstancia.");
    }

    /**
     * {@inheritDoc}
     *
     * @throws TransicionEstadoInvalidaException siempre, ya que un vehículo en
     *         reparación no tiene un viaje en curso.
     */
    @Override
    public void finalizarViaje(Vehiculo vehiculo) {
        throw new TransicionEstadoInvalidaException(
            "No se puede finalizar un viaje: el vehículo " + vehiculo.getNumeroDePatente()
                + " está EN_REPARACION y no tiene un viaje en curso.");
    }

    /**
     * {@inheritDoc}
     *
     * @throws TransicionEstadoInvalidaException siempre, ya que el vehículo ya
     *         se encuentra en reparación.
     */
    @Override
    public void enviarAReparacion(Vehiculo vehiculo) {
        throw new TransicionEstadoInvalidaException(
            "No se puede enviar a reparación: el vehículo " + vehiculo.getNumeroDePatente()
                + " ya está EN_REPARACION.");
    }

    /**
     * {@inheritDoc}
     *
     * <p>Transición permitida: el vehículo vuelve a {@code EN_ESPERA}, quedando
     * nuevamente disponible para retiro.</p>
     */
    @Override
    public void finalizarReparacion(Vehiculo vehiculo) {
        vehiculo.setEstadoActual(EstadoEnEspera.INSTANCIA);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@code false}, ya que un vehículo en reparación no debe ser retirado
     *         bajo ninguna circunstancia.
     */
    @Override
    public boolean permiteRetiro() {
        return false;
    }

    @Override
    public String getNombre() {
        return NOMBRE;
    }
}