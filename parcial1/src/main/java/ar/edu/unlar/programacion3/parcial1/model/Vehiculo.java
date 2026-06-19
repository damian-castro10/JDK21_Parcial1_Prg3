package ar.edu.unlar.programacion3.parcial1.model;

import ar.edu.unlar.programacion3.parcial1.model.estado.EstadoEnEspera;
import ar.edu.unlar.programacion3.parcial1.model.estado.EstadoVehiculo;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Representa un vehículo eléctrico de la plataforma EcoRide (monopatín o bicicleta).
 *
 * <p>Encapsula el ciclo de vida del vehículo mediante el patrón de diseño <b>State</b>
 * (ver {@link EstadoVehiculo}): toda transición de fase se delega al estado actual,
 * que es el único responsable de decidir si la transición es válida.</p>
 *
 * <p>Define además un orden natural ({@link Comparable}) por porcentaje de batería
 * ascendente, utilizado para priorizar la carga de los vehículos con menor energía
 * restante.</p>
 *
 * @author Programación III - UNLaR
 * @since 1.0
 */
public abstract class Vehiculo implements Comparable<Vehiculo> {

    protected String numeroDePatente;
    protected int porcentajeDeBateria;
    protected Double tarifa;

    protected LocalDateTime inicioViajeActual;

    /**
     * Fase actual del ciclo de vida del vehículo. Por defecto, todo vehículo
     * se inicializa {@code EN_ESPERA}, listo para ser retirado.
     */
    protected EstadoVehiculo estadoActual = EstadoEnEspera.INSTANCIA;

    /**
     * Construye un vehículo con sus atributos básicos. El estado inicial siempre
     * es {@link EstadoEnEspera}.
     *
     * @param numeroDePatente identificador único del vehículo.
     * @param porcentajeDeBateria nivel de batería actual, expresado de 0 a 100.
     * @param tarifa tarifa base por minuto de alquiler.
     */
    public Vehiculo(String numeroDePatente, int porcentajeDeBateria, Double tarifa) {
        this.numeroDePatente = numeroDePatente;
        this.porcentajeDeBateria = porcentajeDeBateria;
        this.tarifa = tarifa;
    }


    

    public String getNumeroDePatente() {
        return numeroDePatente;
    }

    public void setNumeroDePatente(String numeroDePatente) {
        this.numeroDePatente = numeroDePatente;
    }

    public int getPorcentajeDeBateria() {
        return porcentajeDeBateria;
    }

    public void setPorcentajeDeBateria(int porcentajeDeBateria) {
        this.porcentajeDeBateria = porcentajeDeBateria;
    }

    public Double getTarifa() {
        return tarifa;
    }

    public void setTarifa(Double tarifa) {
        this.tarifa = tarifa;
    }

    /**
     * Devuelve la fase actual del ciclo de vida del vehículo.
     *
     * @return el estado actual.
     */
    public EstadoVehiculo getEstadoActual() {
        return estadoActual;
    }

    /**
     * Establece la fase actual del vehículo.
     *
     * <p>Este método es invocado exclusivamente por las implementaciones de
     * {@link EstadoVehiculo} al resolver una transición válida. No debe
     * invocarse directamente desde capas de servicio o controladores: las
     * transiciones deben solicitarse a través de {@link #iniciarViaje()},
     * {@link #finalizarViaje()}, {@link #enviarAReparacion()} o
     * {@link #finalizarReparacion()}.</p>
     *
     * @param nuevoEstado el nuevo estado del vehículo.
     */
    public void setEstadoActual(EstadoVehiculo nuevoEstado) {
        this.estadoActual = nuevoEstado;
    }

    /**
     * Solicita iniciar un viaje. Delega la validación de la transición al estado actual.
     *
     * @throws ar.edu.unlar.programacion3.parcial1.exception.TransicionEstadoInvalidaException
     *         si la fase actual no permite iniciar un viaje.
     */
    public void iniciarViaje() {
        estadoActual.iniciarViaje(this);
        this.inicioViajeActual = LocalDateTime.now();
    }

    /**
     * Solicita finalizar el viaje en curso. Delega la validación al estado actual.
     *
     * @throws ar.edu.unlar.programacion3.parcial1.exception.TransicionEstadoInvalidaException
     *         si la fase actual no permite finalizar un viaje.
     */
    public double finalizarViaje() {
        double minutosTranscurridos = calcularMinutosEnViaje();
        estadoActual.finalizarViaje(this);
        this.inicioViajeActual = null;
        return minutosTranscurridos;
    }

    private double calcularMinutosEnViaje() {
    if (inicioViajeActual == null) {
        return 0.0;
    }
    return ChronoUnit.SECONDS.between(inicioViajeActual, LocalDateTime.now()) / 60.0;
    }

    /**
     * Solicita enviar el vehículo a reparación. Delega la validación al estado actual.
     *
     * @throws ar.edu.unlar.programacion3.parcial1.exception.TransicionEstadoInvalidaException
     *         si la fase actual no permite pasar a reparación.
     */
    public void enviarAReparacion() {
        estadoActual.enviarAReparacion(this);
    }

    /**
     * Solicita finalizar la reparación en curso. Delega la validación al estado actual.
     *
     * @throws ar.edu.unlar.programacion3.parcial1.exception.TransicionEstadoInvalidaException
     *         si la fase actual no permite finalizar la reparación.
     */
    public void finalizarReparacion() {
        estadoActual.finalizarReparacion(this);
    }

    /**
     * Indica si, en la fase actual, el vehículo está disponible para ser retirado.
     *
     * @return {@code true} si puede retirarse; {@code false} en caso contrario.
     */
    public boolean estaDisponibleParaRetiro() {
        return estadoActual.permiteRetiro();
    }

    /**
     * Define el orden natural de los vehículos: por porcentaje de batería ascendente.
     *
     * <p>Se utiliza {@link Integer#compare(int, int)} en lugar de una resta directa
     * para evitar el riesgo de overflow descrito en el contrato de {@link Comparable}.</p>
     *
     * @param otro el otro vehículo a comparar.
     * @return un valor negativo si este vehículo tiene menos batería, cero si tienen
     *         igual batería, o un valor positivo si tiene más batería.
     */
    @Override
    public int compareTo(Vehiculo otro) {
        return Integer.compare(this.porcentajeDeBateria, otro.porcentajeDeBateria);
    }
}