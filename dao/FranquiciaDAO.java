package dao;

import database.ConexionBD;
import exception.DatabaseException;
import factory.FranquiciaFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Franquicia;

public class FranquiciaDAO implements DAO<Franquicia, Integer> {

    private final List<Franquicia> memoria = new ArrayList<>();

    public FranquiciaDAO() {
        memoria.add(FranquiciaFactory.crearFranquicia("academica", 1, "Universidad Libre", "Calle 53 # 10-20", "3000000000", "contacto@unilibre.edu.co", "autogestionado", "2026-09-01", "activo", 1000000.0));
    }

    @Override
    public void insertar(Franquicia f) throws DatabaseException {
        try {
            Connection con = ConexionBD.getConexion();
            String sql = "INSERT INTO franquicia (id_franquicia, nombre, tipo, direccion, telefono, correo_contacto, modo_operacion, fecha_alta, estado, cuota_mensual) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, f.getIdFranquicia());
                ps.setString(2, f.getNombre());
                ps.setString(3, f.getTipo());
                ps.setString(4, f.getDireccion());
                ps.setString(5, f.getTelefono());
                ps.setString(6, f.getCorreoContacto());
                ps.setString(7, f.getModoOperacion());
                ps.setString(8, f.getFechaAlta());
                ps.setString(9, f.getEstado());
                ps.setDouble(10, f.getCuotaMensual());
                ps.executeUpdate();
            }
        } catch (Exception e) {
            boolean existe = false;
            for (int i = 0; i < memoria.size(); i++) {
                if (memoria.get(i).getIdFranquicia() == f.getIdFranquicia()) {
                    memoria.set(i, f);
                    existe = true;
                    break;
                }
            }
            if (!existe) {
                memoria.add(f);
            }
            return;
        }

        boolean existe = false;
        for (int i = 0; i < memoria.size(); i++) {
            if (memoria.get(i).getIdFranquicia() == f.getIdFranquicia()) {
                memoria.set(i, f);
                existe = true;
                break;
            }
        }
        if (!existe) {
            memoria.add(f);
        }
    }

    @Override
    public void actualizar(Franquicia f) throws DatabaseException {
        try {
            Connection con = ConexionBD.getConexion();
            String sql = "UPDATE franquicia SET nombre=?, tipo=?, direccion=?, telefono=?, correo_contacto=?, modo_operacion=?, fecha_alta=?, estado=?, cuota_mensual=? WHERE id_franquicia=?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, f.getNombre());
                ps.setString(2, f.getTipo());
                ps.setString(3, f.getDireccion());
                ps.setString(4, f.getTelefono());
                ps.setString(5, f.getCorreoContacto());
                ps.setString(6, f.getModoOperacion());
                ps.setString(7, f.getFechaAlta());
                ps.setString(8, f.getEstado());
                ps.setDouble(9, f.getCuotaMensual());
                ps.setInt(10, f.getIdFranquicia());
                ps.executeUpdate();
            }
        } catch (Exception ignored) {
        }

        for (int i = 0; i < memoria.size(); i++) {
            if (memoria.get(i).getIdFranquicia() == f.getIdFranquicia()) {
                memoria.set(i, f);
                return;
            }
        }
    }

    @Override
    public void eliminar(Integer id) throws DatabaseException {
        try {
            Connection con = ConexionBD.getConexion();
            String sql = "DELETE FROM franquicia WHERE id_franquicia=?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
        } catch (Exception ignored) {
        }

        memoria.removeIf(f -> f.getIdFranquicia() == id);
    }

    @Override
    public Franquicia buscarPorId(Integer id) throws DatabaseException {
        try {
            Connection con = ConexionBD.getConexion();
            String sql = "SELECT * FROM franquicia WHERE id_franquicia=?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return FranquiciaFactory.crearFranquicia(
                                rs.getString("tipo"),
                                rs.getInt("id_franquicia"),
                                rs.getString("nombre"),
                                rs.getString("direccion"),
                                rs.getString("telefono"),
                                rs.getString("correo_contacto"),
                                rs.getString("modo_operacion"),
                                rs.getString("fecha_alta"),
                                rs.getString("estado"),
                                rs.getDouble("cuota_mensual")
                        );
                    }
                }
            }
        } catch (Exception ignored) {
        }

        for (Franquicia f : memoria) {
            if (f.getIdFranquicia() == id) {
                return f;
            }
        }
        return null;
    }

    @Override
    public List<Franquicia> listarTodos() throws DatabaseException {
        List<Franquicia> lista = new ArrayList<>();
        try {
            Connection con = ConexionBD.getConexion();
            String sql = "SELECT * FROM franquicia ORDER BY id_franquicia";
            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    lista.add(FranquiciaFactory.crearFranquicia(
                            rs.getString("tipo"),
                            rs.getInt("id_franquicia"),
                            rs.getString("nombre"),
                            rs.getString("direccion"),
                            rs.getString("telefono"),
                            rs.getString("correo_contacto"),
                            rs.getString("modo_operacion"),
                            rs.getString("fecha_alta"),
                            rs.getString("estado"),
                            rs.getDouble("cuota_mensual")
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
