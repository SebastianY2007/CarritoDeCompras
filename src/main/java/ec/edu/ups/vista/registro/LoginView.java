package ec.edu.ups.vista.registro;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.controlador.ProductoController;
import ec.edu.ups.controlador.UsuarioController;
import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.dao.PreguntaSeguridadDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.MenuPrincipalView;
import ec.edu.ups.vista.carrito.CrearCarritoView;
import ec.edu.ups.vista.carrito.GestionarCarritoAdministradorView;
import ec.edu.ups.vista.carrito.GestionarCarritoUsuarioView;
import ec.edu.ups.vista.producto.ActualizarProductoView;
import ec.edu.ups.vista.producto.AnadirProductoView;
import ec.edu.ups.vista.producto.GestionDeProductosView;
import ec.edu.ups.vista.usuario.ActualizarUsuarioView;
import ec.edu.ups.vista.usuario.AnadirUsuarioView;
import ec.edu.ups.vista.usuario.GestionDeUsuariosView;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginView extends JFrame {

    private JPanel panelPrincipal;
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnIniciarSesion;
    private JButton btnRegistrarse;
    private JButton btnOlvidoContrasena;
    private JComboBox<String> cbxIdioma;
    private JLabel lblUsuario;
    private JLabel lblContrasena;

    private UsuarioDAO usuarioDAO;
    private ProductoDAO productoDAO;
    private CarritoDAO carritoDAO;
    private PreguntaSeguridadDAO preguntaSeguridadDAO;
    private MensajeInternacionalizacionHandler mensajeHandler;

    public LoginView(UsuarioDAO usuarioDAO, ProductoDAO productoDAO, CarritoDAO carritoDAO, PreguntaSeguridadDAO preguntaSeguridadDAO, MensajeInternacionalizacionHandler mensajeHandler) {
        this.usuarioDAO = usuarioDAO;
        this.productoDAO = productoDAO;
        this.carritoDAO = carritoDAO;
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
            iniciarAplicacionPrincipal(usuarioAutenticado);
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.", "Error de Autenticación", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void iniciarAplicacionPrincipal(Usuario usuarioAutenticado) {
        AnadirProductoView anadirProductoView = new AnadirProductoView(mensajeHandler);
        GestionDeProductosView gestionProductosView = new GestionDeProductosView(mensajeHandler);
        ActualizarProductoView actualizarProductoView = new ActualizarProductoView(mensajeHandler);
        AnadirUsuarioView anadirUsuarioView = new AnadirUsuarioView(mensajeHandler);
        GestionDeUsuariosView gestionUsuariosView = new GestionDeUsuariosView(mensajeHandler);
        ActualizarUsuarioView actualizarUsuarioView = new ActualizarUsuarioView(mensajeHandler);
        CrearCarritoView crearCarritoView = new CrearCarritoView();
        GestionarCarritoUsuarioView gestionarCarritoUsuarioView = new GestionarCarritoUsuarioView();
        GestionarCarritoAdministradorView gestionarCarritoAdminView = new GestionarCarritoAdministradorView();

        MenuPrincipalView menuPrincipal = new MenuPrincipalView(usuarioAutenticado, mensajeHandler, this);
        JDesktopPane desktopPanePrincipal = menuPrincipal.getjDesktopPane();

        ProductoController productoController = new ProductoController(
                productoDAO, anadirProductoView, gestionProductosView, actualizarProductoView,
                crearCarritoView, desktopPanePrincipal, mensajeHandler
        );
        UsuarioController usuarioController = new UsuarioController(
                usuarioDAO, gestionUsuariosView, anadirUsuarioView, actualizarUsuarioView,
                desktopPanePrincipal, mensajeHandler
        );
        CarritoController carritoController = new CarritoController(this.carritoDAO, productoDAO, crearCarritoView, gestionarCarritoUsuarioView, gestionarCarritoAdminView);

        productoController.initListeners();
        usuarioController.initListeners();
        carritoController.setUsuarioAutenticado(usuarioAutenticado);
        carritoController.initListeners();

        menuPrincipal.setProductoController(productoController);
        menuPrincipal.setGestionDeProductosView(gestionProductosView);
        menuPrincipal.setUsuarioController(usuarioController);
        menuPrincipal.setGestionDeUsuariosView(gestionUsuariosView);
        menuPrincipal.setCarritoController(carritoController);
        menuPrincipal.setCrearCarritoView(crearCarritoView);
        menuPrincipal.setGestionarCarritoUsuarioView(gestionarCarritoUsuarioView);
        menuPrincipal.setGestionarCarritoAdminView(gestionarCarritoAdminView);

        menuPrincipal.configurarAccesoPorRol();
        menuPrincipal.updateTexts();
        this.setVisible(false);
        menuPrincipal.setVisible(true);
    }

    private void abrirRegistro() {
        RegistroView registroView = new RegistroView(usuarioDAO, preguntaSeguridadDAO, mensajeHandler);
        registroView.setVisible(true);
    }

    private void abrirRecuperarCuenta() {
        String username = JOptionPane.showInputDialog(
                this,
                "Por favor, ingrese su nombre de usuario:",
                "Recuperar Contraseña",
                JOptionPane.QUESTION_MESSAGE
        );

        if (username == null || username.trim().isEmpty()) {
            return;
        }

        Usuario usuarioARecuperar = usuarioDAO.buscarPorUsername(username.trim());

        if (usuarioARecuperar == null || usuarioARecuperar.getPreguntaSeguridad1() == null || usuarioARecuperar.getPreguntaSeguridad1().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Usuario no encontrado o sin preguntas de seguridad.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Map.Entry<String, String>> preguntas = new ArrayList<>();
        preguntas.add(new AbstractMap.SimpleEntry<>(usuarioARecuperar.getPreguntaSeguridad1(), usuarioARecuperar.getRespuestaSeguridad1()));
        preguntas.add(new AbstractMap.SimpleEntry<>(usuarioARecuperar.getPreguntaSeguridad2(), usuarioARecuperar.getRespuestaSeguridad2()));
        preguntas.add(new AbstractMap.SimpleEntry<>(usuarioARecuperar.getPreguntaSeguridad3(), usuarioARecuperar.getRespuestaSeguridad3()));
        Collections.shuffle(preguntas);

        boolean respuestaCorrecta = false;
        for (Map.Entry<String, String> par : preguntas) {
            String pregunta = par.getKey();
            String respuestaCorrectaEncriptada = par.getValue();

            String respuestaUsuario = JOptionPane.showInputDialog(
                    this,
                    pregunta,
                    "Pregunta de Seguridad",
                    JOptionPane.QUESTION_MESSAGE
            );

            if (respuestaUsuario == null) {
                return;
            }

            if (respuestaUsuario.trim().equalsIgnoreCase(respuestaCorrectaEncriptada)) {
                respuestaCorrecta = true;
                break;
            }
        }

        if (respuestaCorrecta) {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            JPasswordField pwdNueva = new JPasswordField(15);
            JPasswordField pwdConfirmar = new JPasswordField(15);
            panel.add(new JLabel("Nueva Contraseña:"));
            panel.add(pwdNueva);
            panel.add(Box.createVerticalStrut(15));
            panel.add(new JLabel("Confirmar Contraseña:"));
            panel.add(pwdConfirmar);

            int option = JOptionPane.showConfirmDialog(
                    this,
                    panel,
                    "Cambiar Contraseña",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (option == JOptionPane.OK_OPTION) {
                String nueva = new String(pwdNueva.getPassword());
                String confirmacion = new String(pwdConfirmar.getPassword());

                if (nueva.isEmpty() || !nueva.equals(confirmacion)) {
                    JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden o están vacías.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    usuarioARecuperar.setContrasena(nueva);
                    usuarioDAO.actualizar(usuarioARecuperar);
                    JOptionPane.showMessageDialog(this, "Contraseña actualizada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Ha fallado todas las preguntas. No puede recuperar la contraseña.", "Acceso Denegado", JOptionPane.ERROR_MESSAGE);
        }
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
                    default:
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
        lblUsuario.setText(mensajes.getString("login.label.username"));
        lblContrasena.setText(mensajes.getString("login.label.password"));
    }

    public void limpiarCampos() {
        txtUsuario.setText("");
        txtContrasena.setText("");
    }
}