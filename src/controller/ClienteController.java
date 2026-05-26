package controller;

import dao.ClienteDAO;
import model.Cliente;

import java.util.List;

/**
 * Contiene la lógica de negocio relacionada con los clientes.
 * Actúa como intermediario entre la interfaz gráfica (Swing) y el DAO.
 * Las validaciones de datos se realizan aquí, no en la vista ni en el DAO.
 */
public class ClienteController {

    private final ClienteDAO dao = new ClienteDAO();

    /**
     * Añade un nuevo cliente tras validar los datos de entrada.
     * @return mensaje de resultado para mostrar al usuario
     */
    public String añadir(String dni, String nombre, String edadStr, String departamento) {
        String error = validar(dni, nombre, edadStr, departamento);
        if (error != null) return error;

        if (dao.buscarPorDni(dni) != null) {
            return "Ya existe un cliente con el DNI " + dni + ".";
        }

        Cliente c = new Cliente(dni, nombre, Integer.parseInt(edadStr), departamento);
        return dao.insertar(c) ? "Cliente añadido correctamente." : "Error al añadir el cliente.";
    }

    /**
     * Actualiza los datos de un cliente existente.
     * @param id ID del cliente a modificar
     * @return mensaje de resultado
     */
    public String editar(int id, String dni, String nombre, String edadStr, String departamento) {
        String error = validar(dni, nombre, edadStr, departamento);
        if (error != null) return error;

        // Comprobar que el DNI no pertenece a otro cliente distinto
        Cliente existente = dao.buscarPorDni(dni);
        if (existente != null && existente.getId() != id) {
            return "El DNI " + dni + " ya está en uso por otro cliente.";
        }

        Cliente c = new Cliente(id, dni, nombre, Integer.parseInt(edadStr), departamento);
        return dao.actualizar(c) ? "Cliente actualizado correctamente." : "Error al actualizar el cliente.";
    }

    /**
     * Elimina un cliente por su ID.
     * @return mensaje de resultado
     */
    public String eliminar(int id) {
        return dao.eliminar(id) ? "Cliente eliminado correctamente." : "Error al eliminar el cliente.";
    }

    /**
     * Devuelve todos los clientes para mostrarlos en la tabla.
     */
    public List<Cliente> listar() {
        return dao.listar();
    }

    /**
     * Valida que los campos obligatorios no estén vacíos y que la edad sea un número válido.
     * @return mensaje de error, o null si los datos son correctos
     */
    private String validar(String dni, String nombre, String edadStr, String departamento) {
        if (dni == null || dni.trim().isEmpty()) return "El DNI no puede estar vacío.";
        if (nombre == null || nombre.trim().isEmpty()) return "El nombre no puede estar vacío.";
        if (departamento == null || departamento.trim().isEmpty()) return "El departamento no puede estar vacío.";
        try {
            int edad = Integer.parseInt(edadStr);
            if (edad < 0 || edad > 120) return "La edad debe estar entre 0 y 120.";
        } catch (NumberFormatException e) {
            return "La edad debe ser un número entero.";
        }
        return null;
    }
}
