package ar.edu.unlar.programacion3.parcial1.model.estado;

import ar.edu.unlar.programacion3.parcial1.exception.TransicionEstadoInvalidaException;
import ar.edu.unlar.programacion3.parcial1.model.Vehiculo;

/**
 * Representa la fase "En Viaje" del ciclo de vida de un {@link Vehiculo}.
 *
 * <p>En esta fase, el usuario está conduciendo el vehículo. Mientras se encuentre
 * en este estado, el vehículo no puede ser alquilado por otro usuario ni enviado
 * a mantenimiento — regla de negocio explícita del dominio.</p>
 *
 * <p>Transición válida desde esta fase:</p>
 * <ul>
 *   <li>{@link #finalizarViaje(Vehiculo)} → pasa a {@link EstadoEnEspera}.</li>
 * </ul>
 *
 * @author Programación III - UNLaR
 * @since 1.0
 */
public class EstadoEnViaje implements EstadoVehiculo {

    /** Instancia única compartida, dado que este estado no posee atributos propios. */
    public static final EstadoEnViaje INSTANCIA = new EstadoEnViaje();

    private static final String NOMBRE = "EN_VIAJE";

    private EstadoEnViaje() {
    }

    /**
     * {@inheritDoc}
     *
     * @throws TransicionEstadoInvalidaException siempre, ya que un vehículo ya
     *         en viaje no puede iniciar otro viaje simultáneamente.
     */
    @Override
    public void iniciarViaje(Vehiculo vehiculo) {
        throw new TransicionEstadoInvalidaException(
            "No se puede iniciar un viaje: el vehículo " + vehiculo.getNumeroDePatente()
                + " ya está EN_VIAJE.");
    }

    /**
     * {@inheritDoc}
     *
     * <p>Transición permitida: el vehículo vuelve a {@code EN_ESPERA}.</p>
     */
    @Override
    public void finalizarViaje(Vehiculo vehiculo) {
        vehiculo.setEstadoActual(EstadoEnEspera.INSTANCIA);
    }

    /**
     * {@inheritDoc}
     *
     * @throws TransicionEstadoInvalidaException siempre — regla de negocio explícita:
     *         un vehículo en viaje no puede ser enviado a mantenimiento mientras
     *         el usuario lo esté conduciendo.
     */
    @Override
    public void enviarAReparacion(Vehiculo vehiculo) {
        throw new TransicionEstadoInvalidaException(
            "No se puede enviar a reparación: el vehículo " + vehiculo.getNumeroDePatente()
                + " está EN_VIAJE y no puede interrumpirse el alquiler en curso.");
    }

    /**
     * {@inheritDoc}
     *
     * @throws TransicionEstadoInvalidaException siempre, ya que un vehículo en viaje
     *         no se encuentra en reparación.
     */
    @Override
    public void finalizarReparacion(Vehiculo vehiculo) {
        throw new TransicionEstadoInvalidaException(
            "No se puede finalizar una reparación: el vehículo " + vehiculo.getNumeroDePatente()
                + " está EN_VIAJE y no se encuentra en reparación.");
    }

    /**
     * {@inheritDoc}
     *
     * @return {@code false}, ya que un vehículo en viaje ya está siendo utilizado
     *         y no puede ser retirado por otro usuario.
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