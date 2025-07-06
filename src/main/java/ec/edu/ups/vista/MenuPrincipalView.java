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
import ec.edu.ups.vista.registro.LoginView;
import ec.edu.ups.vista.usuario.GestionDeUsuariosView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;

public class MenuPrincipalView extends JFrame {

    private Usuario usuarioAutenticado;
    private LoginView loginView;
    private MensajeInternacionalizacionHandler mensajeHandler;
    private ResourceBundle mensajes;

    private JMenuBar menuBar;
    private JMenu menuProductos, menuUsuarios, menuCarrito, menuIdiomas, menuSalir;
    private JMenuItem menuItemGestionarProductos, menuItemGestionarUsuarios, menuItemCrearCarrito,
            menuItemGestionarCarritos, menuItemCerrarSesion, menuItemSalirAplicacion;
    private JDesktopPane jDesktopPane;

    private GestionDeProductosView gestionDeProductosView;
    private GestionDeUsuariosView gestionDeUsuariosView;
    private CrearCarritoView crearCarritoView;
    private GestionarCarritoUsuarioView gestionarCarritoUsuarioView;
    private GestionarCarritoAdministradorView gestionarCarritoAdminView;

    private UsuarioController usuarioController;
    private ProductoController productoController;
    private CarritoController carritoController;

    public MenuPrincipalView(Usuario usuarioAutenticado, MensajeInternacionalizacionHandler msgHandler, LoginView loginView) {
        this.usuarioAutenticado = usuarioAutenticado;
        this.mensajeHandler = msgHandler;
        this.loginView = loginView;
        this.mensajes = ResourceBundle.getBundle("mensajes", new Locale(msgHandler.getLenguajeActual(), msgHandler.getPaisActual()));

        setTitle(mensajes.getString("app.titulo") + " - " + usuarioAutenticado.getUsername());
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        initComponents();
        setupMenuBar();
        configurarVentanaCierre();
    }

    private void initComponents() {
        jDesktopPane = new FondoDesktopPane();

        jDesktopPane.setBackground(new Color(230, 240, 255));

        setContentPane(jDesktopPane);
    }

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

        menuIdiomas = new JMenu();
        menuBar.add(menuIdiomas);

        menuSalir = new JMenu();
        menuItemCerrarSesion = new JMenuItem();
        menuItemSalirAplicacion = new JMenuItem();
        menuSalir.add(menuItemCerrarSesion);
        menuSalir.add(menuItemSalirAplicacion);
        menuBar.add(menuSalir);

        setJMenuBar(menuBar);

        menuItemGestionarProductos.addActionListener(e -> abrirGestionDeProductos());
        menuItemGestionarUsuarios.addActionListener(e -> abrirGestionDeUsuarios());
        menuItemCrearCarrito.addActionListener(e -> abrirCrearCarrito());
        menuItemGestionarCarritos.addActionListener(e -> abrirGestionCarritos());
        menuItemCerrarSesion.addActionListener(e -> cerrarSesion());
        menuItemSalirAplicacion.addActionListener(e -> salirDeAplicacion());
    }

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

    private void abrirGestionDeProductos() {
        if (productoController != null) productoController.listarProductos();
        abrirVentanaInterna(gestionDeProductosView);
    }

    private void abrirGestionDeUsuarios() {
        if (usuarioController != null) usuarioController.listarUsuarios();
        abrirVentanaInterna(gestionDeUsuariosView);
    }

    private void abrirCrearCarrito() {
        if (carritoController != null) carritoController.iniciarNuevoCarrito();
        abrirVentanaInterna(crearCarritoView);
    }

    private void abrirGestionCarritos() {
        if (usuarioAutenticado.getRol() == Rol.ADMINISTRADOR) {
            abrirVentanaInterna(gestionarCarritoAdminView);
        } else if (usuarioAutenticado.getRol() == Rol.USUARIO) {
            abrirVentanaInterna(gestionarCarritoUsuarioView);
        }
    }

    public void updateTexts() {
        mensajes = ResourceBundle.getBundle("mensajes", new Locale(mensajeHandler.getLenguajeActual(), mensajeHandler.getPaisActual()));
        setTitle(mensajes.getString("app.titulo") + " - " + usuarioAutenticado.getUsername());

        menuProductos.setText(mensajes.getString("principal.menu.productos"));
        menuItemGestionarProductos.setText(mensajes.getString("principal.producto.gestionar"));

        menuUsuarios.setText(mensajes.getString("principal.menu.usuarios"));
        menuItemGestionarUsuarios.setText(mensajes.getString("principal.usuario.gestionar"));

        menuCarrito.setText(mensajes.getString("principal.menu.carrito"));
        menuItemCrearCarrito.setText(mensajes.getString("principal.carrito.crear"));
        menuItemGestionarCarritos.setText(mensajes.getString("principal.carrito.gestionar"));

        menuSalir.setText(mensajes.getString("principal.menu.salir"));
        menuItemCerrarSesion.setText(mensajes.getString("principal.menu.salir.sesion"));
        menuItemSalirAplicacion.setText(mensajes.getString("principal.menu.salir.sistema"));

        revalidate();
        repaint();
    }

    public void configurarAccesoPorRol() {
        boolean esAdmin = (usuarioAutenticado != null && usuarioAutenticado.getRol() == Rol.ADMINISTRADOR);
        boolean esUsuario = (usuarioAutenticado != null && usuarioAutenticado.getRol() == Rol.USUARIO);

        menuProductos.setVisible(esAdmin);
        menuUsuarios.setVisible(esAdmin);

        menuItemCrearCarrito.setVisible(esUsuario);
        menuItemGestionarCarritos.setVisible(true);
    }

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

    private void salirDeAplicacion() {
        int confirm = JOptionPane.showConfirmDialog(this,
                mensajes.getString("principal.confirmar.salir"),
                mensajes.getString("global.tituloMensaje"),
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void configurarVentanaCierre() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                salirDeAplicacion();
            }
        });
    }

    public void cambiarIdioma(String lenguaje, String pais) {
        mensajeHandler.setLenguaje(lenguaje, pais);
        updateTexts();
    }

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