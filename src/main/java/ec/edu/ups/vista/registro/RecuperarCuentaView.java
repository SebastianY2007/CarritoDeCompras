package ec.edu.ups.vista.registro;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import javax.swing.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class RecuperarCuentaView extends JDialog {

    private JPanel panelPrincipal;
    private JTextField txtUsuario;
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

    public RecuperarCuentaView(JFrame parent, MensajeInternacionalizacionHandler mensajeHandler) {
        super(parent, "Recuperar Contraseña", true);
        this.mensajeHandler = mensajeHandler;

        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(parent);
        updateTexts();
    }

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

    public void updateTexts() {
        ResourceBundle mensajes = ResourceBundle.getBundle("mensajes", new Locale(mensajeHandler.getLenguajeActual(), mensajeHandler.getPaisActual()));
        setTitle(mensajes.getString("recuperarCuenta.titulo"));
    }

    public void mostrarMensaje(String claveMensaje, int tipoMensaje) {
        ResourceBundle mensajes = ResourceBundle.getBundle("mensajes", new Locale(mensajeHandler.getLenguajeActual(), mensajeHandler.getPaisActual()));
        JOptionPane.showMessageDialog(this, mensajes.getString(claveMensaje), mensajes.getString("global.tituloMensaje"), tipoMensaje);
    }

    public void ajustarYCentrar() {
        pack();
        setLocationRelativeTo(getParent());
    }
}