package ec.edu.ups.vista;

import ec.edu.ups.modelo.Rol; // Importa tu enum Rol
import ec.edu.ups.modelo.Usuario; // Importa tu clase Usuario
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;

public class MenuPrincipalView extends JFrame {

    private MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler;
    private Usuario usuarioAutenticado;

    private JMenuBar menuBar;

    private JMenu menuProducto;
    private JMenu menuCarrito;
    private JMenu menuIdioma;
    private JMenu menuSalir;

    private JMenuItem menuItemCrearProducto;
    private JMenuItem menuItemEliminarProducto;
    private JMenuItem menuItemActualizarProducto;
    private JMenuItem menuItemBuscarProducto;

    private JMenuItem menuItemCrearCarrito; // Ya identificado

    private JMenuItem menuItemIdiomaEspanol;
    private JMenuItem menuItemIdiomaIngles;
    private JMenuItem menuItemIdiomaFrances;

    private JMenuItem menuItemSalir;
    private JMenuItem menuItemCerrarSesion;

    private JDesktopPane jDesktopPane;

    public MenuPrincipalView(Usuario usuarioAutenticado, MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler) {
        this.usuarioAutenticado = usuarioAutenticado;
        this.mensajeInternacionalizacionHandler = mensajeInternacionalizacionHandler;
        initComponents();
        aplicarPermisosDeRol();
    }

    public MenuPrincipalView() {
        initComponents();
    }


    private void initComponents() {
        jDesktopPane = new JDesktopPane();
        menuBar = new JMenuBar();

        if (mensajeInternacionalizacionHandler == null) {
            mensajeInternacionalizacionHandler = new MensajeInternacionalizacionHandler();
        }

        menuProducto = new JMenu(mensajeInternacionalizacionHandler.get("menu.producto"));
        menuCarrito = new JMenu(mensajeInternacionalizacionHandler.get("menu.carrito"));
        menuIdioma = new JMenu(mensajeInternacionalizacionHandler.get("menu.idiomas"));
        menuSalir = new JMenu(mensajeInternacionalizacionHandler.get("menu.salir"));

        menuItemCrearProducto = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.producto.crear"));
        menuItemEliminarProducto = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.producto.eliminar"));
        menuItemActualizarProducto = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.producto.actualizar"));
        menuItemBuscarProducto = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.producto.buscar"));

        menuItemCrearCarrito = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.carrito.crear"));

        menuItemIdiomaEspanol = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.idioma.es"));
        menuItemIdiomaIngles = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.idioma.en"));
        menuItemIdiomaFrances = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.idioma.fr"));

        menuItemSalir = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.salir.salir"));
        menuItemCerrarSesion = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.salir.cerrar"));

        menuBar.add(menuProducto);
        menuBar.add(menuCarrito);
        menuBar.add(menuIdioma);
        menuBar.add(menuSalir);

        menuProducto.add(menuItemCrearProducto);
        menuProducto.add(menuItemEliminarProducto);
        menuProducto.add(menuItemActualizarProducto);
        menuProducto.add(menuItemBuscarProducto);

        menuCarrito.add(menuItemCrearCarrito);

        menuIdioma.add(menuItemIdiomaEspanol);
        menuIdioma.add(menuItemIdiomaIngles);
        menuIdioma.add(menuItemIdiomaFrances);

        menuSalir.add(menuItemSalir);
        menuSalir.add(menuItemCerrarSesion);

        setJMenuBar(menuBar);
        setContentPane(jDesktopPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(mensajeInternacionalizacionHandler.get("app.titulo"));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void aplicarPermisosDeRol() {
        if (usuarioAutenticado != null) {
            if (usuarioAutenticado.getRol() == Rol.USUARIO) {
                deshabilitarMenusAdministrador();
                deshabilitarCrearCarrito();
            } else if (usuarioAutenticado.getRol() == Rol.ADMINISTRADOR) {
                habilitarMenusAdministrador();
                habilitarCrearCarrito();
            }
        } else {
            deshabilitarTodo();
        }
    }

    public void deshabilitarMenusAdministrador() {
        menuItemCrearProducto.setEnabled(false);
        menuItemEliminarProducto.setEnabled(false);
        menuItemActualizarProducto.setEnabled(false);
        menuProducto.setEnabled(false);
    }

    public void habilitarMenusAdministrador() {
        menuItemCrearProducto.setEnabled(true);
        menuItemEliminarProducto.setEnabled(true);
        menuItemActualizarProducto.setEnabled(true);
        menuItemBuscarProducto.setEnabled(true);
        menuProducto.setEnabled(true);
    }

    public void deshabilitarCrearCarrito() {
        menuItemCrearCarrito.setEnabled(false);
    }

    public void habilitarCrearCarrito() {
        menuItemCrearCarrito.setEnabled(true);
    }

    public void deshabilitarTodo() {
        menuBar.setEnabled(false);
        for (JMenu menu : new JMenu[]{menuProducto, menuCarrito, menuIdioma, menuSalir}) {
            menu.setEnabled(false);
            for (int i = 0; i < menu.getItemCount(); i++) {
                JMenuItem item = menu.getItem(i);
                if (item != null) {
                    item.setEnabled(false);
                }
            }
        }
    }

    public JMenuItem getMenuItemCrearProducto() {
        return menuItemCrearProducto;
    }

    public void setMenuItemCrearProducto(JMenuItem menuItemCrearProducto) {
        this.menuItemCrearProducto = menuItemCrearProducto;
    }

    public JMenuItem getMenuItemEliminarProducto() {
        return menuItemEliminarProducto;
    }

    public void setMenuItemEliminarProducto(JMenuItem menuItemEliminarProducto) {
        this.menuItemEliminarProducto = menuItemEliminarProducto;
    }

    public JMenuItem getMenuItemActualizarProducto() {
        return menuItemActualizarProducto;
    }

    public void setMenuItemActualizarProducto(JMenuItem menuItemActualizarProducto) {
        this.menuItemActualizarProducto = menuItemActualizarProducto;
    }

    public JMenuItem getMenuItemBuscarProducto() {
        return menuItemBuscarProducto;
    }

    public void setMenuItemBuscarProducto(JMenuItem menuItemBuscarProducto) {
        this.menuItemBuscarProducto = menuItemBuscarProducto;
    }

    public JMenu getMenuProducto() {
        return menuProducto;
    }

    public void setMenuProducto(JMenu menuProducto) {
        this.menuProducto = menuProducto;
    }

    public JMenu getMenuCarrito() {
        return menuCarrito;
    }

    public void setMenuCarrito(JMenu menuCarrito) {
        this.menuCarrito = menuCarrito;
    }

    public JMenuItem getMenuItemCrearCarrito() {
        return menuItemCrearCarrito;
    }

    public void setMenuItemCrearCarrito(JMenuItem menuItemCrearCarrito) {
        this.menuItemCrearCarrito = menuItemCrearCarrito;
    }

    public JDesktopPane getjDesktopPane() {
        return jDesktopPane;
    }

    public void setjDesktopPane(JDesktopPane jDesktopPane) {
        this.jDesktopPane = jDesktopPane;
    }

    public MensajeInternacionalizacionHandler getMensajeInternacionalizacionHandler() {
        return mensajeInternacionalizacionHandler;
    }

    public void setMensajeInternacionalizacionHandler(MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler) {
        this.mensajeInternacionalizacionHandler = mensajeInternacionalizacionHandler;
    }

    public JMenu getMenuIdioma() {
        return menuIdioma;
    }

    public void setMenuIdioma(JMenu menuIdioma) {
        this.menuIdioma = menuIdioma;
    }

    public JMenu getMenuSalir() {
        return menuSalir;
    }

    public void setMenuSalir(JMenu menuSalir) {
        this.menuSalir = menuSalir;
    }

    public JMenuItem getMenuItemIdiomaEspanol() {
        return menuItemIdiomaEspanol;
    }

    public void setMenuItemIdiomaEspanol(JMenuItem menuItemIdiomaEspanol) {
        this.menuItemIdiomaEspanol = menuItemIdiomaEspanol;
    }

    public JMenuItem getMenuItemIdiomaIngles() {
        return menuItemIdiomaIngles;
    }

    public void setMenuItemIdiomaIngles(JMenuItem menuItemIdiomaIngles) {
        this.menuItemIdiomaIngles = menuItemIdiomaIngles;
    }

    public JMenuItem getMenuItemIdiomaFrances() {
        return menuItemIdiomaFrances;
    }

    public void setMenuItemIdiomaFrances(JMenuItem menuItemIdiomaFrances) {
        this.menuItemIdiomaFrances = menuItemIdiomaFrances;
    }

    public JMenuItem getMenuItemSalir() {
        return menuItemSalir;
    }

    public void setMenuItemSalir(JMenuItem menuItemSalir) {
        this.menuItemSalir = menuItemSalir;
    }

    public JMenuItem getMenuItemCerrarSesion() {
        return menuItemCerrarSesion;
    }

    public void setMenuItemCerrarSesion(JMenuItem menuItemCerrarSesion) {
        this.menuItemCerrarSesion = menuItemCerrarSesion;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void cambiarIdioma(String lenguaje, String pais) {
        mensajeInternacionalizacionHandler.setLenguaje(lenguaje, pais);

        setTitle(mensajeInternacionalizacionHandler.get("app.titulo"));

        menuProducto.setText(mensajeInternacionalizacionHandler.get("menu.producto"));
        menuCarrito.setText(mensajeInternacionalizacionHandler.get("menu.carrito"));
        menuIdioma.setText(mensajeInternacionalizacionHandler.get("menu.idiomas"));
        menuSalir.setText(mensajeInternacionalizacionHandler.get("menu.salir"));

        menuItemCrearProducto.setText(mensajeInternacionalizacionHandler.get("menu.producto.crear"));
        menuItemEliminarProducto.setText(mensajeInternacionalizacionHandler.get("menu.producto.eliminar"));
        menuItemActualizarProducto.setText(mensajeInternacionalizacionHandler.get("menu.producto.actualizar"));
        menuItemBuscarProducto.setText(mensajeInternacionalizacionHandler.get("menu.producto.buscar"));

        menuItemCrearCarrito.setText(mensajeInternacionalizacionHandler.get("menu.carrito.crear"));

        menuItemIdiomaEspanol.setText(mensajeInternacionalizacionHandler.get("menu.idioma.es"));
        menuItemIdiomaIngles.setText(mensajeInternacionalizacionHandler.get("menu.idioma.en"));
        menuItemIdiomaFrances.setText(mensajeInternacionalizacionHandler.get("menu.idioma.fr"));

        menuItemSalir.setText(mensajeInternacionalizacionHandler.get("menu.salir.salir"));
        menuItemCerrarSesion.setText(mensajeInternacionalizacionHandler.get("menu.salir.cerrar"));
    }
}