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
import model.SesionDeUso;

public class SesionDeUsoDAO implements DAO<SesionDeUso, Integer> {

    private final List<SesionDeUso> memoria = new ArrayList<>();
    private int consecutivo = 1;

    public SesionDeUsoDAO() {
    }

    @Override
    public void insertar(SesionDeUso s) throws DatabaseException {
        try {
            Connection con = ConexionBD.getConexion();
            String sql = "INSERT INTO sesion_de_uso (id_usuario, codigo_identificador, fecha_inicio, fecha_fin, duracion_minutos, metodo_apertura, hubo_incidencias, costo_total) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, s.getIdUsuario());
                ps.setString(2, s.getCodigoIdentificador());
                ps.setString(3, s.getFechaInicio());
                ps.setString(4, s.getFechaFin());
                ps.setInt(5, s.getDuracionMinutos());
                ps.setString(6, s.getMetodoApertura());
                ps.setInt(7, s.isHuboIncidencias() ? 1 : 0);
                ps.setDouble(8, s.getCostoTotal());
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        s.setIdSesion(rs.getInt(1));
                    }
                }
            }
        } catch (Exception e) {
            if (s.getIdSesion() <= 0) {
                s.setIdSesion(consecutivo++);
            }
            memoria.add(s);
            return;
        }

        if (s.getIdSesion() <= 0) {
            s.setIdSesion(consecutivo++);
        }
        memoria.add(s);
    }

    @Override
    public void actualizar(SesionDeUso s) throws DatabaseException {
        try {
            Connection con = ConexionBD.getConexion();
            String sql = "UPDATE sesion_de_uso SET id_usuario=?, codigo_identificador=?, fecha_inicio=?, fecha_fin=?, duracion_minutos=?, metodo_apertura=?, hubo_incidencias=?, costo_total=? WHERE id_sesion=?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, s.getIdUsuario());
                ps.setString(2, s.getCodigoIdentificador());
                ps.setString(3, s.getFechaInicio());
                ps.setString(4, s.getFechaFin());
                ps.setInt(5, s.getDuracionMinutos());
                ps.setString(6, s.getMetodoApertura());
                ps.setInt(7, s.isHuboIncidencias() ? 1 : 0);
                ps.setDouble(8, s.getCostoTotal());
                ps.setInt(9, s.getIdSesion());
                ps.executeUpdate();
            }
        } catch (Exception ignored) {
        }

        for (int i = 0; i < memoria.size(); i++) {
            if (memoria.get(i).getIdSesion() == s.getIdSesion()) {
                memoria.set(i, s);
                return;
            }
        }
    }

    @Override
    public void eliminar(Integer id) throws DatabaseException {
        try {
            Connection con = ConexionBD.getConexion();
            String sql = "DELETE FROM sesion_de_uso WHERE id_sesion=?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
        } catch (Exception ignored) {
        }

        memoria.removeIf(s -> s.getIdSesion() == id);
    }

    @Override
    public SesionDeUso buscarPorId(Integer id) throws DatabaseException {
        try {
            Connection con = ConexionBD.getConexion();
            String sql = "SELECT * FROM sesion_de_uso WHERE id_sesion=?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new SesionDeUso(
                                rs.getInt("id_sesion"),
                                rs.getInt("id_usuario"),
                                rs.getString("codigo_identificador"),
                                rs.getString("fecha_inicio"),
                                rs.getString("fecha_fin"),
                                rs.getInt("duracion_minutos"),
                                rs.getString("metodo_apertura"),
                                rs.getInt("hubo_incidencias") == 1,
                                rs.getDouble("costo_total")
                        );
                    }
                }
            }
        } catch (Exception ignored) {
        }

        for (SesionDeUso s : memoria) {
            if (s.getIdSesion() == id) {
                return s;
            }
        }
        return null;
    }

    @Override
    public List<SesionDeUso> listarTodos() throws DatabaseException {
        List<SesionDeUso> lista = new ArrayList<>();
        try {
            Connection con = ConexionBD.getConexion();
            String sql = "SELECT * FROM sesion_de_uso ORDER BY id_sesion DESC";
            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    lista.add(new SesionDeUso(
                            rs.getInt("id_sesion"),
                            rs.getInt("id_usuario"),
                            rs.getString("codigo_identificador"),
                            rs.getString("fecha_inicio"),
                            rs.getString("fecha_fin"),
                            rs.getInt("duracion_minutos"),
                            rs.getString("metodo_apertura"),
                            rs.getInt("hubo_incidencias") == 1,
                            rs.getDouble("costo_total")
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
