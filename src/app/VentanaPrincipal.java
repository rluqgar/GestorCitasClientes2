package app;

import controller.CitaController;
import controller.ClienteController;
import model.Cita;
import model.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Ventana principal de la aplicación.
 * Contiene dos pestañas: una para gestionar clientes y otra para gestionar citas.
 * Toda la lógica de validación se delega en los Controllers.
 */
public class VentanaPrincipal extends JFrame {

    // Controllers
    private final ClienteController clienteCtrl = new ClienteController();
    private final CitaController citaCtrl = new CitaController();

    // ── Pestaña Clientes ──────────────────────────────────────
    private JTable tablaClientes;
    private DefaultTableModel modeloClientes;
    private JTextField txtDni, txtNombre, txtEdad, txtDepartamento;

    // ── Pestaña Citas ─────────────────────────────────────────
    private JTable tablaCitas;
    private DefaultTableModel modeloCitas;
    private JComboBox<Cliente> cbClientes;
    private JTextField txtFecha, txtDescripcion;

    // ID del registro seleccionado actualmente (0 = ninguno)
    private int clienteSeleccionadoId = 0;
    private int citaSeleccionadaId = 0;

    public VentanaPrincipal() {
        setTitle("Gestor de Citas y Clientes");
        setSize(820, 620);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Barra superior con logo
        add(construirBarraSuperior(), BorderLayout.NORTH);

        JTabbedPane pestanas = new JTabbedPane();
        pestanas.addTab("Clientes", construirPestanaClientes());
        pestanas.addTab("Citas", construirPestanaCitas());
        add(pestanas, BorderLayout.CENTER);

        cargarClientes();
        cargarCitas();
    }

    /**
     * Construye la barra superior con el logo y el nombre de la aplicación.
     * El logo se carga desde src/resources/img/logo.png.
     * Si no se encuentra el archivo, se muestra solo el texto.
     */
    private JPanel construirBarraSuperior() {
        JPanel barra = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 8));
        barra.setBackground(new Color(30, 60, 114));

        // Intentar cargar el logo
        java.net.URL urlLogo = getClass().getResource("/resources/img/logo.png");
        if (urlLogo != null) {
            ImageIcon iconoOriginal = new ImageIcon(urlLogo);
            // Escalar a altura 50 px manteniendo proporción
            int alturaLogo = 50;
            int anchuraLogo = iconoOriginal.getIconWidth() * alturaLogo / iconoOriginal.getIconHeight();
            Image imagenEscalada = iconoOriginal.getImage()
                    .getScaledInstance(anchuraLogo, alturaLogo, Image.SCALE_SMOOTH);
            barra.add(new JLabel(new ImageIcon(imagenEscalada)));
        }

        // Texto de bienvenida junto al logo
        JLabel lblTitulo = new JLabel("Sistema de Gestión de Citas y Clientes");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 15));
        barra.add(lblTitulo);

        return barra;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // PESTAÑA CLIENTES
    // ─────────────────────────────────────────────────────────────────────────

    private JPanel construirPestanaClientes() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Tabla
        modeloClientes = new DefaultTableModel(new String[]{"ID", "DNI", "Nombre", "Edad", "Departamento"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaClientes = new JTable(modeloClientes);
        tablaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaClientes.getColumnModel().getColumn(0).setMaxWidth(40);
        tablaClientes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) rellenarFormCliente();
        });
        panel.add(new JScrollPane(tablaClientes), BorderLayout.CENTER);

        // Formulario
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Datos del cliente"));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 6, 4, 6);
        g.fill = GridBagConstraints.HORIZONTAL;

        txtDni = new JTextField(12);
        txtNombre = new JTextField(20);
        txtEdad = new JTextField(5);
        txtDepartamento = new JTextField(15);

        añadirCampo(form, g, "DNI:", txtDni, 0);
        añadirCampo(form, g, "Nombre:", txtNombre, 1);
        añadirCampo(form, g, "Edad:", txtEdad, 2);
        añadirCampo(form, g, "Departamento:", txtDepartamento, 3);

        // Botones
        JButton btnAñadir   = new JButton("Añadir");
        JButton btnEditar    = new JButton("Editar");
        JButton btnEliminar  = new JButton("Eliminar");
        JButton btnLimpiar   = new JButton("Limpiar");

        btnAñadir.addActionListener(e -> accionAñadirCliente());
        btnEditar.addActionListener(e -> accionEditarCliente());
        btnEliminar.addActionListener(e -> accionEliminarCliente());
        btnLimpiar.addActionListener(e -> limpiarFormCliente());

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 4));
        botones.add(btnAñadir);
        botones.add(btnEditar);
        botones.add(btnEliminar);
        botones.add(btnLimpiar);

        g.gridx = 0; g.gridy = 4; g.gridwidth = 2;
        form.add(botones, g);

        panel.add(form, BorderLayout.SOUTH);
        return panel;
    }

    private void accionAñadirCliente() {
        String msg = clienteCtrl.añadir(txtDni.getText().trim(), txtNombre.getText().trim(),
                txtEdad.getText().trim(), txtDepartamento.getText().trim());
        mostrarMensaje(msg);
        cargarClientes();
        limpiarFormCliente();
    }

    private void accionEditarCliente() {
        if (clienteSeleccionadoId == 0) { mostrarMensaje("Selecciona un cliente de la tabla."); return; }
        String msg = clienteCtrl.editar(clienteSeleccionadoId, txtDni.getText().trim(),
                txtNombre.getText().trim(), txtEdad.getText().trim(), txtDepartamento.getText().trim());
        mostrarMensaje(msg);
        cargarClientes();
        limpiarFormCliente();
    }

    private void accionEliminarCliente() {
        if (clienteSeleccionadoId == 0) { mostrarMensaje("Selecciona un cliente de la tabla."); return; }
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Eliminar este cliente y todas sus citas?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String msg = clienteCtrl.eliminar(clienteSeleccionadoId);
            mostrarMensaje(msg);
            cargarClientes();
            cargarCitas();
            limpiarFormCliente();
        }
    }

    private void rellenarFormCliente() {
        int fila = tablaClientes.getSelectedRow();
        if (fila < 0) return;
        clienteSeleccionadoId = (int) modeloClientes.getValueAt(fila, 0);
        txtDni.setText((String) modeloClientes.getValueAt(fila, 1));
        txtNombre.setText((String) modeloClientes.getValueAt(fila, 2));
        txtEdad.setText(String.valueOf(modeloClientes.getValueAt(fila, 3)));
        txtDepartamento.setText((String) modeloClientes.getValueAt(fila, 4));
    }

    private void limpiarFormCliente() {
        clienteSeleccionadoId = 0;
        txtDni.setText(""); txtNombre.setText(""); txtEdad.setText(""); txtDepartamento.setText("");
        tablaClientes.clearSelection();
    }

    private void cargarClientes() {
        modeloClientes.setRowCount(0);
        for (Cliente c : clienteCtrl.listar()) {
            modeloClientes.addRow(new Object[]{c.getId(), c.getDni(), c.getNombre(), c.getEdad(), c.getDepartamento()});
        }
        actualizarComboClientes();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // PESTAÑA CITAS
    // ─────────────────────────────────────────────────────────────────────────

    private JPanel construirPestanaCitas() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Tabla
        modeloCitas = new DefaultTableModel(new String[]{"ID", "Cliente", "Fecha", "Descripción"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaCitas = new JTable(modeloCitas);
        tablaCitas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaCitas.getColumnModel().getColumn(0).setMaxWidth(40);
        tablaCitas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) rellenarFormCita();
        });
        panel.add(new JScrollPane(tablaCitas), BorderLayout.CENTER);

        // Formulario
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Datos de la cita"));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 6, 4, 6);
        g.fill = GridBagConstraints.HORIZONTAL;

        cbClientes = new JComboBox<>();
        txtFecha = new JTextField(16);
        txtDescripcion = new JTextField(30);

        g.gridx = 0; g.gridy = 0; g.gridwidth = 1; form.add(new JLabel("Cliente:"), g);
        g.gridx = 1; form.add(cbClientes, g);
        añadirCampo(form, g, "Fecha (dd/MM/yyyy HH:mm):", txtFecha, 1);
        añadirCampo(form, g, "Descripción:", txtDescripcion, 2);

        // Botones
        JButton btnAñadir  = new JButton("Añadir");
        JButton btnEditar   = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar  = new JButton("Limpiar");

        btnAñadir.addActionListener(e -> accionAñadirCita());
        btnEditar.addActionListener(e -> accionEditarCita());
        btnEliminar.addActionListener(e -> accionEliminarCita());
        btnLimpiar.addActionListener(e -> limpiarFormCita());

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 4));
        botones.add(btnAñadir); botones.add(btnEditar); botones.add(btnEliminar); botones.add(btnLimpiar);

        g.gridx = 0; g.gridy = 3; g.gridwidth = 2;
        form.add(botones, g);

        panel.add(form, BorderLayout.SOUTH);
        return panel;
    }

    private void accionAñadirCita() {
        Cliente c = (Cliente) cbClientes.getSelectedItem();
        if (c == null) { mostrarMensaje("No hay clientes disponibles. Añade uno primero."); return; }
        String msg = citaCtrl.añadir(c.getId(), txtFecha.getText().trim(), txtDescripcion.getText().trim());
        mostrarMensaje(msg);
        cargarCitas();
        limpiarFormCita();
    }

    private void accionEditarCita() {
        if (citaSeleccionadaId == 0) { mostrarMensaje("Selecciona una cita de la tabla."); return; }
        Cliente c = (Cliente) cbClientes.getSelectedItem();
        if (c == null) return;
        String msg = citaCtrl.editar(citaSeleccionadaId, c.getId(), txtFecha.getText().trim(), txtDescripcion.getText().trim());
        mostrarMensaje(msg);
        cargarCitas();
        limpiarFormCita();
    }

    private void accionEliminarCita() {
        if (citaSeleccionadaId == 0) { mostrarMensaje("Selecciona una cita de la tabla."); return; }
        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar esta cita?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String msg = citaCtrl.eliminar(citaSeleccionadaId);
            mostrarMensaje(msg);
            cargarCitas();
            limpiarFormCita();
        }
    }

    private void rellenarFormCita() {
        int fila = tablaCitas.getSelectedRow();
        if (fila < 0) return;
        citaSeleccionadaId = (int) modeloCitas.getValueAt(fila, 0);
        txtFecha.setText((String) modeloCitas.getValueAt(fila, 2));
        txtDescripcion.setText((String) modeloCitas.getValueAt(fila, 3));
    }

    private void limpiarFormCita() {
        citaSeleccionadaId = 0;
        txtFecha.setText(""); txtDescripcion.setText("");
        tablaCitas.clearSelection();
    }

    private void cargarCitas() {
        modeloCitas.setRowCount(0);
        for (Cita c : citaCtrl.listar()) {
            modeloCitas.addRow(new Object[]{c.getId(), c.getClienteNombre(), c.getFechaFormateada(), c.getDescripcion()});
        }
    }

    private void actualizarComboClientes() {
        if (cbClientes == null) return;
        cbClientes.removeAllItems();
        for (Cliente c : clienteCtrl.listar()) cbClientes.addItem(c);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // UTILIDADES
    // ─────────────────────────────────────────────────────────────────────────

    /** Añade una fila etiqueta + campo al formulario con GridBagLayout. */
    private void añadirCampo(JPanel panel, GridBagConstraints g, String label, JTextField campo, int fila) {
        g.gridx = 0; g.gridy = fila; g.gridwidth = 1;
        panel.add(new JLabel(label), g);
        g.gridx = 1;
        panel.add(campo, g);
    }

    /** Muestra un diálogo informativo con el mensaje dado. */
    private void mostrarMensaje(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }
}
