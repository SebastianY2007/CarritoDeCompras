package ec.edu.ups.vista.registro;

import ec.edu.ups.dao.PreguntaSeguridadDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

public class RecuperarCuentaView extends JFrame {

    // --- Componentes de la UI (Deben coincidir con el .form) ---
    private JPanel panelPrincipal;
    private JLabel lblUsuario;
    private JTextField txtUsuario;
    private JButton btnValidarUsuario;
    private JLabel lblP1;
    private JTextField txtP1;
    private JLabel lblP2;
    private JTextField txtP2;
    private JLabel lblP3;
    private JTextField txtP3;
    private JButton btnValidarPreguntas;
    private JLabel lblNuevaContrasena;
    private JPasswordField pwdNuevaContrasena;
    private JLabel lblConfirmarContrasena;
    private JPasswordField pwdConfirmarContrasena;
    private JButton btnCambiarContrasena;

    // --- Dependencias ---
    private UsuarioDAO usuarioDAO;
    private MensajeInternacionalizacionHandler mensajeHandler;
    private ResourceBundle mensajes;
    private Usuario usuarioActual;

    public RecuperarCuentaView(UsuarioDAO usuarioDAO, PreguntaSeguridadDAO preguntaSeguridadDAO, MensajeInternacionalizacionHandler mensajeHandler) {
        this.usuarioDAO = usuarioDAO;
        this.mensajeHandler = mensajeHandler;

        this.mensajes = ResourceBundle.getBundle("mensajes", new Locale(mensajeHandler.getLenguajeActual(), mensajeHandler.getPaisActual()));

        setTitle(mensajes.getString("recuperarCuenta.titulo"));
        setContentPane(panelPrincipal);
        setSize(450, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        SwingUtilities.invokeLater(() -> {
            configurarVistaInicial();
            addEventListeners();
            updateTexts();
        });
    }

    private void configurarVistaInicial() {
        setPreguntasVisible(false);
        setNuevaContrasenaVisible(false);
    }

    private void addEventListeners() {
        btnValidarUsuario.addActionListener(e -> validarUsuarioAction());
        btnValidarPreguntas.addActionListener(e -> validarPreguntasAction());
        btnCambiarContrasena.addActionListener(e -> cambiarContrasenaAction());
    }

    private void validarUsuarioAction() {
        String username = txtUsuario.getText().trim();
        if (username.isEmpty()) {
            mostrarMensaje("recuperarCuenta.mensaje.campoVacioUsuario", JOptionPane.WARNING_MESSAGE);
            return;
        }

        usuarioActual = usuarioDAO.buscarPorUsername(username);

        if (usuarioActual != null && usuarioActual.getPreguntaSeguridad1() != null) {
            if (lblP1 != null) lblP1.setText(usuarioActual.getPreguntaSeguridad1());
            if (lblP2 != null) lblP2.setText(usuarioActual.getPreguntaSeguridad2());
            if (lblP3 != null) lblP3.setText(usuarioActual.getPreguntaSeguridad3());

            setPreguntasVisible(true);
            txtUsuario.setEnabled(false);
            btnValidarUsuario.setEnabled(false);
            mostrarMensaje("recuperarCuenta.mensaje.usuarioEncontrado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            mostrarMensaje("recuperarCuenta.mensaje.usuarioNoEncontrado", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void validarPreguntasAction() {
        String res1 = txtP1.getText().trim();
        String res2 = txtP2.getText().trim();
        String res3 = txtP3.getText().trim();

        if (res1.isEmpty() || res2.isEmpty() || res3.isEmpty()) {
            mostrarMensaje("recuperarCuenta.mensaje.responderTodasPreguntas", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (res1.equalsIgnoreCase(usuarioActual.getRespuestaSeguridad1()) &&
                res2.equalsIgnoreCase(usuarioActual.getRespuestaSeguridad2()) &&
                res3.equalsIgnoreCase(usuarioActual.getRespuestaSeguridad3())) {

            mostrarMensaje("recuperarCuenta.mensaje.respuestasCorrectas", JOptionPane.INFORMATION_MESSAGE);
            setPreguntasVisible(false);
            setNuevaContrasenaVisible(true);
        } else {
            mostrarMensaje("recuperarCuenta.mensaje.respuestasIncorrectas", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cambiarContrasenaAction() {
        char[] nuevaContrasenaChars = pwdNuevaContrasena.getPassword();
        char[] confirmarContrasenaChars = pwdConfirmarContrasena.getPassword();
        String nuevaContrasena = new String(nuevaContrasenaChars);
        String confirmarContrasena = new String(confirmarContrasenaChars);

        if (nuevaContrasena.isEmpty() || !nuevaContrasena.equals(confirmarContrasena)) {
            mostrarMensaje("recuperarCuenta.mensaje.contrasenasNoCoinciden", JOptionPane.ERROR_MESSAGE);
        } else {
            usuarioActual.setContrasena(nuevaContrasena);
            usuarioDAO.actualizar(usuarioActual);
            mostrarMensaje("recuperarCuenta.mensaje.contrasenaActualizada", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }

        Arrays.fill(nuevaContrasenaChars, ' ');
        Arrays.fill(confirmarContrasenaChars, ' ');
    }

    private void setPreguntasVisible(boolean visible) {
        if (lblP1 != null) lblP1.setVisible(visible);
        if (txtP1 != null) txtP1.setVisible(visible);
        if (lblP2 != null) lblP2.setVisible(visible);
        if (txtP2 != null) txtP2.setVisible(visible);
        if (lblP3 != null) lblP3.setVisible(visible);
        if (txtP3 != null) txtP3.setVisible(visible);
        if (btnValidarPreguntas != null) btnValidarPreguntas.setVisible(visible);
    }

    private void setNuevaContrasenaVisible(boolean visible) {
        if (lblNuevaContrasena != null) lblNuevaContrasena.setVisible(visible);
        if (pwdNuevaContrasena != null) pwdNuevaContrasena.setVisible(visible);
        if (lblConfirmarContrasena != null) lblConfirmarContrasena.setVisible(visible);
        if (pwdConfirmarContrasena != null) pwdConfirmarContrasena.setVisible(visible);
        if (btnCambiarContrasena != null) btnCambiarContrasena.setVisible(visible);
    }

    public void updateTexts() {
        this.mensajes = ResourceBundle.getBundle("mensajes", new Locale(mensajeHandler.getLenguajeActual(), mensajeHandler.getPaisActual()));

        setTitle(mensajes.getString("recuperarCuenta.titulo"));

        if (lblUsuario != null) lblUsuario.setText(mensajes.getString("recuperarCuenta.lblUsuario"));
        if (btnValidarUsuario != null) btnValidarUsuario.setText(mensajes.getString("recuperarCuenta.btnValidarUsuario"));
        if (btnValidarPreguntas != null) btnValidarPreguntas.setText(mensajes.getString("recuperarCuenta.btnValidarPreguntas"));
        if (lblNuevaContrasena != null) lblNuevaContrasena.setText(mensajes.getString("recuperarCuenta.lblNuevaContrasena"));
        if (lblConfirmarContrasena != null) lblConfirmarContrasena.setText(mensajes.getString("recuperarCuenta.lblConfirmarContrasena"));
        if (btnCambiarContrasena != null) btnCambiarContrasena.setText(mensajes.getString("recuperarCuenta.btnCambiarContrasena"));

        revalidate();
        repaint();
    }

    private void mostrarMensaje(String claveMensaje, int tipoMensaje) {
        JOptionPane.showMessageDialog(this, mensajes.getString(claveMensaje), mensajes.getString("global.tituloMensaje"), tipoMensaje);
    }
}
