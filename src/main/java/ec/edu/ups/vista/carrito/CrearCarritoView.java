package ec.edu.ups.vista.carrito;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

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

    private ImageIcon redimensionarIcono(ImageIcon icono, int ancho, int alto) {
        Image imagen = icono.getImage();
        Image imagenRedimensionada = imagen.getScaledInstance(ancho, alto, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(imagenRedimensionada);
    }

    private void configurarIconos() {
        java.net.URL urlIconoAgregar = getClass().getResource("/icons/icono_agregar_al_carrito.png");
        java.net.URL urlIconoCrearCarrito = getClass().getResource("/icons/icono_crear_carrito.png");
        java.net.URL urlIconoBuscar = getClass().getResource("/icons/icono_buscar.png");

        if (urlIconoAgregar != null) {
            ImageIcon iconoOriginal = new ImageIcon(urlIconoAgregar);

            ImageIcon iconoAjustado = redimensionarIcono(iconoOriginal, 16, 16);

            btnAgregar.setIcon(iconoAjustado);
        } else {
            System.err.println("Icono no encontrado: /icons/icono_modificar.png");
        }

        if (urlIconoCrearCarrito != null) {
            ImageIcon iconoOriginal = new ImageIcon(urlIconoCrearCarrito);

            ImageIcon iconoAjustado = redimensionarIcono(iconoOriginal, 16, 16);

            btnCrearCarrito.setIcon(iconoAjustado);
        } else {
            System.err.println("Icono no encontrado: /icons/icono_crear_carrito.png");
        }

        if (urlIconoBuscar != null) {
            ImageIcon iconoOriginal = new ImageIcon(urlIconoBuscar);

            ImageIcon iconoAjustado = redimensionarIcono(iconoOriginal, 16, 16);

            btnBuscar.setIcon(iconoAjustado);
        } else {
            System.err.println("Icono no encontrado: /icons/icono_buscar.png");
        }
    }

    private void configurarTabla() {
        tableModel = new DefaultTableModel();
        tblProductos.setModel(tableModel);
    }

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


    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JComboBox<Integer> getCbxCantidad() {
        return cbxCantidad;
    }

    public JButton getBtnAgregar() {
        return btnAgregar;
    }

    public JTable getTblProductos() {
        return tblProductos;
    }

    public JButton getBtnCrearCarrito() {
        return btnCrearCarrito;
    }

    public JLabel getTxtNombre() {
        return txtNombre;
    }

    public JLabel getTxtPrecio() {
        return txtPrecio;
    }

    public JLabel getTxtSubtotal() {
        return txtSubtotal;
    }

    public JLabel getTxtIVA() {
        return txtIVA;
    }

    public JLabel getTxtTotal() {
        return txtTotal;
    }

    public JLabel getLblCodigo() {
        return lblCodigo;
    }

    public JLabel getLblNombre() {
        return lblNombre;
    }

    public JLabel getLblPrecio() {
        return lblPrecio;
    }

    public JLabel getLblCantidad() {
        return lblCantidad;
    }

    public JLabel getLblSubtotal() {
        return lblSubtotal;
    }

    public JLabel getLblIVA() {
        return lblIVA;
    }

    public JLabel getLblTotal() {
        return lblTotal;
    }
}