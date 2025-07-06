package ec.edu.ups.vista.carrito;

import javax.swing.*;
import java.util.Locale;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import java.util.ResourceBundle;

public class ModificarCantidadProductoCarritoView extends JDialog {
    private JPanel panelPrincipal;
    private JComboBox<Integer> cbxCantidad;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private JLabel lblNuevaCantidad;
    private MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler;
    private ResourceBundle mensajes;

    public ModificarCantidadProductoCarritoView(JFrame parent) {
        super(parent, "Modificar Cantidad", true);
        this.setContentPane(panelPrincipal);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(parent);

        for (int i = 1; i <= 20; i++) {
            cbxCantidad.addItem(i);
        }
    }

    public void updateTexts() {
        this.mensajes = ResourceBundle.getBundle("mensajes", new Locale(mensajeInternacionalizacionHandler.getLenguajeActual(), mensajeInternacionalizacionHandler.getPaisActual()));

        ResourceBundle mensajes = mensajeInternacionalizacionHandler.getMensajes();
        setTitle(mensajes.getString("carrito.modificar.titulo"));
        lblNuevaCantidad.setText(mensajes.getString("carrito.modificar.lblNuevaCantidad"));
        btnGuardar.setText(mensajes.getString("global.boton.guardar"));
        btnCancelar.setText(mensajes.getString("global.boton.cancelar"));

        revalidate();
        repaint();
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public JComboBox<Integer> getCbxCantidad() {
        return cbxCantidad;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    public JLabel getLblNuevaCantidad() {
        return lblNuevaCantidad;
    }
}