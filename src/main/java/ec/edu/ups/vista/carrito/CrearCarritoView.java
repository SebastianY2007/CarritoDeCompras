package ec.edu.ups.vista.carrito;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

/**
 * Clase CrearCarritoView
 *
 * Esta clase representa la vista (un JInternalFrame) para la creación de un
 * nuevo carrito de compras. Permite al usuario buscar productos, añadirlos
 * al carrito y finalmente guardar el carrito completo.
 *
 * @author Sebastian Yupangui
 * @version 1.0
 * @since 15/07/2025
 */
public class CrearCarritoView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JComboBox<Integer> cbxCantidad;
    private JButton btnAgregar;
    private JTable tblProductos;
    private JButton btnCrearCarrito;
    private JLabel txtNombre;
    private JLabel txtPrecio;
    private JLabel txtSubtotal;
    private JLabel txtIVA;
    private JLabel txtTotal;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private JLabel lblCantidad;
    private JLabel lblSubtotal;
    private JLabel lblIVA;
    private JLabel lblTotal;
    private MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler;
    private ResourceBundle mensajes;
    private DefaultTableModel tableModel;

    /**
     * Constructor de CrearCarritoView.
     *
     * Inicializa la ventana interna, sus componentes y configura los iconos.
     */
    public CrearCarritoView() {
        setTitle("Crear Carrito");
        setContentPane(panelPrincipal);
        setSize(600, 500);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

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
        java.net.URL urlIconoCrearCarrito = getClass().getResource("/icons/icono_crear_carrito.png");
        java.net.URL urlIconoBuscar = getClass().getResource("/icons/icono_buscar.png");

        if (urlIconoAgregar != null) {
            btnAgregar.setIcon(redimensionarIcono(new ImageIcon(urlIconoAgregar), 16, 16));
        }
        if (urlIconoCrearCarrito != null) {
            btnCrearCarrito.setIcon(redimensionarIcono(new ImageIcon(urlIconoCrearCarrito), 16, 16));
        }
        if (urlIconoBuscar != null) {
            btnBuscar.setIcon(redimensionarIcono(new ImageIcon(urlIconoBuscar), 16, 16));
        }
    }

    /**
     * Configura el modelo de la tabla de productos.
     */
    private void configurarTabla() {
        tableModel = new DefaultTableModel();
        tblProductos.setModel(tableModel);
    }

    /**
     * Actualiza los textos de la interfaz según el idioma seleccionado.
     */
    public void updateTexts() {
        this.mensajes = ResourceBundle.getBundle("mensajes", new Locale(mensajeInternacionalizacionHandler.getLenguajeActual(), mensajeInternacionalizacionHandler.getPaisActual()));

        setTitle(mensajes.getString("carrito.crear.titulo"));
        lblCodigo.setText(mensajes.getString("carrito.label.codigo"));
        lblCantidad.setText(mensajes.getString("carrito.label.cantidad"));
        lblNombre.setText(mensajes.getString("carrito.label.nombre"));
        lblPrecio.setText(mensajes.getString("carrito.label.precio"));
        lblSubtotal.setText(mensajes.getString("carrito.label.subtotal"));
        lblIVA.setText(mensajes.getString("carrito.label.iva"));
        lblTotal.setText(mensajes.getString("carrito.label.total"));

        btnBuscar.setText(mensajes.getString("carrito.boton.buscar"));
        btnAgregar.setText(mensajes.getString("carrito.boton.anadir"));
        btnCrearCarrito.setText(mensajes.getString("carrito.boton.guardar"));

        tableModel.setColumnIdentifiers(new Object[]{
                mensajes.getString("carrito.tabla.codigo"),
                mensajes.getString("carrito.tabla.nombre"),
                mensajes.getString("carrito.tabla.precioUnitario"),
                mensajes.getString("carrito.tabla.cantidad"),
                mensajes.getString("carrito.tabla.subtotal")
        });

        revalidate();
        repaint();
    }

    // --- Getters para que el Controlador pueda acceder a los componentes ---

    public JTextField getTxtCodigo() { return txtCodigo; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JComboBox<Integer> getCbxCantidad() { return cbxCantidad; }
    public JButton getBtnAgregar() { return btnAgregar; }
    public JTable getTblProductos() { return tblProductos; }
    public JButton getBtnCrearCarrito() { return btnCrearCarrito; }
    public JLabel getTxtNombre() { return txtNombre; }
    public JLabel getTxtPrecio() { return txtPrecio; }
    public JLabel getTxtSubtotal() { return txtSubtotal; }
    public JLabel getTxtIVA() { return txtIVA; }
    public JLabel getTxtTotal() { return txtTotal; }
}
