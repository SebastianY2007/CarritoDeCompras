package ec.edu.ups;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.controlador.ProductoController;
import ec.edu.ups.controlador.UsuarioController;
import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.dao.impl.CarritoDAOMemoria;
import ec.edu.ups.dao.impl.ProductoDAOMemoria;
import ec.edu.ups.dao.impl.UsuarioDAOMemoria;
import ec.edu.ups.modelo.Producto; // Importar Producto para crear instancias de prueba
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                UsuarioDAO usuarioDAO = new UsuarioDAOMemoria() {
                    @Override
                    public List<Usuario> listarPorRol() {
                        return List.of();
                    }
                };

                LoginView loginView = new LoginView();
                loginView.setVisible(true);
                UsuarioController usuarioController = new UsuarioController(usuarioDAO, loginView);

                MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler = new MensajeInternacionalizacionHandler();
                mensajeInternacionalizacionHandler.setLenguaje("es", "EC");


                if (loginView.getBtnRegistrarse() != null) {
                    loginView.getBtnRegistrarse().addActionListener(e -> {
                        RegistroView registroView = new RegistroView(usuarioDAO);
                        registroView.setVisible(true);
                        registroView.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                loginView.setVisible(true);
                            }
                        });
                        loginView.setVisible(false);
                    });
                }


                loginView.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {

                        Usuario usuarioAuntenticado = usuarioController.getUsuarioAutenticado();
                        if (usuarioAuntenticado != null) {
                            ProductoDAO productoDAO = new ProductoDAOMemoria();
                            CarritoDAO carritoDAO = new CarritoDAOMemoria();

                            MenuPrincipalView principalView = new MenuPrincipalView(usuarioAuntenticado, mensajeInternacionalizacionHandler);
                            ProductoAnadirView productoAnadirView = new ProductoAnadirView();
                            ProductoListaView productoListaView = new ProductoListaView();
                            CarritoAnadirView carritoAnadirView = new CarritoAnadirView();


                            ProductoController productoController = new ProductoController(productoDAO, productoAnadirView, productoListaView, carritoAnadirView);
                            CarritoController carritoController = new CarritoController(carritoDAO, productoDAO, carritoAnadirView);

                            principalView.mostrarMensaje("Bienvenido: " + usuarioAuntenticado.getUsername());

                            principalView.getMenuItemCrearProducto().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if(!productoAnadirView.isVisible()){
                                        productoAnadirView.setVisible(true);
                                        principalView.getjDesktopPane().add(productoAnadirView);
                                    }
                                }
                            });

                            principalView.getMenuItemBuscarProducto().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if(!productoListaView.isVisible()){
                                        productoListaView.setVisible(true);
                                        principalView.getjDesktopPane().add(productoListaView);
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

                            principalView.setVisible(true);

                        } else {
                            System.out.println("LoginView cerrado sin autenticación exitosa. Terminando aplicación.");
                            System.exit(0);
                        }
                    }
                });
            }
        });
    }
}