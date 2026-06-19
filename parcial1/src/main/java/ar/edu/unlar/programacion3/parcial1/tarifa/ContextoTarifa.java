package ar.edu.unlar.programacion3.parcial1.tarifa;

import org.springframework.stereotype.Service;

/**
 * Mantiene la estrategia de cálculo de tarifa actualmente activa en la
 * plataforma, permitiendo reemplazarla dinámicamente en tiempo de ejecución.
 *
 * <p>Al ser gestionado por Spring como un bean singleton, este contexto
 * conserva la estrategia activa durante toda la vida de la aplicación y
 * a través de múltiples peticiones HTTP, satisfaciendo el requerimiento
 * de permitir cambios de criterio sin reiniciar el servidor.</p>
 *
 * <p>El componente que invoca {@link #calcularCosto(double, double)} no
 * necesita conocer qué estrategia concreta está activa: delega el cálculo
 * a través de la abstracción {@link EstrategiaTarifa} (principio de
 * Inversión de Dependencias - DIP).</p>
 *
 * @author Programación III - UNLaR
 * @since 1.0
 */
@Service
public class ContextoTarifa {

    /** Estrategia activa al iniciar la plataforma: tarifa estándar. */
    private EstrategiaTarifa estrategiaActiva = new TarifaEstandar();

    /**
     * Reemplaza la estrategia de tarifa activa.
     *
     * @param nuevaEstrategia la nueva estrategia a utilizar a partir de este momento.
     */
    public void setEstrategia(EstrategiaTarifa nuevaEstrategia) {
        this.estrategiaActiva = nuevaEstrategia;
    }

    /**
     * Devuelve la estrategia de tarifa actualmente activa.
     *
     * @return la estrategia activa.
     */
    public EstrategiaTarifa getEstrategiaActiva() {
        return estrategiaActiva;
    }

    /**
     * Calcula el costo de un viaje utilizando la estrategia actualmente activa.
     *
     * @param minutosTranscurridos cantidad de minutos que duró el viaje.
     * @param tarifaBaseVehiculo tarifa base por minuto del vehículo alquilado.
     * @return el costo final calculado según el criterio vigente.
     */
    public double calcularCosto(double minutosTranscurridos, double tarifaBaseVehiculo) {
        return estrategiaActiva.calcular(minutosTranscurridos, tarifaBaseVehiculo);
    }
}