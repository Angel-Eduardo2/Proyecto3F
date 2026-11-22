package dao;

import database.ConexionBD;
import exception.DatabaseException;
import factory.UsuarioFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Usuario;

public class UsuarioDAO implements DAO<Usuario, Integer> {

    private final List<Usuario> memoria = new ArrayList<>();

    public UsuarioDAO() {
        memoria.add(UsuarioFactory.crearUsuario("estudiante", 1, 1, "Ana", "Lopez", "ana@mail.com", "3000000000", "2026-09-27", "activo"));
        memoria.add(UsuarioFactory.crearUsuario("empleado", 2, 1, "Juan", "Perez", "juan@mail.com", "3000000001", "2026-09-27", "activo"));
        memoria.add(UsuarioFactory.crearUsuario("visitante", 3, 1, "Luis", "Gomez", "luis@mail.com", "3000000002", "2026-09-27", "activo"));
    }

    @Override
    public void insertar(Usuario u) throws DatabaseException {
        try {
            Connection con = ConexionBD.getConexion();
            String sql = "INSERT INTO usuario (id_usuario, id_sede, nombre, apellido, tipo, correo_electronico, telefono, fecha_registro, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, u.getIdUsuario());
                ps.setInt(2, u.getIdSede());
                ps.setString(3, u.getNombre());
                ps.setString(4, u.getApellido());
                ps.setString(5, u.getTipo());
                ps.setString(6, u.getCorreoElectronico());
                ps.setString(7, u.getTelefono());
                ps.setString(8, u.getFechaRegistro());
                ps.setString(9, u.getEstado());
                ps.executeUpdate();
            }
        } catch (Exception e) {
            boolean existe = false;
            for (int i = 0; i < memoria.size(); i++) {
                if (memoria.get(i).getIdUsuario() == u.getIdUsuario()) {
                    memoria.set(i, u);
                    existe = true;
                    break;
                }
            }
            if (!existe) {
                memoria.add(u);
            }
            return;
        }

        boolean existe = false;
        for (int i = 0; i < memoria.size(); i++) {
            if (memoria.get(i).getIdUsuario() == u.getIdUsuario()) {
                memoria.set(i, u);
                existe = true;
                break;
            }
        }
        if (!existe) {
            memoria.add(u);
        }
    }

    @Override
    public void actualizar(Usuario u) throws DatabaseException {
        try {
            Connection con = ConexionBD.getConexion();
            String sql = "UPDATE usuario SET id_sede=?, nombre=?, apellido=?, tipo=?, correo_electronico=?, telefono=?, fecha_registro=?, estado=? WHERE id_usuario=?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, u.getIdSede());
                ps.setString(2, u.getNombre());
                ps.setString(3, u.getApellido());
                ps.setString(4, u.getTipo());
                ps.setString(5, u.getCorreoElectronico());
                ps.setString(6, u.getTelefono());
                ps.setString(7, u.getFechaRegistro());
                ps.setString(8, u.getEstado());
                ps.setInt(9, u.getIdUsuario());
                ps.executeUpdate();
            }
        } catch (Exception ignored) {
        }

        for (int i = 0; i < memoria.size(); i++) {
            if (memoria.get(i).getIdUsuario() == u.getIdUsuario()) {
                memoria.set(i, u);
                return;
            }
        }
    }

    @Override
    public void eliminar(Integer id) throws DatabaseException {
        try {
            Connection con = ConexionBD.getConexion();
            String sql = "DELETE FROM usuario WHERE id_usuario=?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
        } catch (Exception ignored) {
        }

        memoria.removeIf(u -> u.getIdUsuario() == id);
    }

    @Override
    public Usuario buscarPorId(Integer id) throws DatabaseException {
        try {
            Connection con = ConexionBD.getConexion();
            String sql = "SELECT * FROM usuario WHERE id_usuario=?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return UsuarioFactory.crearUsuario(
                                rs.getString("tipo"),
                                rs.getInt("id_usuario"),
                                rs.getInt("id_sede"),
                                rs.getString("nombre"),
                                rs.getString("apellido"),
                                rs.getString("correo_electronico"),
                                rs.getString("telefono"),
                                rs.getString("fecha_registro"),
                                rs.getString("estado")
                        );
                    }
                }
            }
        } catch (Exception ignored) {
        }

        for (Usuario u : memoria) {
            if (u.getIdUsuario() == id) {
                return u;
            }
        }
        return null;
    }

    @Override
    public List<Usuario> listarTodos() throws DatabaseException {
        List<Usuario> lista = new ArrayList<>();
        try {
            Connection con = ConexionBD.getConexion();
            String sql = "SELECT * FROM usuario ORDER BY id_usuario";
            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    lista.add(UsuarioFactory.crearUsuario(
                            rs.getString("tipo"),
                            rs.getInt("id_usuario"),
                            rs.getInt("id_sede"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("correo_electronico"),
                            rs.getString("telefono"),
                            rs.getString("fecha_registro"),
                            rs.getString("estado")
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
