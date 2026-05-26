package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gestiona la conexión con la base de datos MySQL.
 * Implementa el patrón Singleton para reutilizar una única conexión
 * durante toda la ejecución de la aplicación.
 *
 * CONFIGURACIÓN: edita las constantes URL, USUARIO y PASSWORD
 * con los datos de tu entorno local antes de ejecutar el proyecto.
 */
public class ConexionBD {

    private static final String URL      = "jdbc:mysql://localhost:3306/tienda_db?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO  = "root";       // cambia aquí tu usuario de MySQL
    private static final String PASSWORD = "";           // cambia aquí tu contraseña de MySQL

    private static Connection instancia = null;

    // Constructor privado: nadie puede instanciar esta clase desde fuera
    private ConexionBD() {}

    /**
     * Devuelve la conexión activa. Si no existe o está cerrada, la crea.
     * @return Connection objeto de conexión a MySQL
     * @throws SQLException si no se puede conectar a la base de datos
     */
    public static Connection getConexion() throws SQLException {
        if (instancia == null || instancia.isClosed()) {
            instancia = DriverManager.getConnection(URL, USUARIO, PASSWORD);
        }
        return instancia;
    }

    /**
     * Cierra la conexión activa si existe.
     */
    public static void cerrar() {
        try {
            if (instancia != null && !instancia.isClosed()) {
                instancia.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
