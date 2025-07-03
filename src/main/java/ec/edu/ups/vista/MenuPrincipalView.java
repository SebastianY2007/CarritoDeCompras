package ec.edu.ups.vista;

import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.controlador.ProductoController;
import ec.edu.ups.vista.producto.AnadirProductoView;
import ec.edu.ups.vista.producto.GestionDeProductosView;
import ec.edu.ups.vista.registro.LoginView;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;

public class MenuPrincipalView extends JFrame {

    // --- Variables de Instancia ---
    private Usuario usuarioAutenticado;
    private LoginView loginView; // Referencia a la vista de login para poder volver a ella
    private MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler;
    private ResourceBundle mensajes;

    // --- Componentes de la UI ---
    private JMenuBar menuBar;
    private JMenu menuProductos;
    private JMenuItem menuItemGestionarProductos;
    private JMenu menuCarrito;
    private JMenuItem menuItemCrearCarrito;
    private JMenu menuIdiomas;
    private JMenuItem menuItemIdiomaEspanol;
    private JMenuItem menuItemIdiomaIngles;
    private JMenuItem menuItemIdiomaFrances;
    private JMenu menuSalir;
    private JMenuItem menuItemCerrarSesion;
    private JMenuItem menuItemSalirAplicacion;
    private JDesktopPane jDesktopPane;

    // --- Dependencias de Vistas y Controladores ---
    private GestionDeProductosView gestionDeProductosView;
    private AnadirProductoView anadirProductoView;
    private ProductoController productoController;

    public MenuPrincipalView(Usuario usuarioAutenticado, MensajeInternacionalizacionHandler msgHandler, LoginView loginView) {
        this.usuarioAutenticado = usuarioAutenticado;
        this.mensajeInternacionalizacionHandler = msgHandler;
        this.loginView = loginView;
        this.mensajes = ResourceBundle.getBundle("mensajes", new Locale(msgHandler.getLenguajeActual(), msgHandler.getPaisActual()));

        setTitle(mensajes.getString("app.titulo") + " - " + usuarioAutenticado.getUsername());
        setSize(1200, 800);

        // CORRECCIÓN IMPORTANTE: Asegurarse de que el cierre sea manejado por nuestro listener
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        initComponents();
        setupMenuBar();
        updateTexts();
        configurarVentanaCierre(); // Configura el comportamiento de la 'X'
        configurarAccesoPorRol();

        setContentPane(jDesktopPane);
    }

    private void initComponents() {
        jDesktopPane = new JDesktopPane();
    }

    private void setupMenuBar() {
        menuBar = new JMenuBar();

        // Menús (código existente)
        menuProductos = new JMenu();
        menuItemGestionarProductos = new JMenuItem();
        menuProductos.add(menuItemGestionarProductos);
        menuBar.add(menuProductos);

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
        menuItemIdiomaEspanol.addActionListener(e -> cambiarIdioma("es", "EC"));
        menuItemIdiomaIngles.addActionListener(e -> cambiarIdioma("en", "US"));
        menuItemIdiomaFrances.addActionListener(e -> cambiarIdioma("fr", "FR"));

        // CORRECCIÓN: Listeners para las acciones de salida
        menuItemCerrarSesion.addActionListener(e -> cerrarSesion());
        menuItemSalirAplicacion.addActionListener(e -> salirDeAplicacion());
    }

    /**
     * CORREGIDO: Cierra la sesión actual y vuelve a la ventana de Login.
     */
    private void cerrarSesion() {
        int confirm = JOptionPane.showConfirmDialog(this,
                mensajes.getString("principal.confirmar.cerrar"), // Clave corregida
                mensajes.getString("app.titulo"),
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            this.dispose(); // Cierra esta ventana (MenuPrincipalView)
            if (loginView != null) {
                loginView.limpiarCampos();
                loginView.setVisible(true); // Muestra la ventana de login de nuevo
            } else {
                // Si algo falla, como último recurso, cierra la aplicación.
                System.exit(0);
            }
        }
    }

    /**
     * CORREGIDO: Cierra completamente la aplicación.
     */
    private void salirDeAplicacion() {
        int confirm = JOptionPane.showConfirmDialog(this,
                mensajes.getString("principal.confirmar.salir"), // Clave corregida
                mensajes.getString("app.titulo"),
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0); // Cierra la JVM
        }
    }

    /**
     * CORREGIDO: Configura el comportamiento del botón 'X' de la ventana.
     * Ahora llama al mismo método que el menú "Salir de la Aplicación".
     */
    private void configurarVentanaCierre() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                salirDeAplicacion(); // Reutiliza la lógica del menú
            }
        });
    }

    // --- OTROS MÉTODOS (sin cambios) ---

    private void abrirGestionDeProductos() {
        if (gestionDeProductosView != null && jDesktopPane != null) {
            if (!gestionDeProductosView.isVisible()) {
                jDesktopPane.add(gestionDeProductosView);
                gestionDeProductosView.setVisible(true);
            }
            if (productoController != null) {
                productoController.listarProductos();
            }
            gestionDeProductosView.toFront();
        }
    }

    public void updateTexts() {
        mensajes = ResourceBundle.getBundle("mensajes", new Locale(mensajeInternacionalizacionHandler.getLenguajeActual(), mensajeInternacionalizacionHandler.getPaisActual()));
        setTitle(mensajes.getString("app.titulo") + " - " + usuarioAutenticado.getUsername());
        menuProductos.setText(mensajes.getString("principal.menu.productos"));
        menuItemGestionarProductos.setText(mensajes.getString("principal.producto.gestionar"));
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
    }

    public void cambiarIdioma(String lenguaje, String pais) {
        mensajeInternacionalizacionHandler.setLenguaje(lenguaje, pais);
        updateTexts();
    }

    private void configurarAccesoPorRol() {
        if (usuarioAutenticado != null && usuarioAutenticado.getRol() == Rol.ADMINISTRADOR) {
            menuProductos.setVisible(true);
        } else {
            menuProductos.setVisible(false);
        }
        menuCarrito.setVisible(true);
        menuIdiomas.setVisible(true);
        menuSalir.setVisible(true);
    }

    // --- Getters y Setters ---
    public JDesktopPane getjDesktopPane() { return jDesktopPane; }
    public void setGestionDeProductosView(GestionDeProductosView gestionDeProductosView) { this.gestionDeProductosView = gestionDeProductosView; }
    public void setAnadirProductoView(AnadirProductoView anadirProductoView) { this.anadirProductoView = anadirProductoView; }
    public void setProductoController(ProductoController productoController) { this.productoController = productoController; }
}
