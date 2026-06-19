package ar.edu.unlar.programacion3.parcial1.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unlar.programacion3.parcial1.dto.DesbloqueoResponse;
import ar.edu.unlar.programacion3.parcial1.dto.FinalizacionResponse;
import ar.edu.unlar.programacion3.parcial1.exception.BateriaInsuficienteException;
import ar.edu.unlar.programacion3.parcial1.exception.TransicionEstadoInvalidaException;
import ar.edu.unlar.programacion3.parcial1.exception.VehiculoNoEncontradoException;
import ar.edu.unlar.programacion3.parcial1.service.EcorideService;

/**
 * Expone las operaciones de alquiler de vehículos de la plataforma EcoRide:
 * desbloqueo y finalización de viaje.
 *
 * <p>Las respuestas se devuelven como estructuras JSON dedicadas
 * ({@link DesbloqueoResponse}, {@link FinalizacionResponse}), evitando
 * exponer las entidades de dominio internas o cadenas de texto sin
 * estructurar.</p>
 *
 * @author Programación III - UNLaR
 * @since 1.0
 */
@RestController
@RequestMapping("/api/alquileres")
public class EcorideController {

    private final EcorideService ecorideService;

    /**
     * Construye el controlador inyectando el servicio de orquestación por constructor.
     *
     * @param ecorideService el servicio que procesa desbloqueos y finalizaciones.
     */
    public EcorideController(EcorideService ecorideService) {
        this.ecorideService = ecorideService;
    }

    /**
     * Desbloquea un vehículo para iniciar un viaje.
     *
     * @param idUsuario identificador del usuario solicitante.
     * @param patente patente del vehículo a desbloquear.
     * @param metodoPago medio de pago ({@code "TARJETA"} o {@code "BILLETERA"}).
     * @return 200 OK con el detalle del desbloqueo, o el código de error correspondiente.
     */
    @GetMapping("/desbloquear")
    public ResponseEntity<?> desbloquearVehiculo(
            @RequestParam String idUsuario,
            @RequestParam String patente,
            @RequestParam String metodoPago) {
        try {
            DesbloqueoResponse resultado = ecorideService.procesarDesbloqueo(idUsuario, patente, metodoPago);
            return ResponseEntity.ok(resultado);

        } catch (VehiculoNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());

        } catch (BateriaInsuficienteException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error: " + e.getMessage());

        } catch (TransicionEstadoInvalidaException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: " + e.getMessage());

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    /**
     * Finaliza un viaje en curso, calculando el costo final según la
     * estrategia de tarifa vigente.
     *
     * @param idUsuario identificador del usuario que finaliza el viaje.
     * @param patente patente del vehículo a devolver.
     * @param metodoPago medio de pago para el cobro final.
     * @return 200 OK con el detalle de la finalización, o el código de error correspondiente.
     */
    @GetMapping("/finalizar")
    public ResponseEntity<?> finalizarViaje(
            @RequestParam String idUsuario,
            @RequestParam String patente,
            @RequestParam String metodoPago) {
        try {
            FinalizacionResponse resultado = ecorideService.procesarFinalizacion(idUsuario, patente, metodoPago);
            return ResponseEntity.ok(resultado);

        } catch (VehiculoNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());

        } catch (TransicionEstadoInvalidaException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: " + e.getMessage());

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}