package ec.edu.ups.vista.registro;

import ec.edu.ups.controlador.ProductoController;
import ec.edu.ups.controlador.UsuarioController;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.dao.PreguntaSeguridadDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.MenuPrincipalView;
import ec.edu.ups.vista.carrito.CarritoAnadirView;
import ec.edu.ups.vista.producto.ActualizarProductoView;
import ec.edu.ups.vista.producto.AnadirProductoView;
import ec.edu.ups.vista.producto.GestionDeProductosView;
import ec.edu.ups.vista.usuario.ActualizarUsuarioView;
import ec.edu.ups.vista.usuario.AnadirUsuarioView;
import ec.edu.ups.vista.usuario.GestionDeUsuariosView;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginView extends JFrame {

    // --- Componentes de la UI ---
    private JPanel panelPrincipal;
    private JTextField txtUsername;
    private JPasswordField txtContrasena;
    private JButton btnIniciarSesion;
    private JButton btnRegistrarse;
    private JButton btnOlvido;
    private JComboBox<String> cbxIdioma;

    // --- Dependencias ---
    private UsuarioDAO usuarioDAO;
    private ProductoDAO productoDAO;
    private PreguntaSeguridadDAO preguntaSeguridadDAO;
    private MensajeInternacionalizacionHandler mensajeHandler;

    public LoginView(UsuarioDAO usuarioDAO, ProductoDAO productoDAO, PreguntaSeguridadDAO preguntaSeguridadDAO, MensajeInternacionalizacionHandler mensajeHandler) {
        this.usuarioDAO = usuarioDAO;
        this.productoDAO = productoDAO;
        this.preguntaSeguridadDAO = preguntaSeguridadDAO;
        this.mensajeHandler = mensajeHandler;

        setTitle("Inicio de Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panelPrincipal);
        setSize(450, 250);
        setLocationRelativeTo(null);

        configurarSelectorDeIdioma();
        addEventListeners();
        updateTexts();
    }

    private void addEventListeners() {
        btnIniciarSesion.addActionListener(e -> autenticarUsuario());
        btnRegistrarse.addActionListener(e -> abrirRegistro());
        btnOlvido.addActionListener(e -> abrirRecuperarCuenta());
    }

    private void autenticarUsuario() {
        String username = txtUsername.getText();
        String password = new String(txtContrasena.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Usuario y contraseña no pueden estar vacíos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario usuario = usuarioDAO.buscarPorUsername(username);

        if (usuario != null && usuario.getContrasena().equals(password)) {
            JOptionPane.showMessageDialog(this, mensajeHandler.getMensajes().getString("login.mensaje.exito"), "Éxito", JOptionPane.INFORMATION_MESSAGE);
            iniciarAplicacionPrincipal(usuario);
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.", "Error de Autenticación", JOptionPane.ERROR_MESSAGE);
            limpiarCampos();
        }
    }

    private void iniciarAplicacionPrincipal(Usuario usuarioAutenticado) {
        this.dispose();

        AnadirProductoView anadirProductoView = new AnadirProductoView(mensajeHandler);
        GestionDeProductosView gestionProductosView = new GestionDeProductosView(mensajeHandler);
        ActualizarProductoView actualizarProductoView = new ActualizarProductoView();
        AnadirUsuarioView anadirUsuarioView = new AnadirUsuarioView(mensajeHandler);
        GestionDeUsuariosView gestionUsuariosView = new GestionDeUsuariosView(mensajeHandler);
        ActualizarUsuarioView actualizarUsuarioView = new ActualizarUsuarioView(mensajeHandler);
        CarritoAnadirView carritoAnadirView = new CarritoAnadirView();

        MenuPrincipalView menuPrincipal = new MenuPrincipalView(usuarioAutenticado, mensajeHandler, this);
        JDesktopPane desktopPanePrincipal = menuPrincipal.getjDesktopPane();

        new UsuarioController(usuarioDAO, anadirUsuarioView, gestionUsuariosView, actualizarUsuarioView, desktopPanePrincipal, mensajeHandler);
        new ProductoController(productoDAO, anadirProductoView, gestionProductosView, carritoAnadirView, desktopPanePrincipal);

        menuPrincipal.setGestionDeProductosView(gestionProductosView);
        menuPrincipal.setAnadirProductoView(anadirProductoView);

        menuPrincipal.setVisible(true);
    }

    /**
     * CORREGIDO: Ahora crea y muestra la ventana de Registro,
     * pasándole todas las dependencias que necesita para funcionar.
     */
    private void abrirRegistro() {
        RegistroView registroView = new RegistroView(usuarioDAO, preguntaSeguridadDAO, mensajeHandler);
        registroView.setVisible(true);
    }

    /**
     * CORREGIDO: Ahora crea y muestra la ventana de Recuperar Cuenta,
     * pasándole todas las dependencias que necesita para funcionar.
     */
    private void abrirRecuperarCuenta() {
        RecuperarCuentaView recuperarCuentaView = new RecuperarCuentaView(usuarioDAO, preguntaSeguridadDAO, mensajeHandler);
        recuperarCuentaView.setVisible(true);
    }

    private void configurarSelectorDeIdioma() {
        cbxIdioma.addItem("Español");
        cbxIdioma.addItem("English");
        cbxIdioma.addItem("Français");

        cbxIdioma.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String idiomaSeleccionado = (String) e.getItem();
                switch (idiomaSeleccionado) {
                    case "English":
                        mensajeHandler.setLenguaje("en", "US");
                        break;
                    case "Français":
                        mensajeHandler.setLenguaje("fr", "FR");
                        break;
                    default:
                        mensajeHandler.setLenguaje("es", "EC");
                        break;
                }
                updateTexts();
            }
        });
    }

    public void updateTexts() {
        ResourceBundle mensajes = mensajeHandler.getMensajes();
        setTitle(mensajes.getString("login.titulo"));
        btnIniciarSesion.setText(mensajes.getString("login.boton.iniciar"));
        btnRegistrarse.setText(mensajes.getString("login.boton.registrar"));
        btnOlvido.setText(mensajes.getString("login.boton.olvido"));
    }

    public void limpiarCampos() {
        txtUsername.setText("");
        txtContrasena.setText("");
    }
}
