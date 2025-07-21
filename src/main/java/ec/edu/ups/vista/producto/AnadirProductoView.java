package ec.edu.ups.vista.producto;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

/**
 * Clase AnadirProductoView
 *
 * Esta clase representa la vista (un JInternalFrame) que permite al administrador
 * añadir un nuevo producto al sistema, ingresando su código, nombre y precio.
 *
 * @author Sebastian Yupangui
 * @version 1.0
 * @since 15/07/2025
 */
public class AnadirProductoView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JButton btnAgregar;
    private JButton btnLimpiar;

    private MensajeInternacionalizacionHandler mensajeHandler;

    /**
     * Constructor de AnadirProductoView.
     *
     * @param handler El manejador de internacionalización para los textos de la UI.
     */
    public AnadirProductoView(MensajeInternacionalizacionHandler handler) {
        this.mensajeHandler = handler;

        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
        setSize(400, 250);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        updateTexts();
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
        java.net.URL urlIconoAgregar = getClass().getResource("/icons/icono_agregar_producto.png");
        java.net.URL urlIconoEliminar = getClass().getResource("/icons/icono_limpiar.png");

        if (urlIconoAgregar != null) {
            btnAgregar.setIcon(redimensionarIcono(new ImageIcon(urlIconoAgregar), 16, 16));
        }
        if (urlIconoEliminar != null) {
            btnLimpiar.setIcon(redimensionarIcono(new ImageIcon(urlIconoEliminar), 16, 16));
        }
    }

    /**
     * Actualiza los textos de la interfaz según el idioma seleccionado.
     */
    public void updateTexts() {
        ResourceBundle mensajes = mensajeHandler.getMensajes();

        setTitle(mensajes.getString("anadirProducto.titulo"));
        lblCodigo.setText(mensajes.getString("anadirProducto.label.codigo"));
        lblNombre.setText(mensajes.getString("anadirProducto.label.nombre"));
        lblPrecio.setText(mensajes.getString("anadirProducto.label.precio"));
        btnAgregar.setText(mensajes.getString("global.boton.agregar"));
        btnLimpiar.setText(mensajes.getString("global.boton.limpiar"));
    }

    // --- Getters para que el Controlador pueda acceder a los componentes ---

    /**
     * Obtiene el JTextField para el código del producto.
     * @return el JTextField del código.
     */
    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    /**
     * Obtiene el JTextField para el nombre del producto.
     * @return el JTextField del nombre.
     */
    public JTextField getTxtNombre() {
        return txtNombre;
    }

    /**
     * Obtiene el JTextField para el precio del producto.
     * @return el JTextField del precio.
     */
    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    /**
     * Obtiene el botón de agregar.
     * @return el JButton de agregar.
     */
    public JButton getBtnAgregar() {
        return btnAgregar;
    }

    /**
     * Obtiene el botón de limpiar.
     * @return el JButton de limpiar.
     */
    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }
}
