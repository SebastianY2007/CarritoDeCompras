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
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnIniciarSesion;
    private JButton btnRegistrarse;
    private JButton btnOlvidoContrasena;
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

        setContentPane(panelPrincipal);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SwingUtilities.invokeLater(() -> {
            setupListeners();
            configurarSelectorDeIdioma();
            updateTexts();
        });
    }

    private void setupListeners() {
        btnIniciarSesion.addActionListener(e -> iniciarSesion());
        btnRegistrarse.addActionListener(e -> abrirRegistro());
        btnOlvidoContrasena.addActionListener(e -> abrirRecuperarCuenta());
    }

    private void iniciarSesion() {
        String username = txtUsuario.getText().trim();
        String password = new String(txtContrasena.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Usuario usuarioAutenticado = usuarioDAO.buscarPorUsername(username);
        if (usuarioAutenticado != null && usuarioAutenticado.getContrasena().equals(password)) {
            this.setVisible(false);
            iniciarAplicacionPrincipal(usuarioAutenticado);
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.", "Error de Autenticación", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * CORREGIDO: Este método ahora es el responsable de construir y conectar toda la aplicación principal.
     */
    private void iniciarAplicacionPrincipal(Usuario usuarioAutenticado) {
        this.dispose();

        // 1. Crear todas las vistas internas
        AnadirProductoView anadirProductoView = new AnadirProductoView(mensajeHandler);
        GestionDeProductosView gestionProductosView = new GestionDeProductosView(mensajeHandler);
        ActualizarProductoView actualizarProductoView = new ActualizarProductoView(mensajeHandler);
        AnadirUsuarioView anadirUsuarioView = new AnadirUsuarioView(mensajeHandler);
        GestionDeUsuariosView gestionUsuariosView = new GestionDeUsuariosView(mensajeHandler);
        ActualizarUsuarioView actualizarUsuarioView = new ActualizarUsuarioView(mensajeHandler);
        CarritoAnadirView carritoAnadirView = new CarritoAnadirView();

        // 2. Crear la ventana principal
        MenuPrincipalView menuPrincipal = new MenuPrincipalView(usuarioAutenticado, mensajeHandler, this);
        JDesktopPane desktopPanePrincipal = menuPrincipal.getjDesktopPane();

        // 3. Crear los controladores, pasándoles las vistas que manejarán
        // CORRECCIÓN: Se añade el 'mensajeHandler' al constructor de ProductoController.
        ProductoController productoController = new ProductoController(
                productoDAO, anadirProductoView, gestionProductosView, actualizarProductoView,
                carritoAnadirView, desktopPanePrincipal, mensajeHandler
        );

        UsuarioController usuarioController = new UsuarioController(
                usuarioDAO, gestionUsuariosView, anadirUsuarioView, actualizarUsuarioView,
                desktopPanePrincipal, mensajeHandler
        );

        // 4. Inyectar las vistas y los controladores en la MenuPrincipalView
        menuPrincipal.setGestionDeProductosView(gestionProductosView);
        menuPrincipal.setProductoController(productoController);

        menuPrincipal.setGestionDeUsuariosView(gestionUsuariosView);
        menuPrincipal.setUsuarioController(usuarioController);

        // 5. Mostrar la ventana principal ya configurada
        menuPrincipal.setVisible(true);
    }

    private void abrirRegistro() {
        RegistroView registroView = new RegistroView(usuarioDAO, preguntaSeguridadDAO, mensajeHandler);
        registroView.setVisible(true);
    }

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
                String idiomaSeleccionado = (String) cbxIdioma.getSelectedItem();
                switch (idiomaSeleccionado) {
                    case "English":
                        mensajeHandler.setLenguaje("en", "US");
                        break;
                    case "Français":
                        mensajeHandler.setLenguaje("fr", "FR");
                        break;
                    default: // Español
                        mensajeHandler.setLenguaje("es", "EC");
                        break;
                }
                updateTexts();
            }
        });
    }

    public void updateTexts() {
        ResourceBundle mensajes = ResourceBundle.getBundle("mensajes", new Locale(mensajeHandler.getLenguajeActual(), mensajeHandler.getPaisActual()));
        setTitle(mensajes.getString("login.titulo"));
        btnIniciarSesion.setText(mensajes.getString("login.boton.iniciar"));
        btnRegistrarse.setText(mensajes.getString("login.boton.registrar"));
        btnOlvidoContrasena.setText(mensajes.getString("login.boton.olvido"));
    }

    public void limpiarCampos() {
        txtUsuario.setText("");
        txtContrasena.setText("");
    }
}
