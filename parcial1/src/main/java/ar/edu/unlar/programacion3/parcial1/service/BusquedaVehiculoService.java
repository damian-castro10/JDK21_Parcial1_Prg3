package ar.edu.unlar.programacion3.parcial1.service;

import org.springframework.stereotype.Service;

import ar.edu.unlar.programacion3.parcial1.exception.VehiculoNoEncontradoException;
import ar.edu.unlar.programacion3.parcial1.model.EstacionAnclaje;
import ar.edu.unlar.programacion3.parcial1.model.Vehiculo;

/**
 * Servicio responsable exclusivamente de la búsqueda de vehículos dentro de
 * una {@link EstacionAnclaje}.
 *
 * <p>Aprovecha la indexación por patente de {@link EstacionAnclaje} para
 * resolver búsquedas en tiempo O(1) amortizado, reemplazando el recorrido
 * secuencial O(n) utilizado en la versión inicial del sistema.</p>
 *
 * @author Programación III - UNLaR
 * @since 1.0
 */
@Service
public class BusquedaVehiculoService {

    /**
     * Busca un vehículo por su número de patente.
     *
     * @param estacion la estación de anclaje sobre la cual buscar.
     * @param patente la patente del vehículo solicitado.
     * @return el vehículo encontrado.
     * @throws VehiculoNoEncontradoException si no existe ningún vehículo con esa patente.
     */
    public Vehiculo buscarPorPatente(EstacionAnclaje estacion, String patente) {
        Vehiculo vehiculo = estacion.buscarPorPatente(patente);
        if (vehiculo == null) {
            throw new VehiculoNoEncontradoException(
                "No se encontró ningún vehículo con la patente: " + patente);
        }
        return vehiculo;
    }
}