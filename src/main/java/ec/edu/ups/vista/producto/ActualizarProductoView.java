package ec.edu.ups.vista.producto;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import javax.swing.*;
import java.util.ResourceBundle;

public class ActualizarProductoView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JComboBox<String> cbxElegir;
    private JTextField txtElegir;
    private JButton btnActualizar;
    private JButton btnCancelar;

    private MensajeInternacionalizacionHandler mensajeHandler;

    public ActualizarProductoView(MensajeInternacionalizacionHandler handler) {
        this.mensajeHandler = handler;

        setContentPane(panelPrincipal);
        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setIconifiable(true);
        setSize(400, 200);
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);

        updateTexts();
    }

    public void updateTexts() {
        ResourceBundle mensajes = mensajeHandler.getMensajes();

        setTitle(mensajes.getString("actualizarProducto.titulo"));
        btnActualizar.setText(mensajes.getString("actualizarProducto.boton.actualizar"));
        btnCancelar.setText(mensajes.getString("global.boton.cancelar"));

        if (cbxElegir != null) {
            int selectedIndex = cbxElegir.getSelectedIndex();

            cbxElegir.removeAllItems();
            cbxElegir.addItem(mensajes.getString("actualizarProducto.opcion.nombre"));
            cbxElegir.addItem(mensajes.getString("actualizarProducto.opcion.precio"));

            if (selectedIndex >= 0 && selectedIndex < cbxElegir.getItemCount()) {
                cbxElegir.setSelectedIndex(selectedIndex);
            }
        }
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public JComboBox<String> getCbxElegir() {
        return cbxElegir;
    }

    public JTextField getTxtElegir() {
        return txtElegir;
    }

    public JButton getBtnActualizar() {
        return btnActualizar;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    public MensajeInternacionalizacionHandler getMensajeHandler() {
        return mensajeHandler;
    }
}