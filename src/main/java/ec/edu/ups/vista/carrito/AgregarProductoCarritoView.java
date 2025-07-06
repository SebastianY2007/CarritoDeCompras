package ec.edu.ups.vista.carrito;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

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

    private ImageIcon redimensionarIcono(ImageIcon icono, int ancho, int alto) {
        Image imagen = icono.getImage();
        Image imagenRedimensionada = imagen.getScaledInstance(ancho, alto, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(imagenRedimensionada);
    }

    private void configurarIconos() {
        java.net.URL urlIconoAgregar = getClass().getResource("/icons/icono_agregar_al_carrito.png");
        java.net.URL urlIconoCancelar = getClass().getResource("/icons/icono_cancelar.png");
        java.net.URL urlIconoBuscar = getClass().getResource("/icons/icono_buscar.png");

        if (urlIconoAgregar != null) {
            ImageIcon iconoOriginal = new ImageIcon(urlIconoAgregar);

            ImageIcon iconoAjustado = redimensionarIcono(iconoOriginal, 16, 16);

            btnAgregar.setIcon(iconoAjustado);
        } else {
            System.err.println("Icono no encontrado: /icons/icono_modificar.png");
        }

        if (urlIconoCancelar != null) {
            ImageIcon iconoOriginal = new ImageIcon(urlIconoCancelar);

            ImageIcon iconoAjustado = redimensionarIcono(iconoOriginal, 16, 16);

            btnCancelar.setIcon(iconoAjustado);
        } else {
            System.err.println("Icono no encontrado: /icons/icono_cancelar.png");
        }

        if (urlIconoBuscar != null) {
            ImageIcon iconoOriginal = new ImageIcon(urlIconoBuscar);

            ImageIcon iconoAjustado = redimensionarIcono(iconoOriginal, 16, 16);

            btnBuscar.setIcon(iconoAjustado);
        } else {
            System.err.println("Icono no encontrado: /icons/icono_buscar.png");
        }
    }

    public void updateTexts() {
        this.mensajes = ResourceBundle.getBundle("mensajes", new Locale(mensajeInternacionalizacionHandler.getLenguajeActual(), mensajeInternacionalizacionHandler.getPaisActual()));
        setTitle(mensajes.getString("agregar.titulo"));

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

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JLabel getTxtNombre() {
        return txtNombre;
    }

    public JLabel getTxtPrecio() {
        return txtPrecio;
    }

    public JComboBox<Integer> getCbxCantidad() {
        return cbxCantidad;
    }

    public JButton getBtnAgregar() {
        return btnAgregar;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
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

    public MensajeInternacionalizacionHandler getMensajeInternacionalizacionHandler() {
        return mensajeInternacionalizacionHandler;
    }
}