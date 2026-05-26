package app;

import util.ConexionBD;

import javax.swing.*;

/**
 * Punto de entrada de la aplicación.
 * Aplica el look and feel del sistema operativo y lanza la ventana principal.
 */
public class Main {

    public static void main(String[] args) {

        // Usar el aspecto visual del sistema operativo
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Si no está disponible, se usa el look and feel por defecto de Java
        }

        // Verificar la conexión a la base de datos antes de abrir la ventana
        try {
            ConexionBD.getConexion();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "No se pudo conectar a la base de datos.\n\n" +
                "Comprueba que MySQL está en marcha y que las credenciales\n" +
                "en src/util/ConexionBD.java son correctas.\n\n" +
                "Error: " + e.getMessage(),
                "Error de conexión", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        // Lanzar la interfaz en el hilo de eventos de Swing (EDT)
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }
}
