package ar.edu.unlar.programacion3.parcial1.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unlar.programacion3.parcial1.dto.AlertaGpsRequest;
import ar.edu.unlar.programacion3.parcial1.dto.AlertaGpsResponse;
import ar.edu.unlar.programacion3.parcial1.dto.DeduplicacionResponse;
import ar.edu.unlar.programacion3.parcial1.model.alerta.AlertaGPS;
import ar.edu.unlar.programacion3.parcial1.service.DeduplicacionAlertasService;

/**
 * Expone el procesamiento de reportes de geolocalización (GPS) recibidos
 * desde los dispositivos de los vehículos, incluyendo la deduplicación de
 * reportes repetidos por fallas de antena.
 *
 * @author Programación III - UNLaR
 * @since 1.0
 */
@RestController
@RequestMapping("/api/alertas")
public class AlertaController {

    private final DeduplicacionAlertasService deduplicacionAlertasService;

    /**
     * Construye el controlador inyectando el servicio de deduplicación por constructor.
     *
     * @param deduplicacionAlertasService servicio encargado de eliminar reportes duplicados.
     */
    public AlertaController(DeduplicacionAlertasService deduplicacionAlertasService) {
        this.deduplicacionAlertasService = deduplicacionAlertasService;
    }

    /**
     * Recibe una colección de reportes GPS, potencialmente con duplicados
     * producto de fallas de antena, y devuelve la colección deduplicada.
     *
     * @param reportes lista de reportes recibidos en el cuerpo de la petición.
     * @return 200 OK con el detalle de la deduplicación realizada.
     */
    @PostMapping("/deduplicar")
    public ResponseEntity<DeduplicacionResponse> deduplicarAlertas(
            @RequestBody List<AlertaGpsRequest> reportes) {

        List<AlertaGPS> alertasRecibidas = convertirARequest(reportes);
        Set<AlertaGPS> alertasUnicas = deduplicacionAlertasService.deduplicar(alertasRecibidas);

        DeduplicacionResponse respuesta = new DeduplicacionResponse(
            alertasRecibidas.size(),
            alertasUnicas.size(),
            convertirAResponse(alertasUnicas)
        );

        return ResponseEntity.ok(respuesta);
    }

    /**
     * Traduce los reportes recibidos del cuerpo HTTP a instancias inmutables
     * del modelo de dominio {@link AlertaGPS}.
     *
     * @param reportes los reportes recibidos.
     * @return la lista convertida a {@link AlertaGPS}.
     */
    private List<AlertaGPS> convertirARequest(List<AlertaGpsRequest> reportes) {
        List<AlertaGPS> alertas = new ArrayList<>();
        for (AlertaGpsRequest reporte : reportes) {
            alertas.add(new AlertaGPS(reporte.getPatente(), reporte.getLatitud(), reporte.getLongitud()));
        }
        return alertas;
    }

    /**
     * Traduce un conjunto de {@link AlertaGPS} ya deduplicado a su
     * representación pública {@link AlertaGpsResponse}.
     *
     * @param alertas el conjunto de alertas únicas.
     * @return la lista de DTOs correspondiente.
     */
    private List<AlertaGpsResponse> convertirAResponse(Set<AlertaGPS> alertas) {
        List<AlertaGpsResponse> respuesta = new ArrayList<>();
        for (AlertaGPS alerta : alertas) {
            respuesta.add(new AlertaGpsResponse(alerta.getPatente(), alerta.getLatitud(), alerta.getLongitud()));
        }
        return respuesta;
    }
}