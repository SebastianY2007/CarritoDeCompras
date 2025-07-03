package ec.edu.ups.vista.usuario;

import ec.edu.ups.controlador.UsuarioController;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Locale;
import java.util.ResourceBundle;

public class GestionDeUsuariosView extends JInternalFrame {

    // --- Componentes de la UI (Deben coincidir con tu .form) ---
    private JPanel panelPrincipal;
    private JLabel lblNombre;
    private JTextField txtNombre; // Este es el campo de texto para buscar
    private JTable tblUsuarios;
    private JButton btnBuscar;
    private JButton btnListar;
    private JButton btnEliminar;
    private JButton btnActualizar;
    private JButton btnAgregar;
    private JScrollPane scrollPane; // Asumiendo que la tabla está dentro de un JScrollPane

    // --- Dependencias y Modelo ---
    private DefaultTableModel modelo;
    private UsuarioController usuarioController;
    private MensajeInternacionalizacionHandler mensajeHandler;
    private ResourceBundle mensajes;

    public GestionDeUsuariosView(MensajeInternacionalizacionHandler mensajeHandler) {
        this.mensajeHandler = mensajeHandler;

        // Configuración de la ventana interna
        setContentPane(panelPrincipal);
        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setIconifiable(true);
        setSize(800, 600);
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE); // Ocultar en lugar de cerrar

        // Configuración del modelo de la tabla
        configurarTabla();

        // La configuración de textos se hará al final para evitar errores
        SwingUtilities.invokeLater(this::updateTexts);
    }

    private void configurarTabla() {
        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Hace que la tabla no sea editable
                return false;
            }
        };
        tblUsuarios.setModel(modelo);
    }

    // --- Getters para que el controlador pueda acceder a los componentes ---
    public JTextField getTxtBuscar() {
        return txtNombre;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JButton getBtnListar() {
        return btnListar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public JButton getBtnActualizar() {
        return btnActualizar;
    }

    public JButton getBtnAgregar() {
        return btnAgregar;
    }

    public JTable getTblUsuarios() {
        return tblUsuarios;
    }

    // --- Setters para inyección de dependencias ---
    public void setUsuarioController(UsuarioController usuarioController) {
        this.usuarioController = usuarioController;
    }

    public void setMensajeInternacionalizacionHandler(MensajeInternacionalizacionHandler mensajeHandler) {
        this.mensajeHandler = mensajeHandler;
        updateTexts();
    }

    // --- Método para actualizar textos (Internacionalización) ---
    public void updateTexts() {
        mensajes = ResourceBundle.getBundle("mensajes", new Locale(mensajeHandler.getLenguajeActual(), mensajeHandler.getPaisActual()));

        setTitle(mensajes.getString("gestionUsuarios.titulo"));

        if (lblNombre != null) {
            lblNombre.setText(mensajes.getString("gestionUsuarios.label.buscar"));
        }

        btnBuscar.setText(mensajes.getString("gestionUsuarios.boton.buscar"));
        btnListar.setText(mensajes.getString("gestionUsuarios.boton.listar"));
        btnAgregar.setText(mensajes.getString("gestionUsuarios.boton.agregar"));
        btnActualizar.setText(mensajes.getString("gestionUsuarios.boton.actualizar"));
        btnEliminar.setText(mensajes.getString("gestionUsuarios.boton.eliminar"));

        // Actualizar encabezados de la tabla
        modelo.setColumnIdentifiers(new Object[]{
                mensajes.getString("gestionUsuarios.tabla.username"),
                mensajes.getString("gestionUsuarios.tabla.email"),
                mensajes.getString("gestionUsuarios.tabla.rol"),
                mensajes.getString("gestionUsuarios.tabla.nombre")
        });

        revalidate();
        repaint();
    }
}
