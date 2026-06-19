package ar.edu.unlar.programacion3.parcial1.tarifa;

/**
 * Criterio de tarifa estándar: multiplica los minutos transcurridos por la
 * tarifa base del vehículo, sin recargos adicionales.
 *
 * @author Programación III - UNLaR
 * @since 1.0
 */
public class TarifaEstandar implements EstrategiaTarifa {

    private static final String NOMBRE = "ESTANDAR";

    /**
     * {@inheritDoc}
     *
     * @return {@code minutosTranscurridos * tarifaBaseVehiculo}.
     */
    @Override
    public double calcular(double minutosTranscurridos, double tarifaBaseVehiculo) {
        return minutosTranscurridos * tarifaBaseVehiculo;
    }

    @Override
    public String getNombre() {
        return NOMBRE;
    }
}