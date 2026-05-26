package model;

/**
 * Representa un cliente de la tienda.
 * Contiene los datos personales y de contacto del cliente.
 */
public class Cliente {

    private int id;
    private String dni;
    private String nombre;
    private int edad;
    private String departamento;

    public Cliente() {}

    public Cliente(String dni, String nombre, int edad, String departamento) {
        this.dni = dni;
        this.nombre = nombre;
        this.edad = edad;
        this.departamento = departamento;
    }

    public Cliente(int id, String dni, String nombre, int edad, String departamento) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.edad = edad;
        this.departamento = departamento;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    @Override
    public String toString() {
        return nombre + " (" + dni + ")";
    }
}
