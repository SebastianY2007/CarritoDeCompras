package ec.edu.ups.vista.producto;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import javax.swing.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class AnadirProductoView extends JInternalFrame {

    // --- Componentes de la UI (Deben coincidir con tu .form) ---
    private JPanel panelPrincipal;
    private JLabel codigoLabel;
    private JLabel nombreLabel;
    private JLabel precioLabel;
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JButton btnAgregar;
    private JButton btnLimpiar;

    // --- Dependencias ---
    private MensajeInternacionalizacionHandler mensajeHandler;
    private ResourceBundle mensajes;

    public AnadirProductoView(MensajeInternacionalizacionHandler mensajeHandler) {
        this.mensajeHandler = mensajeHandler;

        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
        setSize(400, 250);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        // La configuración de textos se hará al final para evitar errores
        SwingUtilities.invokeLater(this::updateTexts);
    }

    // --- Getters para que el controlador acceda a los componentes ---
    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    public JButton getBtnAgregar() {
        return btnAgregar;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    // --- Métodos de la Vista ---
    public void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
    }

    public void mostrarMensaje(String mensaje, int tipoDeMensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Mensaje del Sistema", tipoDeMensaje);
    }

    public void updateTexts() {
        if (mensajeHandler == null) return;
        mensajes = ResourceBundle.getBundle("mensajes", new Locale(mensajeHandler.getLenguajeActual(), mensajeHandler.getPaisActual()));

        setTitle(mensajes.getString("anadirProducto.titulo"));
        codigoLabel.setText(mensajes.getString("anadirProducto.label.codigo"));
        nombreLabel.setText(mensajes.getString("anadirProducto.label.nombre"));
        precioLabel.setText(mensajes.getString("anadirProducto.label.precio"));
        btnAgregar.setText(mensajes.getString("anadirProducto.boton.agregar"));
        btnLimpiar.setText(mensajes.getString("anadirProducto.boton.limpiar"));

        revalidate();
        repaint();
    }
}
