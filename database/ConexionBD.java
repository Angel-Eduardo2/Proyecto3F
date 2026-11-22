package database;

import exception.DatabaseException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionBD {

    private static String url = "jdbc:sqlite:locksecure.db";
    private static String usuario = "";
    private static String contrasena = "";
    private static Connection conexion = null;

    public static void configurar(String nuevaUrl, String nuevoUsuario, String nuevaContrasena) {
        url = nuevaUrl;
        usuario = nuevoUsuario;
        contrasena = nuevaContrasena;
        cerrarConexion();
    }

    public static synchronized Connection getConexion() throws DatabaseException {
        try {
            if (conexion == null || conexion.isClosed()) {
                if (url.startsWith("jdbc:sqlite:")) {
                    try {
                        Class.forName("org.sqlite.JDBC");
                    } catch (ClassNotFoundException ignored) {
                    }
                } else if (url.startsWith("jdbc:mysql:")) {
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                    } catch (ClassNotFoundException ignored) {
                    }
                }

                if (usuario == null || usuario.isEmpty()) {
                    conexion = DriverManager.getConnection(url);
                } else {
                    conexion = DriverManager.getConnection(url, usuario, contrasena);
                }
                crearTablasSiNoExisten(conexion);
            }
            return conexion;
        } catch (SQLException e) {
            throw new DatabaseException("Error al conectar con la base de datos: " + e.getMessage(), e);
        }
    }

    private static void crearTablasSiNoExisten(Connection con) {
        try (Statement st = con.createStatement()) {
            st.execute("CREATE TABLE IF NOT EXISTS franquicia (" +
                    "id_franquicia INTEGER PRIMARY KEY, " +
                    "nombre TEXT NOT NULL, " +
                    "tipo TEXT NOT NULL, " +
                    "direccion TEXT, " +
                    "telefono TEXT, " +
                    "correo_contacto TEXT, " +
                    "modo_operacion TEXT, " +
                    "fecha_alta TEXT, " +
                    "estado TEXT NOT NULL, " +
                    "cuota_mensual REAL NOT NULL)");

            st.execute("CREATE TABLE IF NOT EXISTS sede (" +
                    "id_sede INTEGER PRIMARY KEY, " +
                    "id_franquicia INTEGER NOT NULL, " +
                    "nombre TEXT NOT NULL, " +
                    "direccion TEXT, " +
                    "ciudad TEXT, " +
                    "capacidad_total_lockers INTEGER NOT NULL, " +
                    "fecha_apertura TEXT, " +
                    "administrador_sede TEXT, " +
                    "FOREIGN KEY (id_franquicia) REFERENCES franquicia(id_franquicia))");

            st.execute("CREATE TABLE IF NOT EXISTS usuario (" +
                    "id_usuario INTEGER PRIMARY KEY, " +
                    "id_sede INTEGER NOT NULL, " +
                    "nombre TEXT NOT NULL, " +
                    "apellido TEXT NOT NULL, " +
                    "tipo TEXT NOT NULL, " +
                    "correo_electronico TEXT NOT NULL, " +
                    "telefono TEXT, " +
                    "fecha_registro TEXT, " +
                    "estado TEXT NOT NULL, " +
                    "FOREIGN KEY (id_sede) REFERENCES sede(id_sede))");

            st.execute("CREATE TABLE IF NOT EXISTS locker (" +
                    "codigo_identificador TEXT PRIMARY KEY, " +
                    "id_sede INTEGER NOT NULL, " +
                    "tipo TEXT NOT NULL, " +
                    "estado TEXT NOT NULL, " +
                    "fecha_ultimo_mantenimiento TEXT, " +
                    "cantidad_veces_usado INTEGER NOT NULL, " +
                    "FOREIGN KEY (id_sede) REFERENCES sede(id_sede))");

            st.execute("CREATE TABLE IF NOT EXISTS sesion_de_uso (" +
                    "id_sesion INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "id_usuario INTEGER NOT NULL, " +
                    "codigo_identificador TEXT NOT NULL, " +
                    "fecha_inicio TEXT NOT NULL, " +
                    "fecha_fin TEXT, " +
                    "duracion_minutos INTEGER, " +
                    "metodo_apertura TEXT, " +
                    "hubo_incidencias INTEGER, " +
                    "costo_total REAL, " +
                    "FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario), " +
                    "FOREIGN KEY (codigo_identificador) REFERENCES locker(codigo_identificador))");
        } catch (SQLException ignored) {
        }
    }

    public static void cerrar(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ignored) {
            }
        }
    }

    public static void cerrar(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException ignored) {
            }
        }
    }

    public static void cerrar(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ignored) {
            }
        }
    }

    public static void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
            } catch (SQLException ignored) {
            }
            conexion = null;
        }
    }
}
