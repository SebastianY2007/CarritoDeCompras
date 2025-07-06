package ec.edu.ups.vista.producto;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import javax.swing.*;
import java.util.ResourceBundle;

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

    public AnadirProductoView(MensajeInternacionalizacionHandler handler) {
        this.mensajeHandler = handler;

        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
        setSize(400, 250);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        updateTexts();
    }

    public void updateTexts() {
        ResourceBundle mensajes = mensajeHandler.getMensajes();

        setTitle(mensajes.getString("anadirProducto.titulo"));
        lblCodigo.setText(mensajes.getString("anadirProducto.label.codigo"));
        lblNombre.setText(mensajes.getString("anadirProducto.label.nombre"));
        lblPrecio.setText(mensajes.getString("anadirProducto.label.precio"));
        btnAgregar.setText(mensajes.getString("global.boton.agregar"));
        btnLimpiar.setText(mensajes.getString("global.boton.limpiar"));
    }

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
}