package ar.edu.unlar.programacion3.parcial1.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unlar.programacion3.parcial1.exception.BateriaInsuficienteException;
import ar.edu.unlar.programacion3.parcial1.exception.VehiculoNoEncontradoException;
import ar.edu.unlar.programacion3.parcial1.service.EcorideService;

@RestController
@RequestMapping("/api/alquileres")
public class EcorideController {

    private final EcorideService ecorideService;

    public EcorideController(EcorideService ecorideService) {
        this.ecorideService = ecorideService;
    }

@GetMapping("/desbloquear")
public ResponseEntity<String> desbloquearVehiculo(
        @RequestParam String idUsuario,
        @RequestParam String patente,
        @RequestParam String metodoPago) {
    try {
        String resultado = ecorideService.procesarDesbloqueo(idUsuario, patente, metodoPago);
        return ResponseEntity.ok(resultado);

    } catch (VehiculoNoEncontradoException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());

    } catch (BateriaInsuficienteException e) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error: " + e.getMessage());

    } catch (IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());

    } catch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
    }
}
}