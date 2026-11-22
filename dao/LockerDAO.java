package dao;

import database.ConexionBD;
import exception.DatabaseException;
import factory.LockerFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Locker;

public class LockerDAO implements DAO<Locker, String> {

    private final List<Locker> memoria = new ArrayList<>();

    public LockerDAO() {
        memoria.add(LockerFactory.crearLocker("estandar", "L001", 1, "disponible", "2026-09-01", 5));
        memoria.add(LockerFactory.crearLocker("refrigerado", "L002", 1, "averiado", "2026-09-05", 10));
        memoria.add(LockerFactory.crearLocker("grande", "L003", 1, "disponible", "2026-09-10", 2));
    }

    @Override
    public void insertar(Locker l) throws DatabaseException {
        try {
            Connection con = ConexionBD.getConexion();
            String sql = "INSERT INTO locker (codigo_identificador, id_sede, tipo, estado, fecha_ultimo_mantenimiento, cantidad_veces_usado) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, l.getCodigoIdentificador());
                ps.setInt(2, l.getIdSede());
                ps.setString(3, l.getTipo());
                ps.setString(4, l.getEstado());
                ps.setString(5, l.getFechaUltimoMantenimiento());
                ps.setInt(6, l.getCantidadVecesUsado());
                ps.executeUpdate();
            }
        } catch (Exception e) {
            boolean existe = false;
            for (int i = 0; i < memoria.size(); i++) {
                if (memoria.get(i).getCodigoIdentificador().equalsIgnoreCase(l.getCodigoIdentificador())) {
                    memoria.set(i, l);
                    existe = true;
                    break;
                }
            }
            if (!existe) {
                memoria.add(l);
            }
            return;
        }

        boolean existe = false;
        for (int i = 0; i < memoria.size(); i++) {
            if (memoria.get(i).getCodigoIdentificador().equalsIgnoreCase(l.getCodigoIdentificador())) {
                memoria.set(i, l);
                existe = true;
                break;
            }
        }
        if (!existe) {
            memoria.add(l);
        }
    }

    @Override
    public void actualizar(Locker l) throws DatabaseException {
        try {
            Connection con = ConexionBD.getConexion();
            String sql = "UPDATE locker SET id_sede=?, tipo=?, estado=?, fecha_ultimo_mantenimiento=?, cantidad_veces_usado=? WHERE codigo_identificador=?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, l.getIdSede());
                ps.setString(2, l.getTipo());
                ps.setString(3, l.getEstado());
                ps.setString(4, l.getFechaUltimoMantenimiento());
                ps.setInt(5, l.getCantidadVecesUsado());
                ps.setString(6, l.getCodigoIdentificador());
                ps.executeUpdate();
            }
        } catch (Exception ignored) {
        }

        for (int i = 0; i < memoria.size(); i++) {
            if (memoria.get(i).getCodigoIdentificador().equalsIgnoreCase(l.getCodigoIdentificador())) {
                memoria.set(i, l);
                return;
            }
        }
    }

    @Override
    public void eliminar(String codigo) throws DatabaseException {
        try {
            Connection con = ConexionBD.getConexion();
            String sql = "DELETE FROM locker WHERE codigo_identificador=?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, codigo);
                ps.executeUpdate();
            }
        } catch (Exception ignored) {
        }

        memoria.removeIf(l -> l.getCodigoIdentificador().equalsIgnoreCase(codigo));
    }

    @Override
    public Locker buscarPorId(String codigo) throws DatabaseException {
        try {
            Connection con = ConexionBD.getConexion();
            String sql = "SELECT * FROM locker WHERE codigo_identificador=?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, codigo);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return LockerFactory.crearLocker(
                                rs.getString("tipo"),
                                rs.getString("codigo_identificador"),
                                rs.getInt("id_sede"),
                                rs.getString("estado"),
                                rs.getString("fecha_ultimo_mantenimiento"),
                                rs.getInt("cantidad_veces_usado")
                        );
                    }
                }
            }
        } catch (Exception ignored) {
        }

        for (Locker l : memoria) {
            if (l.getCodigoIdentificador().equalsIgnoreCase(codigo)) {
                return l;
            }
        }
        return null;
    }

    @Override
    public List<Locker> listarTodos() throws DatabaseException {
        List<Locker> lista = new ArrayList<>();
        try {
            Connection con = ConexionBD.getConexion();
            String sql = "SELECT * FROM locker ORDER BY codigo_identificador";
            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    lista.add(LockerFactory.crearLocker(
                            rs.getString("tipo"),
                            rs.getString("codigo_identificador"),
                            rs.getInt("id_sede"),
                            rs.getString("estado"),
                            rs.getString("fecha_ultimo_mantenimiento"),
                            rs.getInt("cantidad_veces_usado")
                    ));
                }
            }
            if (!lista.isEmpty()) {
                return lista;
            }
        } catch (Exception ignored) {
        }

        return new ArrayList<>(memoria);
    }
}
