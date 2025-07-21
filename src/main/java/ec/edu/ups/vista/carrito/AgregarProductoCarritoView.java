package ec.edu.ups.vista.carrito;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

/**
 * Clase AgregarProductoCarritoView
 *
 * Esta clase representa la vista (un JDialog modal) que permite al usuario
 * buscar un producto por código y agregarlo a un carrito de compras existente.
 *
 * @author Sebastian Yupangui
 * @version 1.0
 * @since 15/07/2025
 */
public class AgregarProductoCarritoView extends JDialog {
    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JLabel txtNombre;
    private JLabel txtPrecio;
    private JComboBox<Integer> cbxCantidad;
    private JButton btnAgregar;
    private JButton btnCancelar;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private JLabel lblCantidad;
    private MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler;
    private ResourceBundle mensajes;

    /**
     * Constructor de AgregarProductoCarritoView.
     *
     * @param parent El JFrame padre sobre el cual este diálogo será modal.
     */
    public AgregarProductoCarritoView(JFrame parent) {
        super(parent, "Agregar Producto al Carrito", true);
        this.setContentPane(panelPrincipal);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(parent);

        for (int i = 1; i <= 20; i++) {
            cbxCantidad.addItem(i);
        }

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
        java.net.URL urlIconoAgregar = getClass().getResource("/icons/icono_agregar_al_carrito.png");
        java.net.URL urlIconoCancelar = getClass().getResource("/icons/icono_cancelar.png");
        java.net.URL urlIconoBuscar = getClass().getResource("/icons/icono_buscar.png");

        if (urlIconoAgregar != null) {
            btnAgregar.setIcon(redimensionarIcono(new ImageIcon(urlIconoAgregar), 16, 16));
        }
        if (urlIconoCancelar != null) {
            btnCancelar.setIcon(redimensionarIcono(new ImageIcon(urlIconoCancelar), 16, 16));
        }
        if (urlIconoBuscar != null) {
            btnBuscar.setIcon(redimensionarIcono(new ImageIcon(urlIconoBuscar), 16, 16));
        }
    }

    /**
     * Actualiza los textos de la interfaz según el idioma seleccionado.
     */
    public void updateTexts() {
        this.mensajes = ResourceBundle.getBundle("mensajes", new Locale(mensajeInternacionalizacionHandler.getLenguajeActual(), mensajeInternacionalizacionHandler.getPaisActual()));
        setTitle(mensajes.getString("carrito.agregar.titulo"));
        lblCodigo.setText(mensajes.getString("carrito.label.codigo"));
        lblNombre.setText(mensajes.getString("carrito.label.nombre"));
        lblPrecio.setText(mensajes.getString("carrito.label.precio"));
        lblCantidad.setText(mensajes.getString("carrito.label.cantidad"));
        btnBuscar.setText(mensajes.getString("global.boton.buscar"));
        btnAgregar.setText(mensajes.getString("global.boton.agregar"));
        btnCancelar.setText(mensajes.getString("global.boton.cancelar"));

        revalidate();
        repaint();
    }

    // --- Getters para que el Controlador pueda acceder a los componentes ---

    public JTextField getTxtCodigo() { return txtCodigo; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JLabel getTxtNombre() { return txtNombre; }
    public JLabel getTxtPrecio() { return txtPrecio; }
    public JComboBox<Integer> getCbxCantidad() { return cbxCantidad; }
    public JButton getBtnAgregar() { return btnAgregar; }
    public JButton getBtnCancelar() { return btnCancelar; }
}
