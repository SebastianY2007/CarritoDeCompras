package ec.edu.ups.vista.usuario;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import javax.swing.*;
import java.awt.event.ItemEvent;
import java.util.Locale;
import java.util.ResourceBundle;

public class ActualizarUsuarioView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JComboBox<String> cbxCampo;
    private JTextField txtNuevoValor;
    private JButton btnActualizar;
    private JButton btnCancelar;
    private JLabel lblCampoAActualizar;
    private JLabel lblNuevoValor;

    private MensajeInternacionalizacionHandler mensajeHandler;

    public ActualizarUsuarioView(MensajeInternacionalizacionHandler mensajeHandler) {
        this.mensajeHandler = mensajeHandler;

        setContentPane(panelPrincipal);
        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setIconifiable(true);
        setSize(400, 200);
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);

        cbxCampo.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                txtNuevoValor.setText("");
            }
        });

        SwingUtilities.invokeLater(this::updateTexts);
    }

    public JComboBox<String> getCbxCampo() {
        return cbxCampo;
    }

    public JTextField getTxtNuevoValor() {
        return txtNuevoValor;
    }

    public JButton getBtnActualizar() {
        return btnActualizar;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    public void setMensajeInternacionalizacionHandler(MensajeInternacionalizacionHandler mensajeHandler) {
        this.mensajeHandler = mensajeHandler;
        updateTexts();
    }

    public void updateTexts() {
        if (mensajeHandler == null) return;

        ResourceBundle mensajes = ResourceBundle.getBundle("mensajes", new Locale(mensajeHandler.getLenguajeActual(), mensajeHandler.getPaisActual()));

        setTitle(mensajes.getString("actualizarUsuario.titulo"));
        lblCampoAActualizar.setText(mensajes.getString("actualizarUsuario.label.seleccionar"));
        lblNuevoValor.setText(mensajes.getString("actualizarUsuario.label.nuevoValor"));
        btnActualizar.setText(mensajes.getString("actualizarUsuario.boton.actualizar"));
        btnCancelar.setText(mensajes.getString("actualizarUsuario.boton.cancelar"));

        if (cbxCampo != null) {
            int selectedIndex = cbxCampo.getSelectedIndex();

            cbxCampo.removeAllItems();
            cbxCampo.addItem(mensajes.getString("actualizarUsuario.opcion.username"));
            cbxCampo.addItem(mensajes.getString("actualizarUsuario.opcion.email"));
            cbxCampo.addItem(mensajes.getString("actualizarUsuario.opcion.rol"));

            if (selectedIndex >= 0 && selectedIndex < cbxCampo.getItemCount()) {
                cbxCampo.setSelectedIndex(selectedIndex);
            }
        }

        revalidate();
        repaint();
    }
}