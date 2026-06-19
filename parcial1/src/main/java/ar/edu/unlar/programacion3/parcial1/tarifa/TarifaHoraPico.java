package ar.edu.unlar.programacion3.parcial1.tarifa;

/**
 * Criterio de tarifa en hora pico: aplica un recargo del 40% sobre el costo
 * estándar del viaje, para incentivar la rotación de vehículos en momentos
 * de alta congestión urbana.
 *
 * @author Programación III - UNLaR
 * @since 1.0
 */
public class TarifaHoraPico implements EstrategiaTarifa {

    private static final String NOMBRE = "HORA_PICO";
    private static final double RECARGO_HORA_PICO = 0.40;

    /**
     * {@inheritDoc}
     *
     * @return el costo estándar incrementado en un 40%.
     */
    @Override
    public double calcular(double minutosTranscurridos, double tarifaBaseVehiculo) {
        double costoEstandar = minutosTranscurridos * tarifaBaseVehiculo;
        return costoEstandar + (costoEstandar * RECARGO_HORA_PICO);
    }

    @Override
    public String getNombre() {
        return NOMBRE;
    }
}