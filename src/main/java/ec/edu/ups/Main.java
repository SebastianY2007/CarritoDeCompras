package ec.edu.ups;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.dao.PreguntaSeguridadDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.dao.FabricaDAO; // O la ruta del paquete donde la creaste
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.registro.LoginView;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        // Ejecutar en el hilo de despacho de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            try {
                // 1. Mostrar diálogo para elegir el tipo de almacenamiento
                String[] opciones = {"Memoria", "Archivos de Texto", "Archivos Binarios"};
                String eleccion = (String) JOptionPane.showInputDialog(
                        null,
                        "Seleccione el método de almacenamiento:",
                        "Configuración de Almacenamiento",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        opciones,
                        opciones[0]
                );

                // Si el usuario cierra el diálogo, se termina la aplicación
                if (eleccion == null) {
                    System.exit(0);
                }

                int tipoAlmacenamiento;
                String rutaBase = null;

                // 2. Determinar el tipo y pedir la ruta si es necesario
                switch (eleccion) {
                    case "Archivos de Texto":
                        tipoAlmacenamiento = FabricaDAO.ARCHIVOS_TEXTO;
                        rutaBase = seleccionarRuta();
                        break;
                    case "Archivos Binarios":
                        tipoAlmacenamiento = FabricaDAO.ARCHIVOS_BINARIOS;
                        rutaBase = seleccionarRuta();
                        break;
                    default:
                        tipoAlmacenamiento = FabricaDAO.MEMORIA;
                        break;
                }

                // Si el usuario cancela la selección de ruta, se termina la aplicación
                if (tipoAlmacenamiento != FabricaDAO.MEMORIA && rutaBase == null) {
                    System.exit(0);
                }

                // 3. Crear la fábrica y los DAOs
                FabricaDAO factory = FabricaDAO.getFactory(tipoAlmacenamiento, rutaBase);
                UsuarioDAO usuarioDAO = factory.getUsuarioDAO();
                ProductoDAO productoDAO = factory.getProductoDAO();
                CarritoDAO carritoDAO = factory.getCarritoDAO();
                PreguntaSeguridadDAO preguntaSeguridadDAO = factory.getPreguntaSeguridadDAO();

                // 4. Iniciar la aplicación con los DAOs correctos
                MensajeInternacionalizacionHandler mensajeHandler = new MensajeInternacionalizacionHandler();
                LoginView loginView = new LoginView(usuarioDAO, productoDAO, carritoDAO, preguntaSeguridadDAO, mensajeHandler);
                loginView.setVisible(true);

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Ocurrió un error al iniciar la aplicación.", "Error Crítico", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    // Método auxiliar para mostrar el selector de carpetas
    private static String seleccionarRuta() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccione la Carpeta de Almacenamiento");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);

        int resultado = fileChooser.showOpenDialog(null);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }
        return null; // El usuario canceló
    }
}