package ar.edu.unlar.programacion3.parcial1.DataConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.edu.unlar.programacion3.parcial1.model.BicicletaElectrica;
import ar.edu.unlar.programacion3.parcial1.model.EstacionAnclaje;
import ar.edu.unlar.programacion3.parcial1.model.Monopatin;
import ar.edu.unlar.programacion3.parcial1.model.Usuario;
import ar.edu.unlar.programacion3.parcial1.model.UsuarioPremium;
import ar.edu.unlar.programacion3.parcial1.model.Vehiculo;

public class Config {

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

    public EstacionAnclaje inicializarEstacionConVehiculos() {
        
        EstacionAnclaje estacion = new EstacionAnclaje("Estación Plaza Central", new HashMap<>());

        Map<Vehiculo, String> vehiculosEstacion = estacion.getListadoVehiculo();

        BicicletaElectrica bici1 = new BicicletaElectrica("BIC-001", 100, 250.0, 15.5);
        BicicletaElectrica bici2 = new BicicletaElectrica("BIC-002", 10, 250.0, 20.0);
        BicicletaElectrica bici3 = new BicicletaElectrica("BIC-003", 85, 300.0, 10.0);

        Monopatin mono1 = new Monopatin("MON-001", 90, 150.0, true);
        Monopatin mono2 = new Monopatin("MON-002", 5, 150.0, false);
        Monopatin mono3 = new Monopatin("MON-003", 45, 180.0, true);

        vehiculosEstacion.put(bici1, "DISPONIBLE");
        vehiculosEstacion.put(bici2, "DISPONIBLE");
        vehiculosEstacion.put(bici3, "DISPONIBLE");
        vehiculosEstacion.put(mono1, "DISPONIBLE");
        vehiculosEstacion.put(mono2, "DISPONIBLE");
        vehiculosEstacion.put(mono3, "EN_MANTENIMIENTO");

        return estacion;
    }
}
