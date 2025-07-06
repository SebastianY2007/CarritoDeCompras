package ec.edu.ups.vista.usuario;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import javax.swing.*;
import java.awt.*;
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

        miInitComponents();

        setContentPane(panelPrincipal);
        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setIconifiable(true);
        setSize(400, 200);
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);

        if (cbxCampo != null) {
            cbxCampo.addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    txtNuevoValor.setText("");
                }
            });
        }

        SwingUtilities.invokeLater(this::updateTexts);
    }

    private void miInitComponents() {
        panelPrincipal = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        lblCampoAActualizar = new JLabel("Campo a Actualizar:");
        lblNuevoValor = new JLabel("Nuevo Valor:");

        cbxCampo = new JComboBox<>();
        txtNuevoValor = new JTextField(20);
        btnActualizar = new JButton("Actualizar");
        btnCancelar = new JButton("Cancelar");

        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        panelPrincipal.add(lblCampoAActualizar, gbc);

        gbc.gridx = 1; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL;
        panelPrincipal.add(cbxCampo, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.WEST;
        panelPrincipal.add(lblNuevoValor, gbc);

        gbc.gridx = 1; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        panelPrincipal.add(txtNuevoValor, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnActualizar);
        panelBotones.add(btnCancelar);
        panelPrincipal.add(panelBotones, gbc);

        cbxCampo.addItem("Username");
        cbxCampo.addItem("Email");
        cbxCampo.addItem("Rol");
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

        if (lblCampoAActualizar != null)
            lblCampoAActualizar.setText(mensajes.getString("actualizarUsuario.label.seleccionar"));
        if (lblNuevoValor != null)
            lblNuevoValor.setText(mensajes.getString("actualizarUsuario.label.nuevoValor"));
        if (btnActualizar != null)
            btnActualizar.setText(mensajes.getString("actualizarUsuario.boton.actualizar"));
        if (btnCancelar != null)
            btnCancelar.setText(mensajes.getString("actualizarUsuario.boton.cancelar"));

        if (cbxCampo != null) {
            int selectedIndex = cbxCampo.getSelectedIndex();

            cbxCampo.removeAllItems();
            cbxCampo.addItem(mensajes.getString("actualizarUsuario.opcion.username"));
            cbxCampo.addItem(mensajes.getString("actualizarUsuario.opcion.email"));
            cbxCampo.addItem(mensajes.getString("actualizarUsuario.opcion.rol"));

            if (selectedIndex >= 0 && selectedIndex < cbxCampo.getItemCount()) {
                cbxCampo.setSelectedIndex(selectedIndex);
            } else if (cbxCampo.getItemCount() > 0) {
                cbxCampo.setSelectedIndex(0);
            }
        }

        revalidate();
        repaint();
    }
}