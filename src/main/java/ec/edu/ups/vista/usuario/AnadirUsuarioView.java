package ec.edu.ups.vista.usuario;

import ec.edu.ups.controlador.UsuarioController;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class AnadirUsuarioView extends JInternalFrame {

    // --- Componentes de la UI (Deben coincidir con tu .form) ---
    private JPanel panelPrincipal;
    private JLabel lblNombre; // Etiqueta "Nombre de Usuario:"
    private JTextField txtNombreUsuario; // Campo para el username
    private JLabel lblContrasena;
    private JPasswordField txtContrasena;
    private JLabel lblConfirmarContrasena;
    private JPasswordField txtConfirmarContrasena;
    private JButton btnAgregar;
    private JButton btnLimpiar;

    // --- Dependencias ---
    private UsuarioController usuarioController;
    private MensajeInternacionalizacionHandler mensajeHandler;
    private ResourceBundle mensajes;

    public AnadirUsuarioView(MensajeInternacionalizacionHandler mensajeHandler) {
        this.mensajeHandler = mensajeHandler;

        // Es importante que el panel principal exista en tu .form
        setContentPane(panelPrincipal);
        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setIconifiable(true);
        setSize(400, 250);
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);

        // La configuración de textos se hará al final para evitar errores
        SwingUtilities.invokeLater(this::updateTexts);
    }

    // --- Getters para que el controlador acceda a los componentes ---
    public JTextField getTxtNombreUsuario() {
        // Asumiendo que el campo de texto para el username se llama 'txtNombreUsuario' en tu .form
        return txtNombreUsuario;
    }

    public JPasswordField getTxtContrasena() {
        return txtContrasena;
    }

    public JPasswordField getTxtConfirmarContrasena() {
        return txtConfirmarContrasena;
    }

    public JButton getBtnAgregar() {
        return btnAgregar;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
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
    public void limpiarCampos() {
        if (txtNombreUsuario != null) txtNombreUsuario.setText("");
        if (txtContrasena != null) txtContrasena.setText("");
        if (txtConfirmarContrasena != null) txtConfirmarContrasena.setText("");
    }

    public void updateTexts() {
        mensajes = ResourceBundle.getBundle("mensajes", new Locale(mensajeHandler.getLenguajeActual(), mensajeHandler.getPaisActual()));

        setTitle(mensajes.getString("anadirUsuario.titulo"));

        if (lblNombre != null) lblNombre.setText(mensajes.getString("anadirUsuario.label.nombreUsuario"));
        if (lblContrasena != null) lblContrasena.setText(mensajes.getString("anadirUsuario.label.contrasena"));
        if (lblConfirmarContrasena != null) lblConfirmarContrasena.setText(mensajes.getString("anadirUsuario.label.confirmarContrasena"));

        if (btnAgregar != null) btnAgregar.setText(mensajes.getString("anadirUsuario.boton.agregar"));
        if (btnLimpiar != null) btnLimpiar.setText(mensajes.getString("anadirUsuario.boton.limpiar"));

        revalidate();
        repaint();
    }
}
