package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Representa una cita asociada a un cliente.
 * Almacena la fecha y una descripción del motivo de la cita.
 */
public class Cita {

    private int id;
    private int clienteId;
    private String clienteNombre; // campo auxiliar para mostrar en la tabla
    private LocalDateTime fecha;
    private String descripcion;

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public Cita() {}

    public Cita(int clienteId, LocalDateTime fecha, String descripcion) {
        this.clienteId = clienteId;
        this.fecha = fecha;
        this.descripcion = descripcion;
    }

    public Cita(int id, int clienteId, LocalDateTime fecha, String descripcion) {
        this.id = id;
        this.clienteId = clienteId;
        this.fecha = fecha;
        this.descripcion = descripcion;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getClienteId() { return clienteId; }
    public void setClienteId(int clienteId) { this.clienteId = clienteId; }

    public String getClienteNombre() { return clienteNombre; }
    public void setClienteNombre(String clienteNombre) { this.clienteNombre = clienteNombre; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public String getFechaFormateada() {
        return fecha != null ? fecha.format(FORMATO) : "";
    }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
