package ec.edu.ups.vista;

import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.controlador.ProductoController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;

public class MenuPrincipalView extends JFrame {
    private Usuario usuarioAutenticado;
    private LoginView loginView;
    private MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler;
    private ResourceBundle mensajes;

    // Componentes del menú
    private JMenuBar menuBar;
    private JMenu menuProductos;
    private JMenuItem menuItemCrearProducto;
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

    // Referencias a las vistas y controlador inyectadas desde LoginView
    private GestionDeProductosView gestionDeProductosView;
    private ProductoAnadirView productoAnadirView;
    private ProductoController productoController;

    public MenuPrincipalView(Usuario usuarioAutenticado, MensajeInternacionalizacionHandler msgHandler, LoginView loginView) {
        System.out.println("DEBUG (MenuPrincipalView): Entrando al constructor de MenuPrincipalView.");
        this.usuarioAutenticado = usuarioAutenticado;
        this.mensajeInternacionalizacionHandler = msgHandler;
        this.mensajes = ResourceBundle.getBundle("messages", new Locale(mensajeInternacionalizacionHandler.getLenguajeActual(), mensajeInternacionalizacionHandler.getPaisActual()));
        this.loginView = loginView;

        setTitle(mensajes.getString("app.titulo") + " - " + usuarioAutenticado.getUsername());
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        System.out.println("DEBUG (MenuPrincipalView): initComponents() completado.");
        setupMenuBar();
        System.out.println("DEBUG (MenuPrincipalView): setupMenuBar() completado.");
        updateTexts();
        System.out.println("DEBUG (MenuPrincipalView): updateTexts() completado.");
        configurarVentanaCierre();
        System.out.println("DEBUG (MenuPrincipalView): configurarVentanaCierre() completado.");
        configurarAccesoPorRol();
        System.out.println("DEBUG (MenuPrincipalView): configurarAccesoPorRol() completado.");

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setContentPane(jDesktopPane);
        System.out.println("DEBUG (MenuPrincipalView): Constructor de MenuPrincipalView completado exitosamente.");
    }

    public void setGestionDeProductosView(GestionDeProductosView gestionDeProductosView) {
        this.gestionDeProductosView = gestionDeProductosView;
        System.out.println("DEBUG (MenuPrincipalView): gestionDeProductosView inyectado.");
    }

    public void setProductoAnadirView(ProductoAnadirView productoAnadirView) {
        this.productoAnadirView = productoAnadirView;
        System.out.println("DEBUG (MenuPrincipalView): productoAnadirView inyectado.");
    }

    public void setProductoController(ProductoController productoController) {
        this.productoController = productoController;
        System.out.println("DEBUG (MenuPrincipalView): productoController inyectado.");
    }

    private void initComponents() {
        jDesktopPane = new JDesktopPane();
    }

    private void setupMenuBar() {
        menuBar = new JMenuBar();

        menuProductos = new JMenu();
        menuItemCrearProducto = new JMenuItem();
        menuItemGestionarProductos = new JMenuItem();

        menuProductos.add(menuItemCrearProducto);
        menuProductos.add(menuItemGestionarProductos);

        menuItemGestionarProductos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("DEBUG (MenuPrincipalView): Clic en menuItemGestionarProductos.");
                if (gestionDeProductosView != null && jDesktopPane != null) {
                    if (!gestionDeProductosView.isVisible()) {
                        jDesktopPane.add(gestionDeProductosView);
                        gestionDeProductosView.setVisible(true);
                        if (productoController != null) {
                            productoController.listarProductos();
                        }
                    }
                    gestionDeProductosView.toFront();
                } else {
                    System.err.println("Error: gestionDeProductosView o jDesktopPane no inicializados en listener de menuItemGestionarProductos.");
                }
            }
        });
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

        menuItemCrearProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("DEBUG (MenuPrincipalView): Clic en menuItemCrearProducto.");
                if (productoAnadirView != null && jDesktopPane != null) {
                    if (!productoAnadirView.isVisible()) {
                        jDesktopPane.add(productoAnadirView);
                        productoAnadirView.setVisible(true);
                    }
                    productoAnadirView.toFront();
                } else {
                    System.err.println("Error: productoAnadirView o jDesktopPane no inicializados en menuItemCrearProducto.");
                }
            }
        });

        menuItemCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("DEBUG (MenuPrincipalView): Clic en Cerrar Sesión.");
                cerrarSesion();
            }
        });

        menuItemSalirAplicacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("DEBUG (MenuPrincipalView): Clic en Salir Aplicación.");
                salirAplicacion();
            }
        });
    }

    public void updateTexts() {
        mensajes = ResourceBundle.getBundle("messages", new Locale(mensajeInternacionalizacionHandler.getLenguajeActual(), mensajeInternacionalizacionHandler.getPaisActual()));

        setTitle(mensajes.getString("app.titulo") + " - " + usuarioAutenticado.getUsername());

        menuProductos.setText(mensajes.getString("menu.gestionProductos"));
        menuItemCrearProducto.setText(mensajes.getString("menu.crearProducto"));
        menuItemGestionarProductos.setText(mensajes.getString("menu.gestionProductos"));

        menuCarrito.setText(mensajes.getString("menu.carrito"));
        menuItemCrearCarrito.setText(mensajes.getString("menu.carrito.crear"));

        menuIdiomas.setText(mensajes.getString("menu.idiomas"));
        menuItemIdiomaEspanol.setText(mensajes.getString("menu.idioma.es"));
        menuItemIdiomaIngles.setText(mensajes.getString("menu.idioma.en"));
        menuItemIdiomaFrances.setText(mensajes.getString("menu.idioma.fr"));

        menuSalir.setText(mensajes.getString("menu.salir"));
        menuItemCerrarSesion.setText(mensajes.getString("menu.salir.cerrar"));
        menuItemSalirAplicacion.setText(mensajes.getString("menu.salir.salir"));

        revalidate();
        repaint();
    }

    public void cambiarIdioma(String lenguaje, String pais) {
        System.out.println("DEBUG (MenuPrincipalView): Cambiando idioma a " + lenguaje + "_" + pais);
        mensajeInternacionalizacionHandler.setLenguaje(lenguaje, pais);
        updateTexts();
    }

    private void configurarVentanaCierre() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("DEBUG (MenuPrincipalView): Intentando cerrar ventana.");
                int confirm = JOptionPane.showConfirmDialog(MenuPrincipalView.this,
                        mensajes.getString("menu.confirmarSalida"),
                        mensajes.getString("menu.tituloSalida"),
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    System.out.println("DEBUG (MenuPrincipalView): Saliendo de la aplicación.");
                    System.exit(0);
                }
            }
        });
    }

    private void configurarAccesoPorRol() {
        System.out.println("DEBUG (MenuPrincipalView): Configurando acceso por rol. Rol del usuario: " + (usuarioAutenticado != null ? usuarioAutenticado.getRol() : "NULO"));
        if (usuarioAutenticado != null && usuarioAutenticado.getRol() == Rol.ADMINISTRADOR) {
            menuProductos.setVisible(true);
            menuItemCrearProducto.setVisible(true);
            menuItemGestionarProductos.setVisible(true);
            System.out.println("DEBUG (MenuPrincipalView): Menús de productos visibles para ADMINISTRADOR.");
        } else {
            menuProductos.setVisible(false);
            menuItemCrearProducto.setVisible(false);
            menuItemGestionarProductos.setVisible(false);
            System.out.println("DEBUG (MenuPrincipalView): Menús de productos ocultos para no ADMINISTRADOR.");
        }
        menuCarrito.setVisible(true);
        menuIdiomas.setVisible(true);
        menuSalir.setVisible(true);
    }

    private void cerrarSesion() {
        System.out.println("DEBUG (MenuPrincipalView): Cerrando sesión.");
        this.dispose();
        if (loginView != null) {
            loginView.setVisible(true);
            System.out.println("DEBUG (MenuPrincipalView): Mostrando LoginView.");
        } else {
            System.err.println("Error: loginView no está inicializado en MenuPrincipalView al cerrar sesión.");
        }
    }

    private void salirAplicacion() {
        System.out.println("DEBUG (MenuPrincipalView): Intentando salir de la aplicación.");
        int confirm = JOptionPane.showConfirmDialog(this,
                mensajes.getString("menu.confirmarSalida"),
                mensajes.getString("menu.tituloSalida"),
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            System.out.println("DEBUG (MenuPrincipalView): Confirmado, saliendo de la aplicación.");
            System.exit(0);
        }
    }

    public JMenuItem getMenuItemCrearProducto() { return menuItemCrearProducto; }
    public JMenuItem getMenuItemCrearCarrito() { return menuItemCrearCarrito; }
    public JMenuItem getMenuItemIdiomaEspanol() { return menuItemIdiomaEspanol; }
    public JMenuItem getMenuItemIdiomaIngles() { return menuItemIdiomaIngles; }
    public JMenuItem getMenuItemIdiomaFrances() { return menuItemIdiomaFrances; }
    public JDesktopPane getjDesktopPane() { return jDesktopPane; }
    public JMenuItem getMenuItemGestionarProductos() { return menuItemGestionarProductos; }
}