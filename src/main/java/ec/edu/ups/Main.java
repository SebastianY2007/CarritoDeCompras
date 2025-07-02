package ec.edu.ups;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.dao.impl.CarritoDAOMemoria;
import ec.edu.ups.dao.impl.ProductoDAOMemoria;
import ec.edu.ups.dao.impl.UsuarioDAOMemoria;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.*;

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
                ProductoDAO productoDAO = new ProductoDAOMemoria();
                CarritoDAO carritoDAO = new CarritoDAOMemoria();

                MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler = new MensajeInternacionalizacionHandler();
                mensajeInternacionalizacionHandler.setLenguaje("es", "EC");

                LoginView loginView = new LoginView();
                loginView.inicializarEventos(usuarioDAO, productoDAO, carritoDAO, mensajeInternacionalizacionHandler);

                loginView.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        if (!loginView.isVisible()) {
                            if (!loginView.isDisplayable()) {
                                System.out.println("LoginView cerrado. Terminando aplicación.");
                                System.exit(0);
                            }
                        }
                    }

                    @Override
                    public void windowClosing(WindowEvent e) {
                        System.out.println("Ventana de Login cerrada. Saliendo de la aplicación.");
                        System.exit(0);
                    }
                });

                loginView.setVisible(true);
            }
        });
    }
}