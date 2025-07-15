package ec.edu.ups.vista.usuario;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

public class AnadirUsuarioView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JLabel lblCedula;
    private JTextField txtCedula;
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
        configurarIconos();
    }

    private ImageIcon redimensionarIcono(ImageIcon icono, int ancho, int alto) {
        Image imagen = icono.getImage();
        Image imagenRedimensionada = imagen.getScaledInstance(ancho, alto, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(imagenRedimensionada);
    }

    private void configurarIconos() {
        java.net.URL urlIconoAgregar = getClass().getResource("/icons/icono_agregar_usuario.png");
        java.net.URL urlIconoEliminar = getClass().getResource("/icons/icono_limpiar.png");

        if (urlIconoAgregar != null) {
            ImageIcon iconoOriginal = new ImageIcon(urlIconoAgregar);

            ImageIcon iconoAjustado = redimensionarIcono(iconoOriginal, 16, 16);

            btnAgregar.setIcon(iconoAjustado);
        } else {
            System.err.println("Icono no encontrado: /icons/icono_agregar_usuario.png");
        }

        if (urlIconoEliminar != null) {
            ImageIcon iconoOriginal = new ImageIcon(urlIconoEliminar);

            ImageIcon iconoAjustado = redimensionarIcono(iconoOriginal, 16, 16);

            btnLimpiar.setIcon(iconoAjustado);
        } else {
            System.err.println("Icono no encontrado: /icons/icono_limpiar.png");
        }
    }

    public void updateTexts() {
        ResourceBundle mensajes = mensajeHandler.getMensajes();

        setTitle(mensajes.getString("anadirUsuario.titulo"));
        lblCedula.setText(mensajes.getString("anadirUsuario.label.nombreUsuario"));
        lblContrasena.setText(mensajes.getString("anadirUsuario.label.contrasena"));
        lblConfirmarContrasena.setText(mensajes.getString("anadirUsuario.label.confirmarContrasena"));
        btnAgregar.setText(mensajes.getString("global.boton.agregar"));
        btnLimpiar.setText(mensajes.getString("global.boton.limpiar"));
    }

    public JTextField getTxtNombreUsuario() {
        return txtCedula;
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
        if (txtCedula != null) txtCedula.setText("");
        if (txtContrasena != null) txtContrasena.setText("");
        if (txtConfirmarContrasena != null) txtConfirmarContrasena.setText("");
    }
}