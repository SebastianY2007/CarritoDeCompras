package ec.edu.ups.vista.usuario;

import ec.edu.ups.controlador.UsuarioController;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class ActualizarUsuarioView extends JInternalFrame {

    // --- Componentes de la UI (Deben coincidir con tu .form) ---
    private JPanel panelPrincipal;
    private JComboBox<String> cbxElegir;
    private JTextField txtElegir;
    private JButton btnActualizar;
    private JButton btnCancelar;

    // --- Dependencias y Estado ---
    private UsuarioController usuarioController;
    private MensajeInternacionalizacionHandler mensajeHandler;
    private ResourceBundle mensajes;
    private String usernameActual;

    public ActualizarUsuarioView() {
        // El constructor puede estar vacío si el diseñador de UI inicializa los componentes.
        // La configuración se hará cuando se inyecte el mensajeHandler.
    }

    // Este constructor es necesario para que la lógica de LoginView funcione
    public ActualizarUsuarioView(MensajeInternacionalizacionHandler mensajeHandler) {
        this.mensajeHandler = mensajeHandler;

        setContentPane(panelPrincipal);
        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setIconifiable(true);
        setSize(400, 200);
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);

        SwingUtilities.invokeLater(this::updateTexts);
    }


    // --- Getters para que el controlador acceda a los componentes ---
    public JComboBox<String> getCbxElegir() {
        return cbxElegir;
    }

    public JTextField getTxtValorNuevo() {
        return txtElegir;
    }

    public JButton getBtnActualizar() {
        return btnActualizar;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    public String getUsernameActual() {
        return usernameActual;
    }

    // --- Setters para inyección de dependencias ---
    public void setUsuarioController(UsuarioController usuarioController) {
        this.usuarioController = usuarioController;
    }

    public void setMensajeInternacionalizacionHandler(MensajeInternacionalizacionHandler mensajeHandler) {
        this.mensajeHandler = mensajeHandler;
        updateTexts();
    }

    // --- Métodos de la Vista ---
    public void cargarDatosUsuario(Usuario usuario) {
        this.usernameActual = usuario.getUsername();
        // Limpiar campos al cargar un nuevo usuario
        txtElegir.setText("");
        if (cbxElegir.getItemCount() > 0) {
            cbxElegir.setSelectedIndex(0);
        }
    }

    public void updateTexts() {
        if (mensajeHandler == null) return; // Evitar error si el handler no está listo

        mensajes = ResourceBundle.getBundle("mensajes", new Locale(mensajeHandler.getLenguajeActual(), mensajeHandler.getPaisActual()));

        setTitle(mensajes.getString("actualizarUsuario.titulo"));

        btnActualizar.setText(mensajes.getString("actualizarUsuario.boton.actualizar"));
        btnCancelar.setText(mensajes.getString("actualizarUsuario.boton.cancelar"));

        // --- CORRECCIÓN: Lógica para llenar el JComboBox ---
        if (cbxElegir != null) {
            // Guardar la selección actual para restaurarla después de traducir
            int selectedIndex = cbxElegir.getSelectedIndex();

            // Limpiar y volver a llenar el ComboBox con los textos traducidos
            cbxElegir.removeAllItems();
            cbxElegir.addItem(mensajes.getString("actualizarUsuario.opcion.username"));
            cbxElegir.addItem(mensajes.getString("actualizarUsuario.opcion.contrasena"));
            // NUEVO: Se añaden las opciones para Email y Rol
            cbxElegir.addItem(mensajes.getString("actualizarUsuario.opcion.email"));
            cbxElegir.addItem(mensajes.getString("actualizarUsuario.opcion.rol"));

            // Restaurar la selección si era válida
            if (selectedIndex >= 0 && selectedIndex < cbxElegir.getItemCount()) {
                cbxElegir.setSelectedIndex(selectedIndex);
            }
        }

        revalidate();
        repaint();
    }
}
