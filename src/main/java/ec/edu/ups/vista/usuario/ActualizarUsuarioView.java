package ec.edu.ups.vista.usuario;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.ResourceBundle;

public class ActualizarUsuarioView extends JInternalFrame {

    private JComboBox<String> cbxCampo;
    private JTextField txtNuevoValor;
    private JButton btnActualizar;
    private JButton btnCancelar;
    private JLabel lblCampoAActualizar;
    private JLabel lblNuevoValor;

    private MensajeInternacionalizacionHandler mensajeHandler;

    public ActualizarUsuarioView(MensajeInternacionalizacionHandler handler) {
        this.mensajeHandler = handler;

        configurarLayout();

        setContentPane(this.getContentPane());
        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setIconifiable(true);
        setSize(450, 200);
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);

        cbxCampo.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                txtNuevoValor.setText("");
            }
        });

        updateTexts();
    }

    private void configurarLayout() {
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        lblCampoAActualizar = new JLabel();
        lblNuevoValor = new JLabel();
        cbxCampo = new JComboBox<>();
        txtNuevoValor = new JTextField(20);
        btnActualizar = new JButton();
        btnCancelar = new JButton();

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.3; panelPrincipal.add(lblCampoAActualizar, gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.7; panelPrincipal.add(cbxCampo, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panelPrincipal.add(lblNuevoValor, gbc);
        gbc.gridx = 1; gbc.gridy = 1; panelPrincipal.add(txtNuevoValor, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotones.add(btnActualizar);
        panelBotones.add(btnCancelar);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.weightx = 1.0;
        panelPrincipal.add(panelBotones, gbc);

        this.setLayout(new BorderLayout());
        this.add(panelPrincipal, BorderLayout.CENTER);
    }

    public void updateTexts() {
        ResourceBundle mensajes = mensajeHandler.getMensajes();

        setTitle(mensajes.getString("actualizarUsuario.titulo"));
        lblCampoAActualizar.setText(mensajes.getString("actualizarUsuario.label.seleccionar"));
        lblNuevoValor.setText(mensajes.getString("actualizarUsuario.label.nuevoValor"));
        btnActualizar.setText(mensajes.getString("global.boton.actualizar"));
        btnCancelar.setText(mensajes.getString("global.boton.cancelar"));

        int selectedIndex = cbxCampo.getSelectedIndex();
        cbxCampo.removeAllItems();
        cbxCampo.addItem(mensajes.getString("actualizarUsuario.opcion.username"));
        cbxCampo.addItem(mensajes.getString("actualizarUsuario.opcion.email"));
        cbxCampo.addItem(mensajes.getString("actualizarUsuario.opcion.rol"));

        if (selectedIndex >= 0 && selectedIndex < cbxCampo.getItemCount()) {
            cbxCampo.setSelectedIndex(selectedIndex);
        }
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

    public JLabel getLblCampoAActualizar() {
        return lblCampoAActualizar;
    }

    public JLabel getLblNuevoValor() {
        return lblNuevoValor;
    }

    public MensajeInternacionalizacionHandler getMensajeHandler() {
        return mensajeHandler;
    }
}