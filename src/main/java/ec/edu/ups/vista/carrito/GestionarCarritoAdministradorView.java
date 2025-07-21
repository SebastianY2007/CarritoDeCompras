package ec.edu.ups.vista.carrito;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Clase GestionarCarritoAdministradorView
 *
 * Esta clase representa la vista (un JInternalFrame) que permite a un
 * administrador ver y gestionar todos los carritos de compra del sistema.
 *
 * @author Sebastian Yupangui
 * @version 1.0
 * @since 15/07/2025
 */
public class GestionarCarritoAdministradorView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTable tblCarritos;
    private JButton btnListar;
    private MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler;
    private ResourceBundle mensajes;
    private DefaultTableModel tableModel;

    /**
     * Constructor de GestionarCarritoAdministradorView.
     *
     * Inicializa la ventana interna y sus componentes.
     */
    public GestionarCarritoAdministradorView() {
        setTitle("Gestion de Carritos (Administrador)");
        setContentPane(panelPrincipal);
        setSize(600, 450);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        configurarIconos();
    }

    /**
     * Redimensiona un icono a un tamaño específico.
     * @param icono El ImageIcon original.
     * @param ancho El nuevo ancho del icono.
     * @param alto El nuevo alto del icono.
     * @return un nuevo ImageIcon redimensionado.
     */
    private ImageIcon redimensionarIcono(ImageIcon icono, int ancho, int alto) {
        Image imagen = icono.getImage();
        Image imagenRedimensionada = imagen.getScaledInstance(ancho, alto, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(imagenRedimensionada);
    }

    /**
     * Configura los iconos para los botones de la vista.
     */
    private void configurarIconos() {
        java.net.URL urlIconoListar = getClass().getResource("/icons/icono_listar.png");

        if (urlIconoListar != null) {
            btnListar.setIcon(redimensionarIcono(new ImageIcon(urlIconoListar), 16, 16));
        }
    }

    /**
     * Configura el modelo de la tabla de carritos.
     */
    private void configurarTabla() {
        tableModel = new DefaultTableModel();
        tblCarritos.setModel(tableModel);
    }

    /**
     * Actualiza los textos de la interfaz según el idioma seleccionado.
     */
    public void updateTexts() {
        this.mensajes = ResourceBundle.getBundle("mensajes", new Locale(mensajeInternacionalizacionHandler.getLenguajeActual(), mensajeInternacionalizacionHandler.getPaisActual()));

        setTitle(mensajes.getString("carrito.gestionarAdmin.titulo"));
        btnListar.setText(mensajes.getString("global.boton.listar"));

        tableModel.setColumnIdentifiers(new Object[]{
                mensajes.getString("carrito.gestionarAdmin.tabla.codigo"),
                mensajes.getString("carrito.gestionarAdmin.tabla.usuario"),
                mensajes.getString("carrito.gestionarAdmin.tabla.fecha"),
                mensajes.getString("carrito.gestionarAdmin.tabla.total")
        });

        revalidate();
        repaint();
    }

    // --- Getters para que el Controlador pueda acceder a los componentes ---

    public JTable getTblCarritos() { return tblCarritos; }
    public JButton getBtnListar() { return btnListar; }
}
