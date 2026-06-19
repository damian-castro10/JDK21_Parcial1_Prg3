package ar.edu.unlar.programacion3.parcial1.tarifa;

/**
 * Define el contrato para las distintas políticas de cálculo de tarifa de viaje.
 *
 * <p>Aplica el patrón de diseño <b>Strategy</b>: cada implementación encapsula
 * un criterio económico distinto (estándar, hora pico, recargo climático),
 * permitiendo que la plataforma cambie el criterio de facturación activo en
 * tiempo de ejecución sin modificar el código que lo invoca (principio
 * Abierto/Cerrado - OCP).</p>
 *
 * @author Programación III - UNLaR
 * @since 1.0
 */
public interface EstrategiaTarifa {

    /**
     * Calcula el costo final del viaje según el criterio económico de la estrategia.
     *
     * @param minutosTranscurridos cantidad de minutos que duró el viaje.
     * @param tarifaBaseVehiculo tarifa base por minuto del vehículo alquilado.
     * @return el costo final calculado del viaje.
     */
    double calcular(double minutosTranscurridos, double tarifaBaseVehiculo);

    /**
     * Devuelve el nombre identificatorio del criterio, utilizado para
     * seleccionar la estrategia dinámicamente y para reportes.
     *
     * @return el nombre del criterio (ej. {@code "ESTANDAR"}).
     */
    String getNombre();
}