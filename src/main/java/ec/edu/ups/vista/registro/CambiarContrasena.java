package ec.edu.ups.vista.registro;

import ec.edu.ups.controlador.UsuarioController;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.util.Arrays;

public class CambiarContrasena extends JFrame {

    private JPanel panelPrincipal;
    private JLabel lblDatosUsuario;
    private JLabel lblNombre;
    private JLabel lblCorreoElectronico;
    private JLabel lblRol;
    private JLabel txtCorreoElectronico;
    private JLabel txtNombre;
    private JLabel txtRol;
    private JLabel lblCambiar;
    private JLabel lblNuevaContrasena;
    private JPasswordField txtNuevaContrasena;
    private JLabel lblConfirmarContrasena;
    private JPasswordField txtConfirmarContrasena;
    private JButton btnCambiar;

    private final UsuarioController usuarioController;
    private final Usuario usuarioLogueado;
    private final MensajeInternacionalizacionHandler mensajeHandler;

    public CambiarContrasena(UsuarioController usuarioController, Usuario usuarioLogueado, MensajeInternacionalizacionHandler mensajeHandler) {
        this.usuarioController = usuarioController;
        this.usuarioLogueado = usuarioLogueado;
        this.mensajeHandler = mensajeHandler;

        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        cargarDatosDelUsuario();
        actualizarTextos();
        configurarListeners();
    }

    private void actualizarTextos() {
        setTitle(mensajeHandler.get("cambiarContrasena.titulo"));
        lblDatosUsuario.setText(mensajeHandler.get("cambiarContrasena.label.datosUsuario"));
        lblNombre.setText(mensajeHandler.get("cambiarContrasena.label.nombreUsuario"));
        lblCorreoElectronico.setText(mensajeHandler.get("cambiarContrasena.label.correo"));
        lblRol.setText(mensajeHandler.get("cambiarContrasena.label.rol"));
        lblCambiar.setText(mensajeHandler.get("cambiarContrasena.label.cambiar"));
        lblNuevaContrasena.setText(mensajeHandler.get("cambiarContrasena.label.nuevaContrasena"));
        lblConfirmarContrasena.setText(mensajeHandler.get("cambiarContrasena.label.confirmarContrasena"));
        btnCambiar.setText(mensajeHandler.get("global.boton.actualizar"));
    }

    private void cargarDatosDelUsuario() {
        if (this.usuarioLogueado != null) {
            txtNombre.setText(usuarioLogueado.getCedula());
            txtCorreoElectronico.setText(usuarioLogueado.getCorreoElectronico());
            txtRol.setText(usuarioLogueado.getRol().name());
        } else {
            JOptionPane.showMessageDialog(this, "Error: No se pudo cargar la información del usuario.", "Error de Sesión", JOptionPane.ERROR_MESSAGE);
            this.dispose();
        }
    }

    private void configurarListeners() {
        btnCambiar.addActionListener(e -> cambiarContrasena());
    }

    private void cambiarContrasena() {
        char[] nuevaPassChars = txtNuevaContrasena.getPassword();
        char[] confirmarPassChars = txtConfirmarContrasena.getPassword();
        String nuevaContrasena = new String(nuevaPassChars);
        String confirmarContrasena = new String(confirmarPassChars);

        // Validaciones con mensajes internacionalizados
        if (nuevaContrasena.isEmpty() || confirmarContrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, mensajeHandler.get("cambiarContrasena.error.camposVacios"), mensajeHandler.get("global.aviso.titulo"), JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!nuevaContrasena.equals(confirmarContrasena)) {
            JOptionPane.showMessageDialog(this, mensajeHandler.get("cambiarContrasena.error.noCoinciden"), mensajeHandler.get("global.error.titulo"), JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            boolean exito = usuarioController.actualizarContrasena(usuarioLogueado.getCedula(), nuevaContrasena);
            if (exito) {
                JOptionPane.showMessageDialog(this, mensajeHandler.get("cambiarContrasena.exito"), mensajeHandler.get("global.exito.titulo"), JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, mensajeHandler.get("cambiarContrasena.error.noActualizada"), mensajeHandler.get("global.error.titulo"), JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), mensajeHandler.get("global.error.titulo"), JOptionPane.ERROR_MESSAGE);
        } finally {
            Arrays.fill(nuevaPassChars, ' ');
            Arrays.fill(confirmarPassChars, ' ');
        }
    }
}
