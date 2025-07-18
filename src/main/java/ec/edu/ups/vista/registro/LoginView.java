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

/**
 * Clase LoginView
 * <p>
 * Esta clase representa la ventana de inicio de sesión (un JFrame), que es el
 * punto de entrada principal a la aplicación. Permite a los usuarios autenticarse,
 * acceder a la funcionalidad de registro, recuperar su contraseña y seleccionar
 * el idioma de la interfaz.
 *
 * @author Sebastian Yupangui
 * @version 1.0
 * @since 15/07/2025
 */
public class LoginView extends JFrame {

    //<editor-fold defaultstate="collapsed" desc="Componentes de la interfaz de usuario">
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
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="DAOs y Handlers">
    private final UsuarioDAO usuarioDAO;
    private final ProductoDAO productoDAO;
    private final CarritoDAO carritoDAO;
    private final PreguntaSeguridadDAO preguntaSeguridadDAO;
    private final MensajeInternacionalizacionHandler mensajeHandler;
    private ResourceBundle mensajes;
    //</editor-fold>

    /**
     * Constructor de LoginView.
     * <p>
     * Inicializa la ventana de inicio de sesión, configurando sus componentes,
     * listeners y textos según el manejador de internacionalización.
     *
     * @param usuarioDAO          El objeto de acceso a datos para los usuarios.
     * @param productoDAO         El objeto de acceso a datos para los productos.
     * @param carritoDAO          El objeto de acceso a datos para los carritos.
     * @param preguntaSeguridadDAO El objeto de acceso a datos para las preguntas de seguridad.
     * @param mensajeHandler      El manejador para la internacionalización de textos.
     */
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

        // Asegura que los componentes de Swing se inicialicen en el Event Dispatch Thread.
        SwingUtilities.invokeLater(() -> {
            setupListeners();
            configurarSelectorDeIdioma();
            updateTexts();
            configurarIconos();
        });
    }

    /**
     * Inicializa los componentes de la interfaz de usuario.
     * Este método es llamado por el diseñador de UI de IntelliJ IDEA.
     */
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

    /**
     * Redimensiona un icono a las dimensiones especificadas.
     *
     * @param icono El ImageIcon original.
     * @param ancho El nuevo ancho deseado para el icono.
     * @param alto  El nuevo alto deseado para el icono.
     * @return Un nuevo ImageIcon redimensionado.
     */
    private ImageIcon redimensionarIcono(ImageIcon icono, int ancho, int alto) {
        Image imagen = icono.getImage();
        Image imagenRedimensionada = imagen.getScaledInstance(ancho, alto, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(imagenRedimensionada);
    }

    /**
     * Carga y asigna los iconos a los componentes correspondientes de la interfaz.
     * Los iconos se redimensionan para un ajuste visual adecuado.
     */
    private void configurarIconos() {
        // Carga de URLs de los recursos de iconos
        java.net.URL urlIconoIngresar = getClass().getResource("/icons/icono_ingresar.png");
        java.net.URL urlIconoRegistrar = getClass().getResource("/icons/icono_registrarse.png");
        java.net.URL urlIconoIdioma = getClass().getResource("/icons/icono_idiomas.png");
        java.net.URL urlIconoOlvido = getClass().getResource("/icons/icono_olvido_contraseña.png");
        java.net.URL urlIconoUsuario = getClass().getResource("/icons/icono_usuario.png");
        java.net.URL urlIconoContrasena = getClass().getResource("/icons/icono_contrasena.png");

        // Asignación de iconos a botones y etiquetas
        if (urlIconoIngresar != null) {
            btnIniciarSesion.setIcon(redimensionarIcono(new ImageIcon(urlIconoIngresar), 16, 16));
        }
        if (urlIconoRegistrar != null) {
            btnRegistrarse.setIcon(redimensionarIcono(new ImageIcon(urlIconoRegistrar), 16, 16));
        }
        if (urlIconoIdioma != null) {
            lblIdioma.setText(""); // Limpiar texto para mostrar solo el icono
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

    /**
     * Configura los ActionListeners para los botones de la interfaz.
     */
    private void setupListeners() {
        btnIniciarSesion.addActionListener(e -> iniciarSesion());
        btnRegistrarse.addActionListener(e -> abrirRegistro());
        btnOlvidoContrasena.addActionListener(e -> abrirRecuperarCuenta());
    }

    /**
     * Procesa el intento de inicio de sesión.
     * Valida las credenciales y, si son correctas, inicia la aplicación principal.
     */
    private void iniciarSesion() {
        String cedula = txtCedula.getText().trim();
        String password = new String(txtContrasena.getPassword()).trim();

        if (cedula.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, mensajes.getString("global.error.camposVacios"), mensajes.getString("global.aviso.titulo"), JOptionPane.WARNING_MESSAGE);
            return;
        }

        Usuario usuarioAutenticado = usuarioDAO.autenticar(cedula, password);

        if (usuarioAutenticado != null) {
            iniciarAplicacionPrincipal(usuarioAutenticado);
        } else {
            JOptionPane.showMessageDialog(this, mensajes.getString("login.error.autenticacion"), mensajes.getString("global.error.titulo"), JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Inicializa y muestra la ventana principal de la aplicación después de un
     * inicio de sesión exitoso. Configura los controladores y las vistas necesarias.
     *
     * @param usuarioAutenticado El objeto Usuario que ha sido autenticado.
     */
    private void iniciarAplicacionPrincipal(Usuario usuarioAutenticado) {
        // Creación de vistas
        AnadirProductoView anadirProductoView = new AnadirProductoView(mensajeHandler);
        GestionDeProductosView gestionProductosView = new GestionDeProductosView(mensajeHandler);
        ActualizarProductoView actualizarProductoView = new ActualizarProductoView(mensajeHandler);
        AnadirUsuarioView anadirUsuarioView = new AnadirUsuarioView(mensajeHandler);
        GestionDeUsuariosView gestionUsuariosView = new GestionDeUsuariosView(mensajeHandler);
        ActualizarUsuarioView actualizarUsuarioView = new ActualizarUsuarioView(mensajeHandler);
        CrearCarritoView crearCarritoView = new CrearCarritoView();
        GestionarCarritoUsuarioView gestionarCarritoUsuarioView = new GestionarCarritoUsuarioView();
        GestionarCarritoAdministradorView gestionarCarritoAdminView = new GestionarCarritoAdministradorView();

        // Creación de la vista principal y su DesktopPane
        MenuPrincipalView menuPrincipal = new MenuPrincipalView(usuarioAutenticado, mensajeHandler, this);
        JDesktopPane desktopPanePrincipal = menuPrincipal.getjDesktopPane();

        // Creación e inicialización de controladores
        ProductoController productoController = new ProductoController(productoDAO, anadirProductoView, gestionProductosView, actualizarProductoView, crearCarritoView, desktopPanePrincipal, mensajeHandler);
        UsuarioController usuarioController = new UsuarioController(usuarioDAO, gestionUsuariosView, anadirUsuarioView, actualizarUsuarioView, desktopPanePrincipal, mensajeHandler);
        CarritoController carritoController = new CarritoController(this.carritoDAO, productoDAO, crearCarritoView, gestionarCarritoUsuarioView, gestionarCarritoAdminView);

        productoController.initListeners();
        usuarioController.initListeners();
        carritoController.setUsuarioAutenticado(usuarioAutenticado);
        carritoController.initListeners();

        // Vinculación de controladores y vistas con el menú principal
        menuPrincipal.setProductoController(productoController);
        menuPrincipal.setGestionDeProductosView(gestionProductosView);
        menuPrincipal.setUsuarioController(usuarioController);
        menuPrincipal.setGestionDeUsuariosView(gestionUsuariosView);
        menuPrincipal.setCarritoController(carritoController);
        menuPrincipal.setCrearCarritoView(crearCarritoView);
        menuPrincipal.setGestionarCarritoUsuarioView(gestionarCarritoUsuarioView);
        menuPrincipal.setGestionarCarritoAdminView(gestionarCarritoAdminView);

        // Configuración final y visualización
        menuPrincipal.configurarAccesoPorRol();
        menuPrincipal.updateTexts();
        this.setVisible(false);
        menuPrincipal.setVisible(true);
    }

    /**
     * Abre la ventana de registro de nuevos usuarios.
     */
    private void abrirRegistro() {
        RegistroView registroView = new RegistroView(usuarioDAO, preguntaSeguridadDAO, mensajeHandler);
        registroView.setVisible(true);
    }

    /**
     * Inicia el proceso de recuperación de contraseña.
     * Solicita la cédula, valida al usuario, presenta una pregunta de seguridad
     * y, si la respuesta es correcta, permite establecer una nueva contraseña.
     */
    private void abrirRecuperarCuenta() {
        String cedula = JOptionPane.showInputDialog(this, mensajes.getString("recuperar.ingreseCedula"), mensajes.getString("recuperar.titulo"), JOptionPane.QUESTION_MESSAGE);
        if (cedula == null || cedula.trim().isEmpty()) {
            return;
        }

        Usuario usuarioARecuperar = usuarioDAO.buscarPorCedula(cedula.trim());
        if (usuarioARecuperar == null) {
            JOptionPane.showMessageDialog(this, mensajes.getString("recuperar.error.noEncontrado"), mensajes.getString("global.error.titulo"), JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (usuarioARecuperar.getPreguntaSeguridad1() == null || usuarioARecuperar.getPreguntaSeguridad1().isEmpty()) {
            JOptionPane.showMessageDialog(this, mensajes.getString("recuperar.error.sinPreguntas"), mensajes.getString("global.error.titulo"), JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Preparar y barajar las preguntas de seguridad
        List<Map.Entry<String, String>> preguntas = new ArrayList<>();
        preguntas.add(new AbstractMap.SimpleEntry<>(usuarioARecuperar.getPreguntaSeguridad1(), usuarioARecuperar.getRespuestaSeguridad1()));
        preguntas.add(new AbstractMap.SimpleEntry<>(usuarioARecuperar.getPreguntaSeguridad2(), usuarioARecuperar.getRespuestaSeguridad2()));
        preguntas.add(new AbstractMap.SimpleEntry<>(usuarioARecuperar.getPreguntaSeguridad3(), usuarioARecuperar.getRespuestaSeguridad3()));
        Collections.shuffle(preguntas);

        boolean respuestaCorrecta = false;
        // Iterar y mostrar una pregunta al azar
        for (Map.Entry<String, String> par : preguntas) {
            String clavePregunta = par.getKey();
            String preguntaTraducida = mensajes.getString(clavePregunta);
            String respuestaCorrectaEncriptada = par.getValue();
            String respuestaUsuario = JOptionPane.showInputDialog(this, preguntaTraducida, mensajes.getString("recuperar.preguntaSeguridad"), JOptionPane.QUESTION_MESSAGE);

            if (respuestaUsuario == null) return; // El usuario canceló

            if (respuestaUsuario.trim().equalsIgnoreCase(respuestaCorrectaEncriptada)) {
                respuestaCorrecta = true;
                break;
            }
        }

        if (respuestaCorrecta) {
            // Panel para la nueva contraseña
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            JPasswordField pwdNueva = new JPasswordField(20);
            JPasswordField pwdConfirmar = new JPasswordField(20);
            panel.add(new JLabel(mensajes.getString("cambiarContrasena.label.nuevaContrasena")));
            panel.add(pwdNueva);
            panel.add(Box.createVerticalStrut(10)); // Espaciador
            panel.add(new JLabel(mensajes.getString("cambiarContrasena.label.confirmarContrasena")));
            panel.add(pwdConfirmar);

            int option = JOptionPane.showConfirmDialog(this, panel, mensajes.getString("recuperar.cambiarContrasena"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (option == JOptionPane.OK_OPTION) {
                String nuevaContrasena = new String(pwdNueva.getPassword());
                String confirmacion = new String(pwdConfirmar.getPassword());

                if (nuevaContrasena.isEmpty() || !nuevaContrasena.equals(confirmacion)) {
                    JOptionPane.showMessageDialog(this, mensajes.getString("cambiarContrasena.error.noCoinciden"), mensajes.getString("global.error.titulo"), JOptionPane.ERROR_MESSAGE);
                } else {
                    usuarioARecuperar.setContrasena(nuevaContrasena);
                    usuarioDAO.actualizar(usuarioARecuperar);
                    JOptionPane.showMessageDialog(this, mensajes.getString("cambiarContrasena.exito"), mensajes.getString("global.exito.titulo"), JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, mensajes.getString("recuperar.error.respuestaIncorrecta"), mensajes.getString("global.accesoDenegado"), JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Configura el JComboBox para la selección de idioma.
     * Añade los idiomas soportados y un listener para cambiar el locale de la aplicación.
     */
    private void configurarSelectorDeIdioma() {
        cbxIdioma.addItem("Español");
        cbxIdioma.addItem("English");
        cbxIdioma.addItem("Français");

        cbxIdioma.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String idiomaSeleccionado = (String) cbxIdioma.getSelectedItem();
                if (idiomaSeleccionado != null) {
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
                }
                updateTexts();
            }
        });
    }

    /**
     * Actualiza los textos de todos los componentes de la interfaz según el
     * idioma actualmente seleccionado en el MensajeInternacionalizacionHandler.
     */
    public void updateTexts() {
        mensajes = ResourceBundle.getBundle("mensajes", new Locale(mensajeHandler.getLenguajeActual(), mensajeHandler.getPaisActual()));
        setTitle(mensajes.getString("login.titulo"));
        btnIniciarSesion.setText(mensajes.getString("login.boton.iniciar"));
        btnRegistrarse.setText(mensajes.getString("login.boton.registrar"));
        btnOlvidoContrasena.setText(mensajes.getString("login.boton.olvido"));
        lblCedula.setText(mensajes.getString("login.label.cedula"));
        lblContrasena.setText(mensajes.getString("login.label.password"));
        // El texto de lblIdioma se gestiona en configurarIconos
    }

    /**
     * Limpia los campos de texto de cédula y contraseña.
     * Útil para ser llamado después de un cierre de sesión.
     */
    public void limpiarCampos() {
        txtCedula.setText("");
        txtContrasena.setText("");
    }
}