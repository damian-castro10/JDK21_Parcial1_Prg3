package ar.edu.unlar.programacion3.parcial1.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unlar.programacion3.parcial1.tarifa.ContextoTarifa;
import ar.edu.unlar.programacion3.parcial1.tarifa.EstrategiaTarifa;
import ar.edu.unlar.programacion3.parcial1.tarifa.TarifaEstandar;
import ar.edu.unlar.programacion3.parcial1.tarifa.TarifaHoraPico;
import ar.edu.unlar.programacion3.parcial1.tarifa.TarifaTemporalClimatico;

/**
 * Expone el endpoint para consultar y modificar en tiempo de ejecución la
 * estrategia de cálculo de tarifa activa en la plataforma.
 *
 * <p>La selección de la estrategia concreta a partir del criterio recibido
 * por parámetro se resuelve mediante un {@code Map<String, EstrategiaTarifa>},
 * evitando estructuras condicionales (if/else o switch) y permitiendo
 * incorporar nuevos criterios sin modificar este controlador (OCP).</p>
 *
 * @author Programación III - UNLaR
 * @since 1.0
 */
@RestController
@RequestMapping("/api/tarifas")
public class TarifaController {

    private final ContextoTarifa contextoTarifa;
    private final Map<String, EstrategiaTarifa> estrategiasDisponibles;

    /**
     * Construye el controlador inyectando el contexto de tarifa por
     * constructor y registrando las estrategias disponibles.
     *
     * @param contextoTarifa el contexto que mantiene la estrategia activa.
     */
    public TarifaController(ContextoTarifa contextoTarifa) {
        this.contextoTarifa = contextoTarifa;
        this.estrategiasDisponibles = new HashMap<>();
        estrategiasDisponibles.put("ESTANDAR", new TarifaEstandar());
        estrategiasDisponibles.put("HORA_PICO", new TarifaHoraPico());
        estrategiasDisponibles.put("TEMPORAL_CLIMATICO", new TarifaTemporalClimatico());
    }

    /**
     * Devuelve el nombre del criterio de tarifa actualmente activo.
     *
     * @return 200 OK con el nombre de la estrategia vigente.
     */
    @GetMapping("/criterio-actual")
    public ResponseEntity<String> consultarCriterioActual() {
        return ResponseEntity.ok(contextoTarifa.getEstrategiaActiva().getNombre());
    }

    /**
     * Cambia la estrategia de tarifa activa en tiempo de ejecución, sin
     * necesidad de reiniciar la aplicación.
     *
     * @param criterio nombre del criterio a activar
     *        ({@code "ESTANDAR"}, {@code "HORA_PICO"} o {@code "TEMPORAL_CLIMATICO"}).
     * @return 200 OK con un mensaje de confirmación, o 400 Bad Request si el
     *         criterio no existe.
     */
    @GetMapping("/cambiar-criterio")
    public ResponseEntity<String> cambiarCriterio(@RequestParam String criterio) {
        EstrategiaTarifa nuevaEstrategia = estrategiasDisponibles.get(criterio.toUpperCase());

        if (nuevaEstrategia == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Criterio de tarifa no válido: " + criterio
                    + ". Criterios aceptados: " + estrategiasDisponibles.keySet());
        }

        contextoTarifa.setEstrategia(nuevaEstrategia);
        return ResponseEntity.ok("Criterio de tarifa actualizado a: " + nuevaEstrategia.getNombre());
    }
}