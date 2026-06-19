package ar.edu.unlar.programacion3.parcial1.tarifa;

/**
 * Criterio de tarifa por temporal climático: suma un recargo de seguridad
 * plano y fijo al costo estándar del viaje, para cubrir seguros especiales
 * durante condiciones de lluvia o tormenta.
 *
 * @author Programación III - UNLaR
 * @since 1.0
 */
public class TarifaTemporalClimatico implements EstrategiaTarifa {

    private static final String NOMBRE = "TEMPORAL_CLIMATICO";
    private static final double RECARGO_PLANO_SEGURIDAD = 150.0;

    /**
     * {@inheritDoc}
     *
     * @return el costo estándar más el recargo plano de seguridad.
     */
    @Override
    public double calcular(double minutosTranscurridos, double tarifaBaseVehiculo) {
        double costoEstandar = minutosTranscurridos * tarifaBaseVehiculo;
        return costoEstandar + RECARGO_PLANO_SEGURIDAD;
    }

    @Override
    public String getNombre() {
        return NOMBRE;
    }
}