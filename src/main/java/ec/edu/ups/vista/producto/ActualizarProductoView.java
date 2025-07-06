package ec.edu.ups.vista.producto;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

public class ActualizarProductoView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JComboBox<String> cbxElegir;
    private JTextField txtElegir;
    private JButton btnActualizar;
    private JButton btnCancelar;

    private MensajeInternacionalizacionHandler mensajeHandler;
    private ResourceBundle mensajes;
    private int idActual;

    public ActualizarProductoView() {
    }

    public ActualizarProductoView(MensajeInternacionalizacionHandler mensajeHandler) {
        this.mensajeHandler = mensajeHandler;

        setContentPane(panelPrincipal);
        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setIconifiable(true);
        setSize(400, 200);
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);

        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampoElegir();
            }
        });

        SwingUtilities.invokeLater(this::updateTexts);
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

    public int getIdActual() {
        return idActual;
    }

    public void setMensajeInternacionalizacionHandler(MensajeInternacionalizacionHandler mensajeHandler) {
        this.mensajeHandler = mensajeHandler;
        updateTexts();
    }

    public void setIdActual(int idActual) {
        this.idActual = idActual;
        limpiarCampoElegir();
    }

    public void limpiarCampoElegir() {
        if (txtElegir != null) {
            txtElegir.setText("");
        }
    }

    public void mostrarMensaje(String mensaje, int tipoMensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Mensaje", tipoMensaje);
    }

    public void updateTexts() {
        if (mensajeHandler == null) return;

        mensajes = ResourceBundle.getBundle("mensajes", new Locale(mensajeHandler.getLenguajeActual(), mensajeHandler.getPaisActual()));

        setTitle(mensajes.getString("actualizarProducto.titulo"));

        if (btnActualizar != null) {
            btnActualizar.setText(mensajes.getString("actualizarProducto.boton.actualizar"));
        }
        if (btnCancelar != null) {
            btnCancelar.setText(mensajes.getString("actualizarProducto.boton.cancelar"));
        }


        if (cbxElegir != null) {
            int selectedIndex = cbxElegir.getSelectedIndex();

            cbxElegir.removeAllItems();
            cbxElegir.addItem(mensajes.getString("actualizarProducto.opcion.nombre"));
            cbxElegir.addItem(mensajes.getString("actualizarProducto.opcion.precio"));

            if (selectedIndex >= 0 && selectedIndex < cbxElegir.getItemCount()) {
                cbxElegir.setSelectedIndex(selectedIndex);
            }
        }

        revalidate();
        repaint();
    }
}