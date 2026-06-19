package ar.edu.unlar.programacion3.parcial1.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Representa una estación de anclaje de la plataforma EcoRide, donde los vehículos
 * descansan a la espera de ser retirados.
 *
 * <p>Internamente, los vehículos se indexan por su número de patente en un
 * {@link HashMap}, lo que garantiza una búsqueda en tiempo constante amortizado
 * (O(1)) sin importar la cantidad de vehículos registrados — en contraposición
 * al recorrido secuencial O(n) utilizado en la versión inicial del sistema.</p>
 *
 * <p>El estado operativo de cada vehículo (en espera, en viaje, en reparación)
 * ya no se almacena en esta clase: reside en el propio {@link Vehiculo} a través
 * del patrón State, evitando una duplicación de la fuente de verdad.</p>
 *
 * @author Programación III - UNLaR
 * @since 1.0
 */
public class EstacionAnclaje {

    private String nombreUnico;

    /** Vehículos indexados por número de patente, para acceso O(1). */
    private Map<String, Vehiculo> vehiculosPorPatente;

    /**
     * Construye una estación de anclaje vacía con el nombre dado.
     *
     * @param nombreUnico nombre identificatorio de la estación.
     */
    public EstacionAnclaje(String nombreUnico) {
        this.nombreUnico = nombreUnico;
        this.vehiculosPorPatente = new HashMap<>();
    }

    public String getNombreUnico() {
        return nombreUnico;
    }

    public void setNombreUnico(String nombreUnico) {
        this.nombreUnico = nombreUnico;
    }

    /**
     * Devuelve el mapa interno de vehículos indexados por patente.
     *
     * <p>Se expone para permitir su recorrido completo en operaciones de
     * ordenamiento o reporte. Para búsquedas puntuales por patente, preferir
     * {@link #buscarPorPatente(String)} en lugar de iterar este mapa.</p>
     *
     * @return el mapa de vehículos, con la patente como clave.
     */
    public Map<String, Vehiculo> getVehiculosPorPatente() {
        return vehiculosPorPatente;
    }

    /**
     * Registra un vehículo en la estación, indexándolo por su número de patente.
     *
     * @param vehiculo el vehículo a registrar.
     */
    public void registrarVehiculo(Vehiculo vehiculo) {
        vehiculosPorPatente.put(vehiculo.getNumeroDePatente().toUpperCase(), vehiculo);
    }

    /**
     * Busca un vehículo por su número de patente en tiempo O(1) amortizado.
     *
     * @param patente la patente a buscar (no distingue mayúsculas/minúsculas).
     * @return el vehículo encontrado, o {@code null} si no existe ninguno con esa patente.
     */
    public Vehiculo buscarPorPatente(String patente) {
        if (patente == null) {
            return null;
        }
        return vehiculosPorPatente.get(patente.toUpperCase());
    }
}