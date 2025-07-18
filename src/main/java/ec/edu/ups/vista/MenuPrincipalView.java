package ec.edu.ups.vista;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.controlador.ProductoController;
import ec.edu.ups.controlador.UsuarioController;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.carrito.CrearCarritoView;
import ec.edu.ups.vista.carrito.GestionarCarritoAdministradorView;
import ec.edu.ups.vista.carrito.GestionarCarritoUsuarioView;
import ec.edu.ups.vista.producto.GestionDeProductosView;
import ec.edu.ups.vista.registro.CambiarContrasena;
import ec.edu.ups.vista.registro.LoginView;
import ec.edu.ups.vista.usuario.GestionDeUsuariosView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Clase MenuPrincipalView
 *
 * Esta clase representa la ventana principal de la aplicación, que funciona como
 * un contenedor MDI (Multiple Document Interface) utilizando un JDesktopPane.
 * Alberga el menú principal y gestiona la visualización de las ventanas internas.
 *
 * @author Sebastian Yupangui
 * @version 1.0
 * @since 15/07/2025
 */
public class MenuPrincipalView extends JFrame {

    private Usuario usuarioAutenticado;
    private LoginView loginView;
    private MensajeInternacionalizacionHandler mensajeHandler;
    private ResourceBundle mensajes;

    private JMenuBar menuBar;
    private JMenu menuProductos, menuUsuarios, menuCarrito, menuMasOpciones;
    private JMenuItem menuItemGestionarProductos, menuItemGestionarUsuarios, menuItemCrearCarrito,
            menuItemGestionarCarritos, menuItemCerrarSesion, menuItemSalirAplicacion, menuItemCambiarContrasena;
    private JDesktopPane jDesktopPane;

    private GestionDeProductosView gestionDeProductosView;
    private GestionDeUsuariosView gestionDeUsuariosView;
    private CrearCarritoView crearCarritoView;
    private GestionarCarritoUsuarioView gestionarCarritoUsuarioView;
    private GestionarCarritoAdministradorView gestionarCarritoAdminView;

    private UsuarioController usuarioController;
    private ProductoController productoController;
    private CarritoController carritoController;

    /**
     * Constructor de MenuPrincipalView.
     *
     * @param usuarioAutenticado El objeto del usuario que ha iniciado sesión.
     * @param msgHandler El manejador para la internacionalización de textos.
     * @param loginView Una referencia a la ventana de login para poder mostrarla al cerrar sesión.
     */
    public MenuPrincipalView(Usuario usuarioAutenticado, MensajeInternacionalizacionHandler msgHandler, LoginView loginView) {
        this.usuarioAutenticado = usuarioAutenticado;
        this.mensajeHandler = msgHandler;
        this.loginView = loginView;
        this.mensajes = ResourceBundle.getBundle("mensajes", new Locale(msgHandler.getLenguajeActual(), msgHandler.getPaisActual()));

        setTitle(mensajes.getString("app.titulo") + " - " + usuarioAutenticado.getNombre());
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        initComponents();
        setupMenuBar();
        configurarVentanaCierre();
    }

    /**
     * Inicializa los componentes principales de la ventana.
     * Establece el panel de escritorio personalizado como el contenedor principal.
     */
    private void initComponents() {
        jDesktopPane = new FondoDesktopPane();
        jDesktopPane.setBackground(new Color(230, 240, 255));
        setContentPane(jDesktopPane);
    }

    /**
     * Construye y configura la barra de menú y sus elementos.
     */
    private void setupMenuBar() {
        menuBar = new JMenuBar();

        menuProductos = new JMenu();
        menuItemGestionarProductos = new JMenuItem();
        menuProductos.add(menuItemGestionarProductos);
        menuBar.add(menuProductos);

        menuUsuarios = new JMenu();
        menuItemGestionarUsuarios = new JMenuItem();
        menuUsuarios.add(menuItemGestionarUsuarios);
        menuBar.add(menuUsuarios);

        menuCarrito = new JMenu();
        menuItemCrearCarrito = new JMenuItem();
        menuItemGestionarCarritos = new JMenuItem();
        menuCarrito.add(menuItemCrearCarrito);
        menuCarrito.add(menuItemGestionarCarritos);
        menuBar.add(menuCarrito);

        menuMasOpciones = new JMenu();
        menuItemCambiarContrasena = new JMenuItem();
        menuItemCerrarSesion = new JMenuItem();
        menuItemSalirAplicacion = new JMenuItem();
        menuMasOpciones.add(menuItemCambiarContrasena);
        menuMasOpciones.add(menuItemCerrarSesion);
        menuMasOpciones.add(menuItemSalirAplicacion);
        menuBar.add(menuMasOpciones);

        setJMenuBar(menuBar);

        menuItemGestionarProductos.addActionListener(e -> abrirGestionDeProductos());
        menuItemGestionarUsuarios.addActionListener(e -> abrirGestionDeUsuarios());
        menuItemCrearCarrito.addActionListener(e -> abrirCrearCarrito());
        menuItemGestionarCarritos.addActionListener(e -> abrirGestionCarritos());
        menuItemCerrarSesion.addActionListener(e -> cerrarSesion());
        menuItemSalirAplicacion.addActionListener(e -> salirDeAplicacion());
        menuItemCambiarContrasena.addActionListener(e -> abrirCambiarContrasena());
    }

    /**
     * Método genérico para abrir y gestionar ventanas internas (JInternalFrame).
     * @param frame La ventana interna a mostrar.
     */
    private void abrirVentanaInterna(JInternalFrame frame) {
        if (frame == null) return;
        if (!frame.isVisible()) {
            jDesktopPane.add(frame);
            frame.setVisible(true);
        }
        try {
            frame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) { /* Ignorado */ }
        frame.toFront();
    }

    /**
     * Abre la ventana de gestión de productos.
     */
    private void abrirGestionDeProductos() {
        if (productoController != null) productoController.listarProductos();
        abrirVentanaInterna(gestionDeProductosView);
    }

    /**
     * Abre la ventana de gestión de usuarios.
     */
    private void abrirGestionDeUsuarios() {
        if (usuarioController != null) usuarioController.listarUsuarios();
        abrirVentanaInterna(gestionDeUsuariosView);
    }

    /**
     * Abre la ventana para crear un nuevo carrito.
     */
    private void abrirCrearCarrito() {
        if (carritoController != null) carritoController.iniciarNuevoCarrito();
        abrirVentanaInterna(crearCarritoView);
    }

    /**
     * Abre la ventana de gestión de carritos correspondiente al rol del usuario.
     */
    private void abrirGestionCarritos() {
        if (usuarioAutenticado.getRol() == Rol.ADMINISTRADOR) {
            abrirVentanaInterna(gestionarCarritoAdminView);
        } else if (usuarioAutenticado.getRol() == Rol.USUARIO) {
            abrirVentanaInterna(gestionarCarritoUsuarioView);
        }
    }

    /**
     * Abre la ventana para que el usuario cambie su contraseña.
     */
    private void abrirCambiarContrasena() {
        if (usuarioController != null && usuarioAutenticado != null) {
            CambiarContrasena vista = new CambiarContrasena(this.usuarioController, this.usuarioAutenticado, this.mensajeHandler);
            vista.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Error: No se puede abrir la ventana de cambio de contraseña.", "Error de Componentes", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Actualiza todos los textos de la interfaz según el idioma seleccionado.
     */
    public void updateTexts() {
        mensajes = ResourceBundle.getBundle("mensajes", new Locale(mensajeHandler.getLenguajeActual(), mensajeHandler.getPaisActual()));
        setTitle(mensajes.getString("app.titulo") + " - " + usuarioAutenticado.getNombre());

        menuProductos.setText(mensajes.getString("principal.menu.productos"));
        menuItemGestionarProductos.setText(mensajes.getString("principal.producto.gestionar"));

        menuUsuarios.setText(mensajes.getString("principal.menu.usuarios"));
        menuItemGestionarUsuarios.setText(mensajes.getString("principal.usuario.gestionar"));

        menuCarrito.setText(mensajes.getString("principal.menu.carrito"));
        menuItemCrearCarrito.setText(mensajes.getString("principal.carrito.crear"));
        menuItemGestionarCarritos.setText(mensajes.getString("principal.carrito.gestionar"));

        menuMasOpciones.setText("...");
        menuItemCambiarContrasena.setText(mensajes.getString("principal.menu.cambiar.contrasena"));
        menuItemCerrarSesion.setText(mensajes.getString("principal.menu.salir.sesion"));
        menuItemSalirAplicacion.setText(mensajes.getString("principal.menu.salir.sistema"));

        revalidate();
        repaint();
    }

    /**
     * Configura la visibilidad de los menús según el rol del usuario autenticado.
     */
    public void configurarAccesoPorRol() {
        boolean esAdmin = (usuarioAutenticado != null && usuarioAutenticado.getRol() == Rol.ADMINISTRADOR);
        boolean esUsuario = (usuarioAutenticado != null && usuarioAutenticado.getRol() == Rol.USUARIO);

        menuProductos.setVisible(esAdmin);
        menuUsuarios.setVisible(esAdmin);

        menuItemCrearCarrito.setVisible(esUsuario);
        menuItemGestionarCarritos.setVisible(true);
    }

    /**
     * Cierra la sesión del usuario actual y muestra la ventana de login.
     */
    private void cerrarSesion() {
        int confirm = JOptionPane.showConfirmDialog(this,
                mensajes.getString("principal.confirmar.cerrar"),
                mensajes.getString("global.tituloMensaje"),
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            this.dispose();
            loginView.limpiarCampos();
            loginView.setVisible(true);
        }
    }

    /**
     * Muestra un diálogo de confirmación y cierra la aplicación si el usuario acepta.
     */
    private void salirDeAplicacion() {
        int confirm = JOptionPane.showConfirmDialog(this,
                mensajes.getString("principal.confirmar.salir"),
                mensajes.getString("global.tituloMensaje"),
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    /**
     * Configura el comportamiento de la ventana al intentar cerrarla.
     */
    private void configurarVentanaCierre() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                salirDeAplicacion();
            }
        });
    }

    /**
     * Cambia el idioma de la aplicación.
     * @param lenguaje El código del nuevo lenguaje.
     * @param pais El código del nuevo país.
     */
    public void cambiarIdioma(String lenguaje, String pais) {
        mensajeHandler.setLenguaje(lenguaje, pais);
        updateTexts();
    }

    // --- Getters y Setters para la comunicación con otras clases ---
    public JDesktopPane getjDesktopPane() { return jDesktopPane; }
    public void setGestionDeProductosView(GestionDeProductosView g) { this.gestionDeProductosView = g; }
    public void setProductoController(ProductoController p) { this.productoController = p; }
    public void setGestionDeUsuariosView(GestionDeUsuariosView g) { this.gestionDeUsuariosView = g; }
    public void setUsuarioController(UsuarioController u) { this.usuarioController = u; }
    public void setCrearCarritoView(CrearCarritoView c) { this.crearCarritoView = c; }
    public void setGestionarCarritoUsuarioView(GestionarCarritoUsuarioView g) { this.gestionarCarritoUsuarioView = g; }
    public void setGestionarCarritoAdminView(GestionarCarritoAdministradorView g) { this.gestionarCarritoAdminView = g; }
    public void setCarritoController(CarritoController c) { this.carritoController = c; }
}
