import facade.LockSecureFacade;
import factory.FranquiciaFactory;
import factory.LockerFactory;
import factory.ModeloAlquilerFactory;
import factory.UsuarioFactory;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;
import model.Administrador;
import model.AdministradorCentral;
import model.AdministradorLocal;
import model.Franquicia;
import model.Locker;
import model.SesionDeUso;
import model.Usuario;
import strategy.AlquilerEvento;
import strategy.AlquilerMensual;
import strategy.AlquilerPorTiempo;
import strategy.AlquilerPorUso;
import strategy.ModeloAlquiler;
import view.VentanaPrincipal;

public class Main {

    public static void main(String[] args) {
        boolean modoConsola = false;
        for (String arg : args) {
            if (arg.equalsIgnoreCase("--console") || arg.equalsIgnoreCase("-c")) {
                modoConsola = true;
                break;
            }
        }

        if (!modoConsola && !GraphicsEnvironment.isHeadless()) {
            try {
                SwingUtilities.invokeLater(() -> {
                    VentanaPrincipal ventana = new VentanaPrincipal();
                    ventana.setVisible(true);
                });
                return;
            } catch (Exception ignored) {
            }
        }

        ejecutarFlujoConsola();
    }

    public static void ejecutarFlujoConsola() {
        System.out.println("=== LOCKSECURE - DEMOSTRACION EN CONSOLA ===");

        ArrayList<Usuario> usuarios = new ArrayList<>();
        usuarios.add(UsuarioFactory.crearUsuario("estudiante", 1, 1, "Ana", "Lopez", "ana@mail.com", "3000000000", "2026-09-27", "activo"));
        usuarios.add(UsuarioFactory.crearUsuario("empleado", 2, 1, "Juan", "Perez", "juan@mail.com", "3000000001", "2026-09-27", "activo"));
        usuarios.add(UsuarioFactory.crearUsuario("visitante", 3, 1, "Luis", "Gomez", "luis@mail.com", "3000000002", "2026-09-27", "activo"));

        System.out.println("\nTARIFAS (POLIMORFISMO DE USUARIOS):");
        for (Usuario u : usuarios) {
            System.out.println(u.getTipo() + ": $" + u.calcularTarifa(10000));
        }

        ArrayList<Administrador> admins = new ArrayList<>();
        admins.add(new AdministradorLocal(1, 1, "Carlos", "Diaz", "carlos@mail.com", "1234", "admin_local", "2026-09-27"));
        admins.add(new AdministradorCentral(2, 1, "Maria", "Rojas", "maria@mail.com", "1234", "admin_central", "2026-09-27"));

        System.out.println("\nPANELES DE ADMINISTRACION:");
        for (Administrador a : admins) {
            a.accederPanelDeControl();
        }

        ArrayList<Locker> lockers = new ArrayList<>();
        lockers.add(LockerFactory.crearLocker("estandar", "L001", 1, "disponible", "2026-09-01", 5));
        lockers.add(LockerFactory.crearLocker("refrigerado", "L002", 1, "averiado", "2026-09-05", 10));
        lockers.add(LockerFactory.crearLocker("grande", "L003", 1, "disponible", "2026-09-10", 2));

        System.out.println("\nMANTENIMIENTO DE LOCKERS:");
        for (Locker l : lockers) {
            l.verificarMantenimiento();
        }

        ArrayList<ModeloAlquiler> modelos = new ArrayList<>();
        modelos.add(ModeloAlquilerFactory.crearModelo("tiempo"));
        modelos.add(ModeloAlquilerFactory.crearModelo("uso"));
        modelos.add(ModeloAlquilerFactory.crearModelo("mensual"));
        modelos.add(ModeloAlquilerFactory.crearModelo("evento"));

        System.out.println("\nCOSTOS POR MODELO DE ALQUILER (PATRON STRATEGY):");
        for (ModeloAlquiler m : modelos) {
            System.out.println(m.getNombreEstrategia() + ": $" + m.calcularCosto(10000));
        }

        System.out.println("\nPRUEBA DE INTEGRACION CON FACADE Y PERSISTENCIA:");
        try {
            LockSecureFacade facade = new LockSecureFacade();
            facade.registrarUsuario(4, 1, "Pedro", "Torres", "estudiante", "pedro@mail.com", "3000000003", "2026-09-27", "activo");
            facade.registrarLocker("L004", 1, "estandar", "disponible", "2026-09-01", 0);

            SesionDeUso sesion = facade.iniciarSesion(4, "L004", "tiempo", "PIN", 10000);
            System.out.println("Sesion iniciada con ID: " + sesion.getIdSesion() + " | Costo calculado: $" + sesion.getCostoTotal());

            facade.terminarSesion(sesion.getIdSesion(), false);
            System.out.println("Sesion terminada y locker liberado exitosamente.");

            List<Usuario> listaU = facade.listarUsuarios();
            System.out.println("Total usuarios registrados: " + listaU.size());

            List<Locker> listaL = facade.listarLockers();
            System.out.println("Total lockers registrados: " + listaL.size());
        } catch (Exception e) {
            System.out.println("Error en flujo Facade: " + e.getMessage());
        }

        Franquicia fra = FranquiciaFactory.crearFranquicia(
                "academica",
                1,
                "Universidad Libre",
                "Calle 53 # 10-20",
                "3000000000",
                "contacto@mail.com",
                "autogestionado",
                "2026-09-01",
                "activo",
                1000000
        );

        System.out.println("\nFRANQUICIA:");
        System.out.println("Cuota Mensual: $" + fra.calcularCuotaMensual());
        System.out.println("\nLOCKSECURE funcionando correctamente.");
    }
}
