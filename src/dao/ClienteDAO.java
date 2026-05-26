package dao;

import model.Cliente;
import util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestiona todas las operaciones CRUD de la tabla 'clientes' contra MySQL.
 * Usa PreparedStatement en todas las consultas para evitar inyección SQL.
 */
public class ClienteDAO {

    /**
     * Inserta un nuevo cliente en la base de datos.
     * @param cliente objeto Cliente con los datos a insertar
     * @return true si se insertó correctamente, false en caso contrario
     */
    public boolean insertar(Cliente cliente) {
        String sql = "INSERT INTO clientes (dni, nombre, edad, departamento) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setString(1, cliente.getDni());
            ps.setString(2, cliente.getNombre());
            ps.setInt(3, cliente.getEdad());
            ps.setString(4, cliente.getDepartamento());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar cliente: " + e.getMessage());
            return false;
        }
    }

    /**
     * Devuelve todos los clientes almacenados en la base de datos.
     * @return lista de objetos Cliente
     */
    public List<Cliente> listar() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes ORDER BY nombre";
        try (Statement st = ConexionBD.getConexion().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar clientes: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Busca un cliente por su DNI.
     * @param dni DNI del cliente a buscar
     * @return objeto Cliente si se encuentra, null en caso contrario
     */
    public Cliente buscarPorDni(String dni) {
        String sql = "SELECT * FROM clientes WHERE dni = ?";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar cliente: " + e.getMessage());
        }
        return null;
    }

    /**
     * Busca un cliente por su ID.
     * @param id ID del cliente
     * @return objeto Cliente si se encuentra, null en caso contrario
     */
    public Cliente buscarPorId(int id) {
        String sql = "SELECT * FROM clientes WHERE id = ?";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar cliente por id: " + e.getMessage());
        }
        return null;
    }

    /**
     * Actualiza los datos de un cliente existente.
     * @param cliente objeto Cliente con los nuevos datos (debe tener el ID asignado)
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean actualizar(Cliente cliente) {
        String sql = "UPDATE clientes SET dni=?, nombre=?, edad=?, departamento=? WHERE id=?";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setString(1, cliente.getDni());
            ps.setString(2, cliente.getNombre());
            ps.setInt(3, cliente.getEdad());
            ps.setString(4, cliente.getDepartamento());
            ps.setInt(5, cliente.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar cliente: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un cliente por su ID. Sus citas se eliminan en cascada por la BD.
     * @param id ID del cliente a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM clientes WHERE id=?";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar cliente: " + e.getMessage());
            return false;
        }
    }

    /**
     * Convierte una fila del ResultSet en un objeto Cliente.
     */
    private Cliente mapear(ResultSet rs) throws SQLException {
        return new Cliente(
            rs.getInt("id"),
            rs.getString("dni"),
            rs.getString("nombre"),
            rs.getInt("edad"),
            rs.getString("departamento")
        );
    }
}
