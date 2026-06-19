package ar.edu.unlar.programacion3.parcial1.service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import ar.edu.unlar.programacion3.parcial1.model.alerta.AlertaGPS;

/**
 * Servicio responsable de eliminar reportes de geolocalización (GPS)
 * duplicados, producto de fallas intermitentes en las antenas de los
 * dispositivos de los vehículos.
 *
 * <p>El algoritmo realiza una única pasada O(n) sobre la colección recibida,
 * delegando la detección de duplicados en el contrato {@code equals}/
 * {@code hashCode} de {@link AlertaGPS} a través de un {@link LinkedHashSet}.
 * Esto evita el uso de bucles anidados (que resultarían en una complejidad
 * cuadrática O(n²)), ya que cada inserción en el conjunto resuelve la
 * comparación contra los elementos existentes en tiempo O(1) amortizado,
 * en lugar de comparar contra toda la colección ya procesada.</p>
 *
 * @author Programación III - UNLaR
 * @since 1.0
 */
@Service
public class DeduplicacionAlertasService {

    /**
     * Recibe una colección de reportes GPS potencialmente duplicados y
     * devuelve una colección de reportes únicos, preservando el orden de
     * la primera aparición de cada uno.
     *
     * @param alertasConDuplicados lista de alertas recibidas, posiblemente
     *        con repeticiones por fallas de antena.
     * @return un conjunto de alertas únicas, sin duplicados.
     */
    public Set<AlertaGPS> deduplicar(List<AlertaGPS> alertasConDuplicados) {
        Set<AlertaGPS> alertasUnicas = new LinkedHashSet<>();

        // Una única pasada (O(n)): cada add() resuelve hashCode()+equals()
        // en tiempo O(1) amortizado, sin comparar contra todos los elementos
        // ya insertados.
        for (AlertaGPS alerta : alertasConDuplicados) {
            alertasUnicas.add(alerta);
        }

        return alertasUnicas;
    }
}