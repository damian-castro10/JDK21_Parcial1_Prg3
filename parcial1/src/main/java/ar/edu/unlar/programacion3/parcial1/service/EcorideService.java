package ar.edu.unlar.programacion3.parcial1.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.unlar.programacion3.parcial1.DataConfig.Config;
import ar.edu.unlar.programacion3.parcial1.dto.DesbloqueoResponse;
import ar.edu.unlar.programacion3.parcial1.dto.FinalizacionResponse;
import ar.edu.unlar.programacion3.parcial1.exception.BateriaInsuficienteException;
import ar.edu.unlar.programacion3.parcial1.exception.TransicionEstadoInvalidaException;
import ar.edu.unlar.programacion3.parcial1.model.BicicletaElectrica;
import ar.edu.unlar.programacion3.parcial1.model.EstacionAnclaje;
import ar.edu.unlar.programacion3.parcial1.model.ProcesamientoPago;
import ar.edu.unlar.programacion3.parcial1.model.TipoPago;
import ar.edu.unlar.programacion3.parcial1.model.Usuario;
import ar.edu.unlar.programacion3.parcial1.model.UsuarioPremium;
import ar.edu.unlar.programacion3.parcial1.model.Vehiculo;
import ar.edu.unlar.programacion3.parcial1.tarifa.ContextoTarifa;

/**
 * Orquesta el flujo completo de alquiler de vehículos de la plataforma
 * EcoRide: desbloqueo, finalización de viaje y cobro.
 *
 * <p>Delega responsabilidades específicas en colaboradores especializados:
 * la búsqueda de vehículos en {@link BusquedaVehiculoService}, el cálculo
 * de tarifa en {@link ContextoTarifa} (patrón Strategy), y el control del
 * ciclo de vida del vehículo en su propio estado interno (patrón State).</p>
 *
 * @author Programación III - UNLaR
 * @since 1.0
 */
@Service
public class EcorideService {

    private static final int BATERIA_MINIMA_REQUERIDA = 15;
    private static final double DESCUENTO_PREMIUM = 0.15;

    private final EstacionAnclaje estacionAnclaje;
    private final List<Usuario> usuariosRegistrados;
    private final BusquedaVehiculoService busquedaVehiculoService;
    private final ContextoTarifa contextoTarifa;

    /**
     * Construye el servicio inyectando sus colaboradores por constructor
     * (Inversión de Dependencias - DIP), e inicializa los datos simulados
     * de la plataforma a través de {@link Config}.
     *
     * @param busquedaVehiculoService servicio de búsqueda de vehículos por patente.
     * @param contextoTarifa contexto que mantiene la estrategia de tarifa activa.
     */
    public EcorideService(BusquedaVehiculoService busquedaVehiculoService,
                           ContextoTarifa contextoTarifa) {
        Config config = new Config();
        this.estacionAnclaje = config.inicializarEstacionConVehiculos();
        this.usuariosRegistrados = config.inicializarUsuariosSimulados();
        this.busquedaVehiculoService = busquedaVehiculoService;
        this.contextoTarifa = contextoTarifa;
    }

    /**
     * Devuelve la estación de anclaje gestionada por este servicio. Utilizada
     * por otros servicios (ordenamiento, reportes) que necesitan recorrer
     * el catálogo completo de vehículos.
     *
     * @return la estación de anclaje de la plataforma.
     */
    public EstacionAnclaje getEstacionAnclaje() {
        return estacionAnclaje;
    }

    /**
     * Procesa el desbloqueo de un vehículo: lo busca, valida su batería y
     * disponibilidad, cobra la tarifa base y transiciona el vehículo a
     * {@code EN_VIAJE}.
     *
     * @param idUsuario identificador del usuario que solicita el desbloqueo.
     * @param patente patente del vehículo a desbloquear.
     * @param metodoPago medio de pago a utilizar ({@code "TARJETA"} o {@code "BILLETERA"}).
     * @return un {@link DesbloqueoResponse} con el detalle de la operación.
     * @throws ar.edu.unlar.programacion3.parcial1.exception.VehiculoNoEncontradoException
     *         si no existe un vehículo con esa patente.
     * @throws BateriaInsuficienteException si la batería del vehículo es insuficiente.
     * @throws TransicionEstadoInvalidaException si el vehículo no está disponible para retiro.
     * @throws IllegalArgumentException si el método de pago no es reconocido.
     */
    public DesbloqueoResponse procesarDesbloqueo(String idUsuario, String patente, String metodoPago) {
        Vehiculo vehiculo = busquedaVehiculoService.buscarPorPatente(estacionAnclaje, patente);

        if (vehiculo.getPorcentajeDeBateria() < BATERIA_MINIMA_REQUERIDA) {
            throw new BateriaInsuficienteException(
                "Batería insuficiente para el vehículo " + patente
                    + " (" + vehiculo.getPorcentajeDeBateria() + "%).");
        }

        if (!vehiculo.estaDisponibleParaRetiro()) {
            throw new TransicionEstadoInvalidaException(
                "El vehículo " + patente + " no está disponible para retiro. Estado actual: "
                    + vehiculo.getEstadoActual().getNombre());
        }

        buscarUsuarioPorId(idUsuario); // valida que el usuario exista en la plataforma

        Double tarifaBaseDesbloqueo = vehiculo.getTarifa();
        ProcesamientoPago procesadorPago = resolverProcesadorPago(metodoPago);
        procesadorPago.cobrarTarifa(procesadorPago.getTipo(), tarifaBaseDesbloqueo);

        vehiculo.iniciarViaje(); // transición de estado: EN_ESPERA -> EN_VIAJE

        String tipoRodado = obtenerTipoRodado(vehiculo);
        return new DesbloqueoResponse(
            vehiculo.getNumeroDePatente(),
            tipoRodado,
            tarifaBaseDesbloqueo,
            "Desbloqueo exitoso. El vehículo quedó en estado EN_VIAJE."
        );
    }

    /**
     * Procesa la finalización de un viaje: calcula la duración, computa el
     * costo final según la estrategia de tarifa vigente (aplicando descuento
     * si el usuario es premium), cobra y transiciona el vehículo de vuelta
     * a {@code EN_ESPERA}.
     *
     * @param idUsuario identificador del usuario que finaliza el viaje.
     * @param patente patente del vehículo a devolver.
     * @param metodoPago medio de pago a utilizar para el cobro final.
     * @return un {@link FinalizacionResponse} con el detalle de la operación.
     * @throws ar.edu.unlar.programacion3.parcial1.exception.VehiculoNoEncontradoException
     *         si no existe un vehículo con esa patente.
     * @throws TransicionEstadoInvalidaException si el vehículo no estaba {@code EN_VIAJE}.
     * @throws IllegalArgumentException si el método de pago no es reconocido.
     */
    public FinalizacionResponse procesarFinalizacion(String idUsuario, String patente, String metodoPago) {
        Vehiculo vehiculo = busquedaVehiculoService.buscarPorPatente(estacionAnclaje, patente);

        double minutosTranscurridos = vehiculo.finalizarViaje(); // valida transición y calcula duración

        Usuario usuario = buscarUsuarioPorId(idUsuario);

        double costoFinal = contextoTarifa.calcularCosto(minutosTranscurridos, vehiculo.getTarifa());
        if (usuario instanceof UsuarioPremium) {
            costoFinal = costoFinal - (costoFinal * DESCUENTO_PREMIUM);
        }

        ProcesamientoPago procesadorPago = resolverProcesadorPago(metodoPago);
        procesadorPago.cobrarTarifa(procesadorPago.getTipo(), costoFinal);

        return new FinalizacionResponse(
            vehiculo.getNumeroDePatente(),
            minutosTranscurridos,
            costoFinal,
            contextoTarifa.getEstrategiaActiva().getNombre()
        );
    }

    /**
     * Resuelve el procesador de pago correspondiente al método solicitado.
     *
     * @param metodoPago {@code "TARJETA"} o {@code "BILLETERA"} (no distingue mayúsculas).
     * @return el procesador de pago configurado con el tipo correspondiente.
     * @throws IllegalArgumentException si el método de pago no es reconocido.
     */
    private ProcesamientoPago resolverProcesadorPago(String metodoPago) {
        if (metodoPago.equalsIgnoreCase("TARJETA")) {
            return new ProcesamientoPago(TipoPago.TARJETACREDITO);
        } else if (metodoPago.equalsIgnoreCase("BILLETERA")) {
            return new ProcesamientoPago(TipoPago.BILLETERAVIRTUAL);
        }
        throw new IllegalArgumentException("Medio de pago no reconocido: " + metodoPago);
    }

    /**
     * Determina el nombre legible del tipo de rodado de un vehículo.
     *
     * @param vehiculo el vehículo a inspeccionar.
     * @return {@code "Bicicleta Eléctrica"} o {@code "Monopatín"}.
     */
    private String obtenerTipoRodado(Vehiculo vehiculo) {
        if (vehiculo instanceof BicicletaElectrica) {
            return "Bicicleta Eléctrica";
        }
        return "Monopatín";
    }

    /**
     * Busca un usuario registrado por su identificador.
     *
     * @param idUsuario el identificador a buscar.
     * @return el usuario encontrado.
     * @throws RuntimeException si no existe un usuario con ese identificador.
     */
    private Usuario buscarUsuarioPorId(String idUsuario) {
        for (Usuario u : usuariosRegistrados) {
            if (u.getID().equals(idUsuario)) {
                return u;
            }
        }
        throw new RuntimeException("Usuario no registrado en la plataforma: " + idUsuario);
    }
}