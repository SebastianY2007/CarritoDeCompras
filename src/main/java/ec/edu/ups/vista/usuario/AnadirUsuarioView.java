package ec.edu.ups.vista.usuario;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

/**
 * Clase AnadirUsuarioView
 *
 * Representa la vista (un JInternalFrame) que permite a un administrador
 * crear un nuevo usuario en el sistema. Esta vista es parte del módulo de
 * gestión de usuarios.
 *
 * @author Sebastian Yupangui
 * @version 1.0
 * @since 15/07/2025
 */
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

    /**
     * Constructor de AnadirUsuarioView.
     *
     * @param handler El manejador de internacionalización para los textos de la UI.
     */
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

    /**
     * Redimensiona un icono a un tamaño específico.
     * @param icono El ImageIcon original.
     * @param ancho El nuevo ancho del icono.
     * @param alto El nuevo alto del icono.
     * @return un nuevo ImageIcon redimensionado.
     */
    private ImageIcon redimensionarIcono(ImageIcon icono, int ancho, int alto) {
        Image imagen = icono.getImage();
        Image imagenRedimensionada = imagen.getScaledInstance(ancho, alto, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(imagenRedimensionada);
    }

    /**
     * Configura los iconos para los botones de la vista.
     */
    private void configurarIconos() {
        java.net.URL urlIconoAgregar = getClass().getResource("/icons/icono_agregar_usuario.png");
        java.net.URL urlIconoEliminar = getClass().getResource("/icons/icono_limpiar.png");

        if (urlIconoAgregar != null) {
            btnAgregar.setIcon(redimensionarIcono(new ImageIcon(urlIconoAgregar), 16, 16));
        } else {
            System.err.println("Icono no encontrado: /icons/icono_agregar_usuario.png");
        }

        if (urlIconoEliminar != null) {
            btnLimpiar.setIcon(redimensionarIcono(new ImageIcon(urlIconoEliminar), 16, 16));
        } else {
            System.err.println("Icono no encontrado: /icons/icono_limpiar.png");
        }
    }

    /**
     * Actualiza los textos de la interfaz según el idioma seleccionado.
     */
    public void updateTexts() {
        ResourceBundle mensajes = mensajeHandler.getMensajes();

        setTitle(mensajes.getString("anadirUsuario.titulo"));
        // NOTA: La clave 'anadirUsuario.label.nombreUsuario' se usa para la cédula.
        lblCedula.setText(mensajes.getString("anadirUsuario.label.nombreUsuario"));
        lblContrasena.setText(mensajes.getString("anadirUsuario.label.contrasena"));
        lblConfirmarContrasena.setText(mensajes.getString("anadirUsuario.label.confirmarContrasena"));
        btnAgregar.setText(mensajes.getString("global.boton.agregar"));
        btnLimpiar.setText(mensajes.getString("global.boton.limpiar"));
    }

    // --- Getters para que el Controlador pueda acceder a los componentes ---

    /**
     * Obtiene el JTextField para la cédula (anteriormente nombre de usuario).
     * @return el JTextField de la cédula.
     */
    public JTextField getTxtNombreUsuario() {
        return txtCedula;
    }

    /**
     * Obtiene el JPasswordField para la contraseña.
     * @return el JPasswordField de la contraseña.
     */
    public JPasswordField getTxtContrasena() {
        return txtContrasena;
    }

    /**
     * Obtiene el JPasswordField para confirmar la contraseña.
     * @return el JPasswordField de confirmación.
     */
    public JPasswordField getTxtConfirmarContrasena() {
        return txtConfirmarContrasena;
    }

    /**
     * Obtiene el botón de agregar.
     * @return el JButton de agregar.
     */
    public JButton getBtnAgregar() {
        return btnAgregar;
    }

    /**
     * Obtiene el botón de limpiar.
     * @return el JButton de limpiar.
     */
    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    /**
     * Establece el manejador de internacionalización.
     * @param mensajeHandler el nuevo manejador de mensajes.
     */
    public void setMensajeInternacionalizacionHandler(MensajeInternacionalizacionHandler mensajeHandler) {
        this.mensajeHandler = mensajeHandler;
        updateTexts();
    }

    /**
     * Limpia todos los campos del formulario.
     */
    public void limpiarCampos() {
        if (txtCedula != null) txtCedula.setText("");
        if (txtContrasena != null) txtContrasena.setText("");
        if (txtConfirmarContrasena != null) txtConfirmarContrasena.setText("");
    }
}
