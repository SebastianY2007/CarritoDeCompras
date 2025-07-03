package ec.edu.ups;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.dao.PreguntaSeguridadDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.dao.impl.ProductoDAOMemoria;
import ec.edu.ups.dao.impl.PreguntaSeguridadDAOMemoria;
import ec.edu.ups.dao.impl.UsuarioDAOMemoria;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.registro.LoginView;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // 1. Inicialización de DAOs y Handler
            UsuarioDAO usuarioDAO = new UsuarioDAOMemoria();
            ProductoDAO productoDAO = new ProductoDAOMemoria();
            PreguntaSeguridadDAO preguntaSeguridadDAO = new PreguntaSeguridadDAOMemoria(); // DAO necesario
            MensajeInternacionalizacionHandler mensajeHandler = new MensajeInternacionalizacionHandler();
            mensajeHandler.setLenguaje("es", "EC");

            // 2. Creación de la LoginView, pasándole todas las dependencias
            LoginView loginView = new LoginView(usuarioDAO, productoDAO, preguntaSeguridadDAO, mensajeHandler);

            // 3. Mostrar la ventana de login
            loginView.setVisible(true);
        });
    }
}
