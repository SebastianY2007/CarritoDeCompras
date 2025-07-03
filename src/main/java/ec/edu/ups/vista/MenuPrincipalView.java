package ec.edu.ups.vista;

import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.controlador.ProductoController;
import ec.edu.ups.controlador.UsuarioController;
import ec.edu.ups.vista.producto.AnadirProductoView;
import ec.edu.ups.vista.producto.GestionDeProductosView;
import ec.edu.ups.vista.registro.LoginView;
import ec.edu.ups.vista.usuario.GestionDeUsuariosView;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;

public class MenuPrincipalView extends JFrame {

    // --- Variables de Instancia ---
    private Usuario usuarioAutenticado;
    private LoginView loginView;
    private MensajeInternacionalizacionHandler mensajeHandler; // Nombre de variable corregido para consistencia
    private ResourceBundle mensajes;

    // --- Componentes de la UI ---
    private JMenuBar menuBar;
    private JMenu menuProductos, menuUsuarios, menuCarrito, menuIdiomas, menuSalir;
    private JMenuItem menuItemGestionarProductos, menuItemGestionarUsuarios, menuItemCrearCarrito,
            menuItemIdiomaEspanol, menuItemIdiomaIngles, menuItemIdiomaFrances,
            menuItemCerrarSesion, menuItemSalirAplicacion;
    private JDesktopPane jDesktopPane;

    // --- Dependencias (se inyectan desde fuera) ---
    private GestionDeProductosView gestionDeProductosView;
    private GestionDeUsuariosView gestionDeUsuariosView;
    private UsuarioController usuarioController;
    private ProductoController productoController;

    /**
     * CONSTRUCTOR SIMPLIFICADO: Ya no necesita los DAOs.
     * La lógica de creación de controladores se ha movido a LoginView.
     */
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
        configurarAccesoPorRol();
        updateTexts();

        setContentPane(jDesktopPane);
    }

    private void initComponents() {
        jDesktopPane = new JDesktopPane();
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
        menuCarrito.add(menuItemCrearCarrito);
        menuBar.add(menuCarrito);

        menuIdiomas = new JMenu();
        menuItemIdiomaEspanol = new JMenuItem();
        menuItemIdiomaIngles = new JMenuItem();
        menuItemIdiomaFrances = new JMenuItem();
        menuIdiomas.add(menuItemIdiomaEspanol);
        menuIdiomas.add(menuItemIdiomaIngles);
        menuIdiomas.add(menuItemIdiomaFrances);
        menuBar.add(menuIdiomas);

        menuSalir = new JMenu();
        menuItemCerrarSesion = new JMenuItem();
        menuItemSalirAplicacion = new JMenuItem();
        menuSalir.add(menuItemCerrarSesion);
        menuSalir.add(menuItemSalirAplicacion);
        menuBar.add(menuSalir);

        setJMenuBar(menuBar);

        // Listeners
        menuItemGestionarProductos.addActionListener(e -> abrirGestionDeProductos());
        menuItemGestionarUsuarios.addActionListener(e -> abrirGestionDeUsuarios());
        menuItemIdiomaEspanol.addActionListener(e -> cambiarIdioma("es", "EC"));
        menuItemIdiomaIngles.addActionListener(e -> cambiarIdioma("en", "US"));
        menuItemIdiomaFrances.addActionListener(e -> cambiarIdioma("fr", "FR"));
        menuItemCerrarSesion.addActionListener(e -> cerrarSesion());
        menuItemSalirAplicacion.addActionListener(e -> salirDeAplicacion());
    }

    private void abrirGestionDeProductos() {
        if (gestionDeProductosView != null && !gestionDeProductosView.isVisible()) {
            jDesktopPane.add(gestionDeProductosView);
            gestionDeProductosView.setVisible(true);
        }
        if (gestionDeProductosView != null) {
            try {
                gestionDeProductosView.setSelected(true);
            } catch (java.beans.PropertyVetoException e) { /* Ignorado */ }
            gestionDeProductosView.toFront();
            if (productoController != null) {
                productoController.listarProductos();
            }
        }
    }

    private void abrirGestionDeUsuarios() {
        if (gestionDeUsuariosView != null && !gestionDeUsuariosView.isVisible()) {
            jDesktopPane.add(gestionDeUsuariosView);
            gestionDeUsuariosView.setVisible(true);
        }
        if (gestionDeUsuariosView != null) {
            try {
                gestionDeUsuariosView.setSelected(true);
            } catch (java.beans.PropertyVetoException e) { /* Ignorado */ }
            gestionDeUsuariosView.toFront();
            if (usuarioController != null) {
                usuarioController.listarUsuarios();
            }
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

        menuIdiomas.setText(mensajes.getString("principal.menu.idioma"));
        menuItemIdiomaEspanol.setText(mensajes.getString("principal.idioma.es"));
        menuItemIdiomaIngles.setText(mensajes.getString("principal.idioma.en"));
        menuItemIdiomaFrances.setText(mensajes.getString("principal.idioma.fr"));

        menuSalir.setText(mensajes.getString("principal.menu.salir"));
        menuItemCerrarSesion.setText(mensajes.getString("principal.menu.salir.sesion"));
        menuItemSalirAplicacion.setText(mensajes.getString("principal.menu.salir.sistema"));

        revalidate();
        repaint();

        if (gestionDeProductosView != null && gestionDeProductosView.isVisible()) {
            gestionDeProductosView.updateTexts();
        }
        if (gestionDeUsuariosView != null && gestionDeUsuariosView.isVisible()) {
            gestionDeUsuariosView.updateTexts();
        }
    }

    private void configurarAccesoPorRol() {
        boolean esAdmin = (usuarioAutenticado != null && usuarioAutenticado.getRol() == Rol.ADMINISTRADOR);
        menuProductos.setVisible(esAdmin);
        menuUsuarios.setVisible(esAdmin);

        menuCarrito.setVisible(true);
        menuIdiomas.setVisible(true);
        menuSalir.setVisible(true);
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

        if (gestionDeProductosView != null && gestionDeProductosView.isVisible()) {
            gestionDeProductosView.setMensajeInternacionalizacionHandler(mensajeHandler);
        }
        if (gestionDeUsuariosView != null && gestionDeUsuariosView.isVisible()) {
            gestionDeUsuariosView.setMensajeInternacionalizacionHandler(mensajeHandler);
        }
    }

    // --- Getters y Setters ---
    public JDesktopPane getjDesktopPane() { return jDesktopPane; }
    public void setGestionDeProductosView(GestionDeProductosView g) { this.gestionDeProductosView = g; }
    public void setProductoController(ProductoController p) { this.productoController = p; }
    public void setGestionDeUsuariosView(GestionDeUsuariosView g) { this.gestionDeUsuariosView = g; }
    public void setUsuarioController(UsuarioController u) { this.usuarioController = u; }
}
