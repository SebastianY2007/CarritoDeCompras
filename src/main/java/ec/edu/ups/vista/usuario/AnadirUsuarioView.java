package ec.edu.ups.vista.usuario;

import ec.edu.ups.controlador.UsuarioController;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class AnadirUsuarioView extends JInternalFrame {

    // --- Componentes de la UI ---
    private JPanel panelPrincipal;
    private JLabel lblNombreUsuario;
    private JLabel lblContrasena;
    private JLabel lblConfirmarContrasena;
    private JLabel lblNombre;
    private JLabel lblApellido;
    private JLabel lblEmail;
    private JLabel lblTelefono;
    private JTextField txtNombreUsuario;
    private JPasswordField txtContrasena;
    private JPasswordField txtConfirmarContrasena;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtEmail;
    private JTextField txtTelefono;
    private JButton btnAgregar;
    private JButton btnLimpiar;
    private JButton btnCancelar;

    // --- Dependencias ---
    private UsuarioController usuarioController;
    private MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler;
    private ResourceBundle mensajes;

    public AnadirUsuarioView(MensajeInternacionalizacionHandler msgHandler) {
        this.mensajeInternacionalizacionHandler = msgHandler;
        // Se inicializa 'mensajes' para evitar NullPointerException en el setTitle
        this.mensajes = ResourceBundle.getBundle("mensajes", new Locale(msgHandler.getLenguajeActual(), msgHandler.getPaisActual()));

        setTitle(mensajes.getString("anadirUsuario.titulo"));
        setContentPane(panelPrincipal);
        setSize(500, 450);
        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // La llamada a updateTexts() se ha eliminado del constructor para evitar errores.
    }

    // --- Getters para el controlador ---
    public JTextField getTxtNombreUsuario() { return txtNombreUsuario; }
    public JPasswordField getTxtContrasena() { return txtContrasena; }
    public JPasswordField getTxtConfirmarContrasena() { return txtConfirmarContrasena; }
    public JTextField getTxtEmail() { return txtEmail; }
    public JTextField getTxtNombre() { return txtNombre; }
    public JTextField getTxtApellido() { return txtApellido; }
    public JTextField getTxtTelefono() { return txtTelefono; }
    public JButton getBtnAgregar() { return btnAgregar; }
    public JButton getBtnLimpiar() { return btnLimpiar; }
    public JButton getBtnCancelar() { return btnCancelar; }

    // --- Setters para inyectar dependencias ---
    public void setUsuarioController(UsuarioController usuarioController) {
        this.usuarioController = usuarioController;
    }

    public void setMensajeInternacionalizacionHandler(MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler) {
        this.mensajeInternacionalizacionHandler = mensajeInternacionalizacionHandler;
        // CORRECCIÓN: Se eliminó la llamada a updateTexts() de aquí.
        // Este método se llama demasiado pronto durante la inicialización.
        // La llamada inicial a updateTexts() se debe hacer desde la clase Main.
    }

    // --- Métodos de la Vista ---
    public void limpiarCampos() {
        txtNombreUsuario.setText("");
        txtContrasena.setText("");
        txtConfirmarContrasena.setText("");
        txtEmail.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtTelefono.setText("");
    }

    public void mostrarMensaje(String mensaje, int tipoMensaje) {
        // Se asume que tienes una clave "global.info" para el título del diálogo
        JOptionPane.showMessageDialog(this, mensaje, mensajes.getString("global.info"), tipoMensaje);
    }

    public void updateTexts() {
        this.mensajes = ResourceBundle.getBundle("mensajes", new Locale(mensajeInternacionalizacionHandler.getLenguajeActual(), mensajeInternacionalizacionHandler.getPaisActual()));

        setTitle(mensajes.getString("anadirUsuario.titulo"));

        // Actualización de Etiquetas (JLabel)
        lblNombreUsuario.setText(mensajes.getString("anadirUsuario.label.nombreUsuario"));
        lblContrasena.setText(mensajes.getString("anadirUsuario.label.contrasena"));
        lblConfirmarContrasena.setText(mensajes.getString("anadirUsuario.label.confirmarContrasena"));
        lblNombre.setText(mensajes.getString("anadirUsuario.label.nombre"));
        lblApellido.setText(mensajes.getString("anadirUsuario.label.apellido"));
        lblEmail.setText(mensajes.getString("anadirUsuario.label.email"));
        lblTelefono.setText(mensajes.getString("anadirUsuario.label.telefono"));

        // Actualización de Botones (JButton)
        btnAgregar.setText(mensajes.getString("anadirUsuario.boton.agregar"));
        btnLimpiar.setText(mensajes.getString("anadirUsuario.boton.limpiar"));
        btnCancelar.setText(mensajes.getString("anadirUsuario.boton.cancelar"));

        revalidate();
        repaint();
    }
}
