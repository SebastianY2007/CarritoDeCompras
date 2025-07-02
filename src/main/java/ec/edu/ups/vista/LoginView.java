package ec.edu.ups.vista;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.controlador.ProductoController;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class LoginView extends JFrame {
    private JPanel panelPrincipal;
    private JTextField txtUsername;
    private JPasswordField txtContrasena;
    private JButton btnIniciarSesion;
    private JButton btnRegistrarse;
    private JButton btnOlvide;
    private JLabel lblUsuario;
    private JLabel lblContrasena;
    private JComboBox<String> cbxIdioma;

    private MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler;
    private UsuarioDAO usuarioDAO;
    private ProductoDAO productoDAO;
    private CarritoDAO carritoDAO;

    // Declaraciones de vistas a nivel de clase
    private ProductoAnadirView productoAnadirView;
    private GestionDeProductosView gestionDeProductosView;
    private CarritoAnadirView carritoAnadirView;
    private ActualizarProductoView actualizarProductoView;

    public LoginView() {
        setTitle("Iniciar Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        $$$setupUI$$$(); // Asume que esto inicializa los componentes de la GUI
        setContentPane(panelPrincipal);
    }

    public void inicializarEventos(UsuarioDAO usuarioDAO, ProductoDAO productoDAO, CarritoDAO carritoDAO, MensajeInternacionalizacionHandler msgHandler) {
        this.usuarioDAO = usuarioDAO;
        this.productoDAO = productoDAO;
        this.carritoDAO = carritoDAO;
        this.mensajeInternacionalizacionHandler = msgHandler;

        if (cbxIdioma != null) {
            populateLanguageComboBox();
            cbxIdioma.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String selectedLangName = (String) cbxIdioma.getSelectedItem();
                    if (selectedLangName != null) {
                        switch (selectedLangName) {
                            case "Español":
                                mensajeInternacionalizacionHandler.setLenguaje("es", "EC");
                                break;
                            case "English":
                                mensajeInternacionalizacionHandler.setLenguaje("en", "US");
                                break;
                            case "Français":
                                mensajeInternacionalizacionHandler.setLenguaje("fr", "FR");
                                break;
                            default:
                                System.err.println("Idioma no reconocido: " + selectedLangName);
                                break;
                        }
                        updateTexts();
                    }
                }
            });
        }
        updateTexts();

        if (btnRegistrarse != null) {
            btnRegistrarse.addActionListener(e -> {
                RegistroView registroView = new RegistroView(this.usuarioDAO);
                registroView.setVisible(true);
            });
        }

        if (btnIniciarSesion != null) {
            btnIniciarSesion.addActionListener(e -> {
                String user = txtUsername.getText();
                String pass = new String(txtContrasena.getPassword());

                if (user.isEmpty() || pass.isEmpty()) {
                    mostrarMensaje(mensajeInternacionalizacionHandler.get("login.mensaje.camposVacios"));
                    return;
                }

                System.out.println("DEBUG (LoginView): Intentando autenticar usuario: " + user);
                Usuario usuarioAutenticado = this.usuarioDAO.autenticar(user, pass);

                if (usuarioAutenticado != null) {
                    System.out.println("DEBUG (LoginView): Autenticación exitosa para: " + usuarioAutenticado.getUsername());
                    mostrarMensaje(mensajeInternacionalizacionHandler.get("login.mensaje.exito") + " " + usuarioAutenticado.getUsername());

                    System.out.println("DEBUG (LoginView): Instanciando vistas y controladores...");
                    // Instanciar vistas
                    productoAnadirView = new ProductoAnadirView();
                    gestionDeProductosView = new GestionDeProductosView();
                    carritoAnadirView = new CarritoAnadirView();
                    actualizarProductoView = new ActualizarProductoView();

                    // Pasar 'this' (LoginView) al constructor de MenuPrincipalView
                    MenuPrincipalView principalView = new MenuPrincipalView(usuarioAutenticado, mensajeInternacionalizacionHandler, this);

                    // Inicializar controladores con las vistas y el JDesktopPane
                    ProductoController productoController = new ProductoController(productoDAO, productoAnadirView, gestionDeProductosView, carritoAnadirView, principalView.getjDesktopPane());
                    CarritoController carritoController = new CarritoController(carritoDAO, productoDAO, carritoAnadirView);

                    // Pasar instancias de vistas y controlador a MenuPrincipalView
                    principalView.setGestionDeProductosView(gestionDeProductosView);
                    principalView.setProductoAnadirView(productoAnadirView);
                    principalView.setProductoController(productoController);

                    System.out.println("DEBUG (LoginView): Configuracion de listeners para MenuPrincipalView...");

                    principalView.getMenuItemCrearProducto().addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if(!productoAnadirView.isVisible()){
                                principalView.getjDesktopPane().add(productoAnadirView);
                                productoAnadirView.setVisible(true);
                            }
                            productoAnadirView.toFront();
                        }
                    });

                    principalView.getMenuItemGestionarProductos().addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (gestionDeProductosView != null && principalView.getjDesktopPane() != null) {
                                if (!gestionDeProductosView.isVisible()) {
                                    principalView.getjDesktopPane().add(gestionDeProductosView);
                                    gestionDeProductosView.setVisible(true);
                                    if (productoController != null) {
                                        productoController.listarProductos();
                                    }
                                }
                                gestionDeProductosView.toFront();
                            } else {
                                System.err.println("Error: gestionDeProductosView o jDesktopPane no inicializados en listener de menuItemGestionarProductos en LoginView.");
                            }
                        }
                    });

                    principalView.getMenuItemCrearCarrito().addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if(!carritoAnadirView.isVisible()){
                                carritoAnadirView.setVisible(true);
                                principalView.getjDesktopPane().add(carritoAnadirView);
                            }
                            carritoAnadirView.toFront();
                        }
                    });

                    principalView.getMenuItemIdiomaEspanol().addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            principalView.cambiarIdioma("es", "EC");
                        }
                    });

                    principalView.getMenuItemIdiomaIngles().addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            principalView.cambiarIdioma("en", "US");
                        }
                    });

                    principalView.getMenuItemIdiomaFrances().addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            principalView.cambiarIdioma("fr", "FR");
                        }
                    });

                    System.out.println("DEBUG (LoginView): Intentando mostrar MenuPrincipalView y ocultar LoginView...");
                    principalView.setVisible(true); // Esta línea muestra la ventana principal
                    this.setVisible(false); // Esta línea oculta la ventana de login
                    System.out.println("DEBUG (LoginView): Se llamó a setVisible(true) para MenuPrincipalView y setVisible(false) para LoginView.");

                } else {
                    System.out.println("DEBUG (LoginView): Autenticación fallida.");
                    mostrarMensaje(mensajeInternacionalizacionHandler.get("login.mensaje.credencialesIncorrectas"));
                }
                Arrays.fill(txtContrasena.getPassword(), ' ');
            });
        }

        if (btnOlvide != null) {
            btnOlvide.addActionListener(e -> {
                RecuperarCuentaView recuperarCuentaView = new RecuperarCuentaView(this.usuarioDAO);
                recuperarCuentaView.setVisible(true);
            });
        }
    }

    private void populateLanguageComboBox() {
        if (cbxIdioma == null || mensajeInternacionalizacionHandler == null) return;

        cbxIdioma.removeAllItems();
        cbxIdioma.addItem("Español");
        cbxIdioma.addItem("English");
        cbxIdioma.addItem("Français");

        String currentLanguageCode = mensajeInternacionalizacionHandler.getLenguajeActual();
        String currentCountryCode = mensajeInternacionalizacionHandler.getPaisActual();

        if ("es".equalsIgnoreCase(currentLanguageCode) && "EC".equalsIgnoreCase(currentCountryCode)) {
            cbxIdioma.setSelectedItem("Español");
        } else if ("en".equalsIgnoreCase(currentLanguageCode) && "US".equalsIgnoreCase(currentCountryCode)) {
            cbxIdioma.setSelectedItem("English");
        } else if ("fr".equalsIgnoreCase(currentLanguageCode) && "FR".equalsIgnoreCase(currentCountryCode)) {
            cbxIdioma.setSelectedItem("Français");
        } else {
            cbxIdioma.setSelectedIndex(0);
        }
    }

    public void updateTexts() {
        if (mensajeInternacionalizacionHandler == null) return;

        setTitle(mensajeInternacionalizacionHandler.get("login.title"));

        if (lblUsuario != null) lblUsuario.setText(mensajeInternacionalizacionHandler.get("login.label.username"));
        if (lblContrasena != null) lblContrasena.setText(mensajeInternacionalizacionHandler.get("login.label.password"));
        if (btnIniciarSesion != null) btnIniciarSesion.setText(mensajeInternacionalizacionHandler.get("login.button.login"));
        if (btnRegistrarse != null) btnRegistrarse.setText(mensajeInternacionalizacionHandler.get("login.button.register"));
        if (btnOlvide != null) btnOlvide.setText(mensajeInternacionalizacionHandler.get("login.button.forgot"));

        if (panelPrincipal != null) {
            panelPrincipal.revalidate();
            panelPrincipal.repaint();
        }
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    // Getters and setters (omitted for brevity, assume they exist if your GUI designer uses them)
    public JPanel getPanelPrincipal() { return panelPrincipal; }
    public void setPanelPrincipal(JPanel panelPrincipal) { this.panelPrincipal = panelPrincipal; }
    public JTextField getTxtUsername() { return txtUsername; }
    public void setTxtUsername(JTextField txtUsername) { this.txtUsername = txtUsername; }
    public JPasswordField getTxtContrasena() { return txtContrasena; }
    public void setTxtContrasena(JPasswordField txtContrasena) { this.txtContrasena = txtContrasena; }
    public JButton getBtnIniciarSesion() { return btnIniciarSesion; }
    public void setBtnIniciarSesion(JButton btnIniciarSesion) { this.btnIniciarSesion = btnIniciarSesion; }
    public JButton getBtnRegistrarse() { return btnRegistrarse; }
    public void setBtnRegistrarse(JButton btnRegistrarse) { this.btnRegistrarse = btnRegistrarse; }
    public JButton getBtnOlvide() { return btnOlvide; }
    public void setBtnOlvide(JButton btnOlvide) { this.btnOlvide = btnOlvide; }
    public JLabel getLblUsuario() { return lblUsuario; }
    public void setLblUsuario(JLabel lblUsuario) { this.lblUsuario = lblUsuario; }
    public JLabel getLblContrasena() { return lblContrasena; }
    public void setLblContrasena(JLabel lblContrasena) { this.lblContrasena = lblContrasena; }
    public JComboBox<String> getCbxIdioma() { return cbxIdioma; }
    public void setCbxIdioma(JComboBox<String> cbxIdioma) { this.cbxIdioma = cbxIdioma; }

    private void $$$setupUI$$$() {
        // Método generado por el diseñador de GUI, no modificar
    }
    private void createUIComponents() {
        // Método para la creación de componentes personalizados si es necesario
    }
}