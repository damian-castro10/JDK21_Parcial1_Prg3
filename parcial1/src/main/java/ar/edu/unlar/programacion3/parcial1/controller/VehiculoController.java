package ar.edu.unlar.programacion3.parcial1.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unlar.programacion3.parcial1.dto.VehiculoResponse;
import ar.edu.unlar.programacion3.parcial1.model.BicicletaElectrica;
import ar.edu.unlar.programacion3.parcial1.model.Vehiculo;
import ar.edu.unlar.programacion3.parcial1.service.EcorideService;
import ar.edu.unlar.programacion3.parcial1.service.OrdenamientoVehiculoService;

/**
 * Expone los reportes de vehículos ordenados según los criterios operativos
 * y comerciales de la plataforma.
 *
 * <p>Las entidades de dominio nunca se devuelven directamente: se traducen
 * a {@link VehiculoResponse} para no exponer información interna (como el
 * estado del patrón State) y desacoplar el contrato de la API del modelo
 * de dominio.</p>
 *
 * @author Programación III - UNLaR
 * @since 1.0
 */
@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    private final EcorideService ecorideService;
    private final OrdenamientoVehiculoService ordenamientoVehiculoService;

    /**
     * Construye el controlador inyectando sus colaboradores por constructor.
     *
     * @param ecorideService servicio que provee acceso a la estación de anclaje.
     * @param ordenamientoVehiculoService servicio que genera los listados ordenados.
     */
    public VehiculoController(EcorideService ecorideService,
                               OrdenamientoVehiculoService ordenamientoVehiculoService) {
        this.ecorideService = ecorideService;
        this.ordenamientoVehiculoService = ordenamientoVehiculoService;
    }

    /**
     * Devuelve los vehículos ordenados por prioridad de carga: porcentaje
     * de batería ascendente (criterio natural, los de menor energía primero).
     *
     * @return 200 OK con la lista de vehículos ordenada.
     */
    @GetMapping("/prioridad-carga")
    public ResponseEntity<List<VehiculoResponse>> listarPorPrioridadDeCarga() {
        List<Vehiculo> vehiculosOrdenados =
            ordenamientoVehiculoService.ordenarPorPrioridadDeCarga(ecorideService.getEstacionAnclaje());
        return ResponseEntity.ok(convertirAResponse(vehiculosOrdenados));
    }

    /**
     * Devuelve los vehículos ordenados por costo base de tarifa descendente
     * (criterio alternativo, los más caros primero).
     *
     * @return 200 OK con la lista de vehículos ordenada.
     */
    @GetMapping("/tarifa-descendente")
    public ResponseEntity<List<VehiculoResponse>> listarPorTarifaDescendente() {
        List<Vehiculo> vehiculosOrdenados =
            ordenamientoVehiculoService.ordenarPorCostoBaseDescendente(ecorideService.getEstacionAnclaje());
        return ResponseEntity.ok(convertirAResponse(vehiculosOrdenados));
    }

    /**
     * Traduce una lista de entidades {@link Vehiculo} a su representación
     * pública {@link VehiculoResponse}.
     *
     * @param vehiculos la lista de vehículos a convertir.
     * @return la lista de DTOs correspondiente.
     */
    private List<VehiculoResponse> convertirAResponse(List<Vehiculo> vehiculos) {
        List<VehiculoResponse> respuesta = new ArrayList<>();
        for (Vehiculo vehiculo : vehiculos) {
            String tipo = (vehiculo instanceof BicicletaElectrica) ? "Bicicleta Eléctrica" : "Monopatín";
            respuesta.add(new VehiculoResponse(
                vehiculo.getNumeroDePatente(),
                tipo,
                vehiculo.getPorcentajeDeBateria(),
                vehiculo.getTarifa(),
                vehiculo.getEstadoActual().getNombre()
            ));
        }
        return respuesta;
    }
}