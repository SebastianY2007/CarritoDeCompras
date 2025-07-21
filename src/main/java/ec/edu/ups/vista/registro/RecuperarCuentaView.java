package ec.edu.ups.vista.registro;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import javax.swing.*;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Clase RecuperarCuentaView
 *
 * Esta clase representa la vista (un JDialog) para el proceso de recuperación
 * de contraseña. Contiene los paneles que se muestran secuencialmente:
 * pedir usuario, mostrar pregunta y cambiar contraseña.
 *
 * @author Sebastian Yupangui
 * @version 1.0
 * @since 15/07/2025
 */
public class RecuperarCuentaView extends JDialog {

    private JPanel panelPrincipal;
    private JTextField txtUsuario; // Debería renombrarse a txtCedula
    private JButton btnValidarUsuario;
    private JPanel panelPregunta;
    private JTextField txtPregunta;
    private JTextField txtRespuesta;
    private JButton btnValidarRespuesta;
    private JPanel panelContrasena;
    private JPasswordField pwdNuevaContrasena;
    private JPasswordField pwdConfirmarContrasena;
    private JButton btnCambiarContrasena;

    private MensajeInternacionalizacionHandler mensajeHandler;

    /**
     * Constructor de RecuperarCuentaView.
     * @param parent El JFrame padre sobre el cual este diálogo será modal.
     * @param mensajeHandler El manejador para la internacionalización.
     */
    public RecuperarCuentaView(JFrame parent, MensajeInternacionalizacionHandler mensajeHandler) {
        super(parent, "Recuperar Contraseña", true);
        this.mensajeHandler = mensajeHandler;

        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(parent);
        updateTexts();
    }

    // --- Getters para que el Controlador acceda a los componentes ---
    public JTextField getTxtUsuario() { return txtUsuario; }
    public JButton getBtnValidarUsuario() { return btnValidarUsuario; }
    public JPanel getPanelPregunta() { return panelPregunta; }
    public JTextField getTxtPregunta() { return txtPregunta; }
    public JTextField getTxtRespuesta() { return txtRespuesta; }
    public JButton getBtnValidarRespuesta() { return btnValidarRespuesta; }
    public JPanel getPanelContrasena() { return panelContrasena; }
    public JPasswordField getPwdNuevaContrasena() { return pwdNuevaContrasena; }
    public JPasswordField getPwdConfirmarContrasena() { return pwdConfirmarContrasena; }
    public JButton getBtnCambiarContrasena() { return btnCambiarContrasena; }

    /**
     * Actualiza los textos de la interfaz según el idioma seleccionado.
     */
    public void updateTexts() {
        ResourceBundle mensajes = ResourceBundle.getBundle("mensajes", new Locale(mensajeHandler.getLenguajeActual(), mensajeHandler.getPaisActual()));
        setTitle(mensajes.getString("recuperarCuenta.titulo"));
    }

    /**
     * Muestra un mensaje al usuario.
     * @param claveMensaje La clave del mensaje en el archivo de propiedades.
     * @param tipoMensaje El tipo de mensaje (ej: JOptionPane.ERROR_MESSAGE).
     */
    public void mostrarMensaje(String claveMensaje, int tipoMensaje) {
        ResourceBundle mensajes = ResourceBundle.getBundle("mensajes", new Locale(mensajeHandler.getLenguajeActual(), mensajeHandler.getPaisActual()));
        JOptionPane.showMessageDialog(this, mensajes.getString(claveMensaje), mensajes.getString("global.tituloMensaje"), tipoMensaje);
    }

    /**
     * Ajusta el tamaño de la ventana a su contenido y la centra.
     */
    public void ajustarYCentrar() {
        pack();
        setLocationRelativeTo(getParent());
    }
}
