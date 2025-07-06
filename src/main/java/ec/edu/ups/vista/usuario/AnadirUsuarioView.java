package ec.edu.ups.vista.usuario;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import javax.swing.*;
import java.util.ResourceBundle;

public class AnadirUsuarioView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JLabel lblNombre;
    private JTextField txtNombreUsuario;
    private JLabel lblContrasena;
    private JPasswordField txtContrasena;
    private JLabel lblConfirmarContrasena;
    private JPasswordField txtConfirmarContrasena;
    private JButton btnAgregar;
    private JButton btnLimpiar;

    private MensajeInternacionalizacionHandler mensajeHandler;

    public AnadirUsuarioView(MensajeInternacionalizacionHandler handler) {
        this.mensajeHandler = handler;

        setContentPane(panelPrincipal);
        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setIconifiable(true);
        setSize(400, 250);
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);

        updateTexts();
    }

    public void updateTexts() {
        ResourceBundle mensajes = mensajeHandler.getMensajes();

        setTitle(mensajes.getString("anadirUsuario.titulo"));
        lblNombre.setText(mensajes.getString("anadirUsuario.label.nombreUsuario"));
        lblContrasena.setText(mensajes.getString("anadirUsuario.label.contrasena"));
        lblConfirmarContrasena.setText(mensajes.getString("anadirUsuario.label.confirmarContrasena"));
        btnAgregar.setText(mensajes.getString("global.boton.agregar"));
        btnLimpiar.setText(mensajes.getString("global.boton.limpiar"));
    }

    public JTextField getTxtNombreUsuario() {
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

    public void setMensajeInternacionalizacionHandler(MensajeInternacionalizacionHandler mensajeHandler) {
        this.mensajeHandler = mensajeHandler;
        updateTexts();
    }

    public void limpiarCampos() {
        if (txtNombreUsuario != null) txtNombreUsuario.setText("");
        if (txtContrasena != null) txtContrasena.setText("");
        if (txtConfirmarContrasena != null) txtConfirmarContrasena.setText("");
    }
}