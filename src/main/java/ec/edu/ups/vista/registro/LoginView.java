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

import java.awt.*;
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
    private JTextField txtCedula;
    private JPasswordField txtContrasena;
    private JButton btnIniciarSesion;
    private JButton btnRegistrarse;
    private JButton btnOlvidoContrasena;
    private JComboBox<String> cbxIdioma;
    private JLabel lblCedula;
    private JLabel lblContrasena;
    private JLabel lblIdioma;

    private UsuarioDAO usuarioDAO;
    private ProductoDAO productoDAO;
    private CarritoDAO carritoDAO;
    private PreguntaSeguridadDAO preguntaSeguridadDAO;
    private MensajeInternacionalizacionHandler mensajeHandler;
    private ResourceBundle mensajes;

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
            configurarIconos();
        });
    }

    private void createUIComponents() {
        panelPrincipal = new JPanel();
        txtCedula = new JTextField();
        txtContrasena = new JPasswordField();
        btnIniciarSesion = new JButton();
        btnRegistrarse = new JButton();
        btnOlvidoContrasena = new JButton();
        cbxIdioma = new JComboBox<>();
        lblCedula = new JLabel();
        lblContrasena = new JLabel();
        lblIdioma = new JLabel();
    }

    private ImageIcon redimensionarIcono(ImageIcon icono, int ancho, int alto) {
        Image imagen = icono.getImage();
        Image imagenRedimensionada = imagen.getScaledInstance(ancho, alto, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(imagenRedimensionada);
    }

    private void configurarIconos() {
        java.net.URL urlIconoIngresar = getClass().getResource("/icons/icono_ingresar.png");
        java.net.URL urlIconoRegistrar = getClass().getResource("/icons/icono_registrarse.png");
        java.net.URL urlIconoIdioma = getClass().getResource("/icons/icono_idiomas.png");
        java.net.URL urlIconoOlvido = getClass().getResource("/icons/icono_olvido_contraseña.png");
        java.net.URL urlIconoUsuario = getClass().getResource("/icons/icono_usuario.png");
        java.net.URL urlIconoContrasena = getClass().getResource("/icons/icono_contrasena.png");

        if (urlIconoIngresar != null) {
            btnIniciarSesion.setIcon(redimensionarIcono(new ImageIcon(urlIconoIngresar), 16, 16));
        }
        if (urlIconoRegistrar != null) {
            btnRegistrarse.setIcon(redimensionarIcono(new ImageIcon(urlIconoRegistrar), 16, 16));
        }
        if (urlIconoIdioma != null) {
            lblIdioma.setText("");
            lblIdioma.setIcon(redimensionarIcono(new ImageIcon(urlIconoIdioma), 25, 25));
        }
        if (urlIconoOlvido != null) {
            btnOlvidoContrasena.setIcon(redimensionarIcono(new ImageIcon(urlIconoOlvido), 25, 25));
        }
        if (urlIconoUsuario != null) {
            lblCedula.setIcon(redimensionarIcono(new ImageIcon(urlIconoUsuario), 25, 25));
        }
        if (urlIconoContrasena != null) {
            lblContrasena.setIcon(redimensionarIcono(new ImageIcon(urlIconoContrasena), 25, 25));
        }
    }

    private void setupListeners() {
        btnIniciarSesion.addActionListener(e -> iniciarSesion());
        btnRegistrarse.addActionListener(e -> abrirRegistro());
        btnOlvidoContrasena.addActionListener(e -> abrirRecuperarCuenta());
    }

    private void iniciarSesion() {
        String cedula = txtCedula.getText().trim();
        String password = new String(txtContrasena.getPassword()).trim();
        if (cedula.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Usuario usuarioAutenticado = usuarioDAO.autenticar(cedula, password);
        if (usuarioAutenticado != null) {
            iniciarAplicacionPrincipal(usuarioAutenticado);
        } else {
            JOptionPane.showMessageDialog(this, "Cédula o contraseña incorrectos.", "Error de Autenticación", JOptionPane.ERROR_MESSAGE);
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
        String cedula = JOptionPane.showInputDialog(
                this,
                "Por favor, ingrese su número de cédula:",
                "Recuperar Contraseña",
                JOptionPane.QUESTION_MESSAGE
        );
        if (cedula == null || cedula.trim().isEmpty()) {
            return;
        }
        Usuario usuarioARecuperar = usuarioDAO.buscarPorCedula(cedula.trim());
        if (usuarioARecuperar == null) {
            JOptionPane.showMessageDialog(this, "Usuario no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (usuarioARecuperar.getPreguntaSeguridad1() == null || usuarioARecuperar.getPreguntaSeguridad1().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Este usuario no tiene preguntas de seguridad configuradas.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        List<Map.Entry<String, String>> preguntas = new ArrayList<>();
        preguntas.add(new AbstractMap.SimpleEntry<>(usuarioARecuperar.getPreguntaSeguridad1(), usuarioARecuperar.getRespuestaSeguridad1()));
        preguntas.add(new AbstractMap.SimpleEntry<>(usuarioARecuperar.getPreguntaSeguridad2(), usuarioARecuperar.getRespuestaSeguridad2()));
        preguntas.add(new AbstractMap.SimpleEntry<>(usuarioARecuperar.getPreguntaSeguridad3(), usuarioARecuperar.getRespuestaSeguridad3()));
        Collections.shuffle(preguntas);
        boolean respuestaCorrecta = false;
        for (Map.Entry<String, String> par : preguntas) {
            String clavePregunta = par.getKey();
            String preguntaTraducida = mensajes.getString(clavePregunta);
            String respuestaCorrectaEncriptada = par.getValue();
            String respuestaUsuario = JOptionPane.showInputDialog(
                    this,
                    preguntaTraducida,
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
            JLabel lblNueva = new JLabel("Nueva Contraseña:");
            JPasswordField pwdNueva = new JPasswordField(20);
            JLabel lblConfirmar = new JLabel("Confirmar Contraseña:");
            JPasswordField pwdConfirmar = new JPasswordField(20);
            panel.add(lblNueva);
            panel.add(pwdNueva);
            panel.add(Box.createVerticalStrut(10));
            panel.add(lblConfirmar);
            panel.add(pwdConfirmar);
            int option = JOptionPane.showConfirmDialog(
                    this,
                    panel,
                    "Cambiar Contraseña",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );
            if (option == JOptionPane.OK_OPTION) {
                String nuevaContrasena = new String(pwdNueva.getPassword());
                String confirmacion = new String(pwdConfirmar.getPassword());
                if (nuevaContrasena.isEmpty() || !nuevaContrasena.equals(confirmacion)) {
                    JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden o están vacías.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    usuarioARecuperar.setContrasena(nuevaContrasena);
                    usuarioDAO.actualizar(usuarioARecuperar);
                    JOptionPane.showMessageDialog(this, "Contraseña actualizada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Respuesta incorrecta o no reconocida.", "Acceso Denegado", JOptionPane.ERROR_MESSAGE);
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
        mensajes = ResourceBundle.getBundle("mensajes", new Locale(mensajeHandler.getLenguajeActual(), mensajeHandler.getPaisActual()));
        setTitle(mensajes.getString("login.titulo"));
        btnIniciarSesion.setText(mensajes.getString("login.boton.iniciar"));
        btnRegistrarse.setText(mensajes.getString("login.boton.registrar"));
        btnOlvidoContrasena.setText(mensajes.getString("login.boton.olvido"));
        lblCedula.setText(mensajes.getString("login.label.cedula"));
        lblContrasena.setText(mensajes.getString("login.label.password"));
    }

    public void limpiarCampos() {
        txtCedula.setText("");
        txtContrasena.setText("");
    }
}
