package ec.edu.ups;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.dao.PreguntaSeguridadDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.dao.impl.CarritoDAOMemoria;
import ec.edu.ups.dao.impl.ProductoDAOMemoria;
import ec.edu.ups.dao.impl.PreguntaSeguridadDAOMemoria;
import ec.edu.ups.dao.impl.UsuarioDAOMemoria;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.registro.LoginView;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UsuarioDAO usuarioDAO = new UsuarioDAOMemoria();
            ProductoDAO productoDAO = new ProductoDAOMemoria();
            PreguntaSeguridadDAO preguntaSeguridadDAO = new PreguntaSeguridadDAOMemoria();
            CarritoDAO carritoDAO = new CarritoDAOMemoria();

            MensajeInternacionalizacionHandler mensajeHandler = new MensajeInternacionalizacionHandler();
            mensajeHandler.setLenguaje("es", "EC");

            LoginView loginView = new LoginView(usuarioDAO, productoDAO, carritoDAO, preguntaSeguridadDAO, mensajeHandler);

            loginView.setVisible(true);
        });
    }
}