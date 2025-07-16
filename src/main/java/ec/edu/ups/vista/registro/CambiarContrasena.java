package ec.edu.ups.vista.registro;

import ec.edu.ups.controlador.UsuarioController;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.util.Arrays;

/**
 * Clase CambiarContrasena
 *
 * Esta clase representa la vista (un JFrame) que permite a un usuario
 * cambiar su propia contraseña. Muestra los datos del usuario logueado y
 * proporciona campos para ingresar y confirmar la nueva contraseña.
 *
 * @author Sebastian Yupangui
 * @version 1.0
 * @since 15/07/2025
 */
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

    /**
     * Constructor de CambiarContrasena.
     *
     * @param usuarioController El controlador de usuarios para procesar la actualización.
     * @param usuarioLogueado El objeto del usuario que ha iniciado sesión.
     * @param mensajeHandler El manejador para la internacionalización de textos.
     */
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

    /**
     * Actualiza los textos de la interfaz según el idioma seleccionado.
     */
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

    /**
     * Carga y muestra los datos del usuario logueado en las etiquetas.
     * Muestra la cédula en lugar del nombre para identificación.
     */
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

    /**
     * Configura el listener para el botón de cambiar contraseña.
     */
    private void configurarListeners() {
        btnCambiar.addActionListener(e -> cambiarContrasena());
    }

    /**
     * Procesa el cambio de contraseña.
     *
     * Realiza las validaciones necesarias y, si son correctas, llama al
     * controlador para actualizar la contraseña en la capa de persistencia.
     */
    private void cambiarContrasena() {
        char[] nuevaPassChars = txtNuevaContrasena.getPassword();
        char[] confirmarPassChars = txtConfirmarContrasena.getPassword();
        String nuevaContrasena = new String(nuevaPassChars);
        String confirmarContrasena = new String(confirmarPassChars);

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
            // Limpieza segura de las contraseñas en memoria
            Arrays.fill(nuevaPassChars, ' ');
            Arrays.fill(confirmarPassChars, ' ');
        }
    }
}
