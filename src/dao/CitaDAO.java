package dao;

import model.Cita;
import util.ConexionBD;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestiona todas las operaciones CRUD de la tabla 'citas' contra MySQL.
 */
public class CitaDAO {

    /**
     * Inserta una nueva cita en la base de datos.
     * @param cita objeto Cita con los datos a insertar
     * @return true si se insertó correctamente
     */
    public boolean insertar(Cita cita) {
        String sql = "INSERT INTO citas (cliente_id, fecha, descripcion) VALUES (?, ?, ?)";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setInt(1, cita.getClienteId());
            ps.setTimestamp(2, Timestamp.valueOf(cita.getFecha()));
            ps.setString(3, cita.getDescripcion());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar cita: " + e.getMessage());
            return false;
        }
    }

    /**
     * Devuelve todas las citas, incluyendo el nombre del cliente asociado.
     * @return lista de objetos Cita
     */
    public List<Cita> listar() {
        List<Cita> lista = new ArrayList<>();
        String sql = "SELECT c.*, cl.nombre AS cliente_nombre " +
                     "FROM citas c JOIN clientes cl ON c.cliente_id = cl.id " +
                     "ORDER BY c.fecha";
        try (Statement st = ConexionBD.getConexion().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Cita cita = mapear(rs);
                cita.setClienteNombre(rs.getString("cliente_nombre"));
                lista.add(cita);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar citas: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Devuelve todas las citas de un cliente concreto.
     * @param clienteId ID del cliente
     * @return lista de citas del cliente
     */
    public List<Cita> listarPorCliente(int clienteId) {
        List<Cita> lista = new ArrayList<>();
        String sql = "SELECT * FROM citas WHERE cliente_id = ? ORDER BY fecha";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setInt(1, clienteId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar citas por cliente: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Actualiza los datos de una cita existente.
     * @param cita objeto Cita con los nuevos datos
     * @return true si se actualizó correctamente
     */
    public boolean actualizar(Cita cita) {
        String sql = "UPDATE citas SET cliente_id=?, fecha=?, descripcion=? WHERE id=?";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setInt(1, cita.getClienteId());
            ps.setTimestamp(2, Timestamp.valueOf(cita.getFecha()));
            ps.setString(3, cita.getDescripcion());
            ps.setInt(4, cita.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar cita: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina una cita por su ID.
     * @param id ID de la cita a eliminar
     * @return true si se eliminó correctamente
     */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM citas WHERE id=?";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar cita: " + e.getMessage());
            return false;
        }
    }

    /**
     * Convierte una fila del ResultSet en un objeto Cita.
     */
    private Cita mapear(ResultSet rs) throws SQLException {
        LocalDateTime fecha = rs.getTimestamp("fecha").toLocalDateTime();
        return new Cita(
            rs.getInt("id"),
            rs.getInt("cliente_id"),
            fecha,
            rs.getString("descripcion")
        );
    }
}
