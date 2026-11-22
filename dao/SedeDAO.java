package dao;

import database.ConexionBD;
import exception.DatabaseException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Sede;

public class SedeDAO implements DAO<Sede, Integer> {

    private final List<Sede> memoria = new ArrayList<>();

    public SedeDAO() {
        memoria.add(new Sede(1, 1, "Sede Principal Candelaria", "Carrera 6 # 8-06", "Bogota", 50, "2026-09-01", "Carlos Diaz"));
    }

    @Override
    public void insertar(Sede s) throws DatabaseException {
        try {
            Connection con = ConexionBD.getConexion();
            String sql = "INSERT INTO sede (id_sede, id_franquicia, nombre, direccion, ciudad, capacidad_total_lockers, fecha_apertura, administrador_sede) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, s.getIdSede());
                ps.setInt(2, s.getIdFranquicia());
                ps.setString(3, s.getNombre());
                ps.setString(4, s.getDireccion());
                ps.setString(5, s.getCiudad());
                ps.setInt(6, s.getCapacidadTotalLockers());
                ps.setString(7, s.getFechaApertura());
                ps.setString(8, s.getAdministradorSede());
                ps.executeUpdate();
            }
        } catch (Exception e) {
            boolean existe = false;
            for (int i = 0; i < memoria.size(); i++) {
                if (memoria.get(i).getIdSede() == s.getIdSede()) {
                    memoria.set(i, s);
                    existe = true;
                    break;
                }
            }
            if (!existe) {
                memoria.add(s);
            }
            return;
        }

        boolean existe = false;
        for (int i = 0; i < memoria.size(); i++) {
            if (memoria.get(i).getIdSede() == s.getIdSede()) {
                memoria.set(i, s);
                existe = true;
                break;
            }
        }
        if (!existe) {
            memoria.add(s);
        }
    }

    @Override
    public void actualizar(Sede s) throws DatabaseException {
        try {
            Connection con = ConexionBD.getConexion();
            String sql = "UPDATE sede SET id_franquicia=?, nombre=?, direccion=?, ciudad=?, capacidad_total_lockers=?, fecha_apertura=?, administrador_sede=? WHERE id_sede=?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, s.getIdFranquicia());
                ps.setString(2, s.getNombre());
                ps.setString(3, s.getDireccion());
                ps.setString(4, s.getCiudad());
                ps.setInt(5, s.getCapacidadTotalLockers());
                ps.setString(6, s.getFechaApertura());
                ps.setString(7, s.getAdministradorSede());
                ps.setInt(8, s.getIdSede());
                ps.executeUpdate();
            }
        } catch (Exception ignored) {
        }

        for (int i = 0; i < memoria.size(); i++) {
            if (memoria.get(i).getIdSede() == s.getIdSede()) {
                memoria.set(i, s);
                return;
            }
        }
    }

    @Override
    public void eliminar(Integer id) throws DatabaseException {
        try {
            Connection con = ConexionBD.getConexion();
            String sql = "DELETE FROM sede WHERE id_sede=?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
        } catch (Exception ignored) {
        }

        memoria.removeIf(s -> s.getIdSede() == id);
    }

    @Override
    public Sede buscarPorId(Integer id) throws DatabaseException {
        try {
            Connection con = ConexionBD.getConexion();
            String sql = "SELECT * FROM sede WHERE id_sede=?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new Sede(
                                rs.getInt("id_sede"),
                                rs.getInt("id_franquicia"),
                                rs.getString("nombre"),
                                rs.getString("direccion"),
                                rs.getString("ciudad"),
                                rs.getInt("capacidad_total_lockers"),
                                rs.getString("fecha_apertura"),
                                rs.getString("administrador_sede")
                        );
                    }
                }
            }
        } catch (Exception ignored) {
        }

        for (Sede s : memoria) {
            if (s.getIdSede() == id) {
                return s;
            }
        }
        return null;
    }

    @Override
    public List<Sede> listarTodos() throws DatabaseException {
        List<Sede> lista = new ArrayList<>();
        try {
            Connection con = ConexionBD.getConexion();
            String sql = "SELECT * FROM sede ORDER BY id_sede";
            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    lista.add(new Sede(
                            rs.getInt("id_sede"),
                            rs.getInt("id_franquicia"),
                            rs.getString("nombre"),
                            rs.getString("direccion"),
                            rs.getString("ciudad"),
                            rs.getInt("capacidad_total_lockers"),
                            rs.getString("fecha_apertura"),
                            rs.getString("administrador_sede")
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
