package ar.edu.unlar.programacion3.parcial1.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.unlar.programacion3.parcial1.DataConfig.Config;
import ar.edu.unlar.programacion3.parcial1.exception.BateriaInsuficienteException;
import ar.edu.unlar.programacion3.parcial1.exception.VehiculoNoEncontradoException;
import ar.edu.unlar.programacion3.parcial1.model.*;

@Service
public class EcorideService {

    private final EstacionAnclaje estacionAnclaje;
    private final List<Usuario> usuariosRegistrados;

    public EcorideService() {
        Config config = new Config();
        this.estacionAnclaje = config.inicializarEstacionConVehiculos();
        this.usuariosRegistrados = config.inicializarUsuariosSimulados();
    }

    public String procesarDesbloqueo(String idUsuario, String patente, String metodoPago) {
        
        //Localizar el vehículo dentro de la estacion
        Vehiculo vehiculoSolicitado = null;
        for (Vehiculo vehiculo : estacionAnclaje.getListadoVehiculo().keySet()) {
            if (vehiculo.getNumeroDePatente().equalsIgnoreCase(patente)) {
                vehiculoSolicitado = vehiculo;
                break;
            }
        }

        if (vehiculoSolicitado == null) {
            throw new VehiculoNoEncontradoException("Vehículo No Encontrado");
        }

        // validar que el nivel de batería sea apto
        if (vehiculoSolicitado.getPorcentajeDeBateria() < 15) {
            throw new BateriaInsuficienteException("Batería Insuficiente");
        }

        // Obtener el usuario que solicita el viaje
        Usuario usuario = buscarUsuarioPorId(idUsuario);

        // Calcular el importe final del desbloqueo

        Double importeFinal = vehiculoSolicitado.getTarifa();
        if (usuario instanceof UsuarioPremium) {
            importeFinal = importeFinal - (importeFinal * 0.15); 
        }

        // Obtener el medio de pago adecuado
        ProcesamientoPago procesadorPago;
        if (metodoPago.equalsIgnoreCase("TARJETA")) {
            procesadorPago = new ProcesamientoPago(TipoPago.TARJETACREDITO);
        } else if (metodoPago.equalsIgnoreCase("BILLETERA")) {
            procesadorPago = new ProcesamientoPago(TipoPago.BILLETERAVIRTUAL);
        } else {
            throw new IllegalArgumentException("Medio de pago no reconocido: " + metodoPago);
        }

        // Efectuar el cobro
        procesadorPago.cobrarTarifa(procesadorPago.getTipo(), importeFinal);

        // Retornar una respuesta exitosa
        String tipoRodado = vehiculoSolicitado instanceof BicicletaElectrica ? "Bicicleta Eléctrica" : "Monopatín";
        return "Desbloqueo exitoso. Rodado: " + tipoRodado + " (Patente: " + patente + "). Monto cobrado: $" + importeFinal;
    }

    // Método auxiliar para buscar al usuario
    private Usuario buscarUsuarioPorId(String idUsuario) {
        for (Usuario u : usuariosRegistrados) {
            if (u.getID().equals(idUsuario)) {
                return u;
            }
        }
        throw new RuntimeException("Usuario no registrado en la plataforma");
    }
}
