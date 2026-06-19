package ar.edu.unlar.programacion3.parcial1.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.unlar.programacion3.parcial1.model.EstacionAnclaje;
import ar.edu.unlar.programacion3.parcial1.model.Vehiculo;

/**
 * Servicio responsable de generar listados de vehículos ordenados según
 * distintos criterios operativos y comerciales.
 *
 * <p>Implementa los dos criterios requeridos para la logística de la
 * plataforma:</p>
 * <ul>
 *   <li><b>Prioridad de Carga (criterio natural):</b> utiliza el orden
 *       intrínseco definido por {@link Vehiculo#compareTo(Vehiculo)}
 *       ({@link Comparable}), de menor a mayor porcentaje de batería.</li>
 *   <li><b>Costo Base (criterio alternativo):</b> utiliza un
 *       {@link Comparator} externo al modelo de dominio, de mayor a menor
 *       tarifa, sin alterar el orden natural definido en {@code Vehiculo}.</li>
 * </ul>
 *
 * <p>Ambos criterios se resuelven con {@link Collections#sort}, cuya
 * implementación (TimSort) garantiza una complejidad O(n log n) en el
 * caso promedio y peor caso.</p>
 *
 * @author Programación III - UNLaR
 * @since 1.0
 */
@Service
public class OrdenamientoVehiculoService {

    /**
     * Devuelve los vehículos de la estación ordenados por su criterio
     * natural de prioridad de carga: porcentaje de batería ascendente
     * (los de menor energía primero, para cargarlos antes).
     *
     * @param estacion la estación de anclaje cuyos vehículos se desean ordenar.
     * @return una nueva lista de vehículos ordenada de menor a mayor batería.
     */
    public List<Vehiculo> ordenarPorPrioridadDeCarga(EstacionAnclaje estacion) {
        List<Vehiculo> vehiculos = new ArrayList<>(estacion.getVehiculosPorPatente().values());
        Collections.sort(vehiculos); // usa Vehiculo.compareTo() -> orden natural por batería
        return vehiculos;
    }

    /**
     * Devuelve los vehículos de la estación ordenados por costo base de
     * tarifa, de mayor a menor (los más caros primero).
     *
     * <p>Este criterio es externo al modelo de dominio: no modifica ni
     * depende del orden natural de {@link Vehiculo}, satisfaciendo el
     * requerimiento de que ambos criterios convivan sin interferirse.</p>
     *
     * @param estacion la estación de anclaje cuyos vehículos se desean ordenar.
     * @return una nueva lista de vehículos ordenada de mayor a menor tarifa.
     */
    public List<Vehiculo> ordenarPorCostoBaseDescendente(EstacionAnclaje estacion) {
        List<Vehiculo> vehiculos = new ArrayList<>(estacion.getVehiculosPorPatente().values());
        Collections.sort(vehiculos, new ComparadorPorTarifaDescendente());
        return vehiculos;
    }

    /**
     * Comparator externo que ordena vehículos por tarifa descendente
     * (de mayor a menor costo base).
     *
     * <p>Se implementa como clase estática anidada, sin recurrir a
     * expresiones lambda ni a la API de Streams, conforme a la restricción
     * de lógica imperativa tradicional establecida para este examen.</p>
     *
     * <p>Utiliza {@link Double#compare(double, double)} en lugar de una
     * resta directa entre tarifas, evitando el anti-patrón de overflow/
     * imprecisión descrito en el contrato de {@link Comparator}.</p>
     */
    private static class ComparadorPorTarifaDescendente implements Comparator<Vehiculo> {

        /**
         * Compara dos vehículos por tarifa, en orden descendente.
         *
         * @param v1 el primer vehículo.
         * @param v2 el segundo vehículo.
         * @return un valor negativo si {@code v1} tiene mayor tarifa que
         *         {@code v2} (por lo tanto va antes en orden descendente);
         *         cero si son iguales; positivo en caso contrario.
         */
        @Override
        public int compare(Vehiculo v1, Vehiculo v2) {
            // Orden descendente: invertimos el orden de los operandos
            // en lugar de usar reversed(), ya que esa API no está
            // restringida pero mantenemos el estilo explícito y tradicional.
            return Double.compare(v2.getTarifa(), v1.getTarifa());
        }
    }
}