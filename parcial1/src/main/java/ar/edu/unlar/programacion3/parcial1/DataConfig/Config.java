package ar.edu.unlar.programacion3.parcial1.DataConfig;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unlar.programacion3.parcial1.model.BicicletaElectrica;
import ar.edu.unlar.programacion3.parcial1.model.EstacionAnclaje;
import ar.edu.unlar.programacion3.parcial1.model.Monopatin;
import ar.edu.unlar.programacion3.parcial1.model.Usuario;
import ar.edu.unlar.programacion3.parcial1.model.UsuarioPremium;

/**
 * Inicializa los datos simulados de la plataforma EcoRide: usuarios
 * registrados y vehículos disponibles en la estación de anclaje.
 *
 * <p>No se utiliza persistencia externa (base de datos ni ORM): toda la
 * información vive en memoria durante el ciclo de vida de la aplicación,
 * de acuerdo a las restricciones del examen.</p>
 *
 * @author Programación III - UNLaR
 * @since 1.0
 */
public class Config {

    /**
     * Genera la lista de usuarios simulados de la plataforma, incluyendo
     * usuarios regulares y premium.
     *
     * @return la lista de usuarios registrados.
     */
    public List<Usuario> inicializarUsuariosSimulados() {
        List<Usuario> usuarios = new ArrayList<>();

        usuarios.add(new Usuario("101", "Juan Pérez"));
        usuarios.add(new Usuario("102", "Gómez Ana"));
        usuarios.add(new Usuario("103", "Carlos Rodríguez"));
        usuarios.add(new Usuario("104", "Luisa Martínez"));

        usuarios.add(new UsuarioPremium("201", "María López"));
        usuarios.add(new UsuarioPremium("202", "Santiago Herrera"));
        usuarios.add(new UsuarioPremium("203", "Florencia Díaz"));

        return usuarios;
    }

    /**
     * Construye la estación de anclaje con un conjunto inicial de vehículos
     * variados, incluyendo distintos niveles de batería y tarifas para
     * permitir verificar visualmente los criterios de ordenamiento
     * (prioridad de carga y costo base).
     *
     * <p>Uno de los vehículos se inicializa en estado {@code EN_REPARACION}
     * mediante una transición real del patrón State, para poder verificar
     * que el sistema respeta las reglas del ciclo de vida desde el arranque.</p>
     *
     * @return la estación de anclaje inicializada con sus vehículos.
     */
    public EstacionAnclaje inicializarEstacionConVehiculos() {
        EstacionAnclaje estacion = new EstacionAnclaje("Estación Plaza Central");

        BicicletaElectrica bici1 = new BicicletaElectrica("BIC-001", 100, 250.0, 15.5);
        BicicletaElectrica bici2 = new BicicletaElectrica("BIC-002", 10, 250.0, 20.0);
        BicicletaElectrica bici3 = new BicicletaElectrica("BIC-003", 85, 300.0, 10.0);

        Monopatin mono1 = new Monopatin("MON-001", 90, 150.0, true);
        Monopatin mono2 = new Monopatin("MON-002", 5, 150.0, false);
        Monopatin mono3 = new Monopatin("MON-003", 45, 180.0, true);

        estacion.registrarVehiculo(bici1);
        estacion.registrarVehiculo(bici2);
        estacion.registrarVehiculo(bici3);
        estacion.registrarVehiculo(mono1);
        estacion.registrarVehiculo(mono2);
        estacion.registrarVehiculo(mono3);

        // mono3 arranca con fallas: se envía a reparación a través de una
        // transición real del patrón State (no como un String suelto).
        mono3.enviarAReparacion();

        return estacion;
    }
}