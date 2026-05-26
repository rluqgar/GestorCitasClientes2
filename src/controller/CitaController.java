package controller;

import dao.CitaDAO;
import model.Cita;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Contiene la lógica de negocio relacionada con las citas.
 * Valida fechas y datos antes de delegar en el DAO.
 */
public class CitaController {

    private final CitaDAO dao = new CitaDAO();
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    /**
     * Añade una nueva cita para un cliente.
     * @param clienteId ID del cliente al que pertenece la cita
     * @param fechaStr  fecha en formato dd/MM/yyyy HH:mm
     * @param descripcion descripción del motivo de la cita
     * @return mensaje de resultado
     */
    public String añadir(int clienteId, String fechaStr, String descripcion) {
        String error = validar(fechaStr, descripcion);
        if (error != null) return error;

        LocalDateTime fecha = parsearFecha(fechaStr);
        Cita cita = new Cita(clienteId, fecha, descripcion);
        return dao.insertar(cita) ? "Cita añadida correctamente." : "Error al añadir la cita.";
    }

    /**
     * Actualiza una cita existente.
     * @return mensaje de resultado
     */
    public String editar(int id, int clienteId, String fechaStr, String descripcion) {
        String error = validar(fechaStr, descripcion);
        if (error != null) return error;

        LocalDateTime fecha = parsearFecha(fechaStr);
        Cita cita = new Cita(id, clienteId, fecha, descripcion);
        return dao.actualizar(cita) ? "Cita actualizada correctamente." : "Error al actualizar la cita.";
    }

    /**
     * Elimina una cita por su ID.
     * @return mensaje de resultado
     */
    public String eliminar(int id) {
        return dao.eliminar(id) ? "Cita eliminada correctamente." : "Error al eliminar la cita.";
    }

    /**
     * Devuelve todas las citas para mostrarlas en la tabla.
     */
    public List<Cita> listar() {
        return dao.listar();
    }

    /**
     * Devuelve las citas de un cliente concreto.
     */
    public List<Cita> listarPorCliente(int clienteId) {
        return dao.listarPorCliente(clienteId);
    }

    /**
     * Valida que la fecha tenga el formato correcto y que la descripción no esté vacía.
     */
    private String validar(String fechaStr, String descripcion) {
        if (fechaStr == null || fechaStr.trim().isEmpty()) return "La fecha no puede estar vacía.";
        if (descripcion == null || descripcion.trim().isEmpty()) return "La descripción no puede estar vacía.";
        try {
            LocalDateTime.parse(fechaStr.trim(), FORMATO);
        } catch (DateTimeParseException e) {
            return "Formato de fecha incorrecto. Usa dd/MM/yyyy HH:mm (ejemplo: 25/12/2025 10:30).";
        }
        return null;
    }

    private LocalDateTime parsearFecha(String fechaStr) {
        return LocalDateTime.parse(fechaStr.trim(), FORMATO);
    }
}
