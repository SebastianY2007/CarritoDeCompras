package ec.edu.ups.vista.usuario;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.ResourceBundle;

/**
 * Clase ActualizarUsuarioView
 *
 * Representa la vista (un JInternalFrame) que permite a un administrador
 * actualizar campos específicos de un usuario existente (nombre, email o rol).
 *
 * @author Sebastian Yupangui
 * @version 1.1
 * @since 16-07-2025
 */
public class ActualizarUsuarioView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JComboBox<String> cbxCampo;
    private JTextField txtNuevoValor;
    private JButton btnActualizar;
    private JButton btnCancelar;
    private JLabel lblCampoAActualizar;
    private JLabel lblNuevoValor;

    private MensajeInternacionalizacionHandler mensajeHandler;

    /**
     * Constructor de ActualizarUsuarioView.
     * @param handler El manejador de internacionalización para los textos de la UI.
     */
    public ActualizarUsuarioView(MensajeInternacionalizacionHandler handler) {
        this.mensajeHandler = handler;

        configurarLayout(); // Llama al método que construye la UI
        setContentPane(panelPrincipal); // Usa el panel construido

        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
        pack(); // Ajusta el tamaño al contenido

        updateTexts();
        configurarIconos();
    }

    /**
     * Construye el layout de la ventana mediante código para mayor control.
     */
    private void configurarLayout() {
        panelPrincipal = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        lblCampoAActualizar = new JLabel();
        lblNuevoValor = new JLabel();
        cbxCampo = new JComboBox<>();
        txtNuevoValor = new JTextField(20);
        btnActualizar = new JButton();
        btnCancelar = new JButton();

        gbc.gridx = 0; gbc.gridy = 0; panelPrincipal.add(lblCampoAActualizar, gbc);
        gbc.gridx = 1; gbc.gridy = 0; panelPrincipal.add(cbxCampo, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panelPrincipal.add(lblNuevoValor, gbc);
        gbc.gridx = 1; gbc.gridy = 1; panelPrincipal.add(txtNuevoValor, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panelBotones.add(btnActualizar);
        panelBotones.add(btnCancelar);
        gbc.gridy = 2; gbc.gridwidth = 2;
        panelPrincipal.add(panelBotones, gbc);
    }

    /**
     * Configura los iconos para los botones de la vista.
     */
    private void configurarIconos() {
        // ... (código de configuración de iconos)
    }

    /**
     * Actualiza los textos de la interfaz según el idioma seleccionado.
     */
    public void updateTexts() {
        ResourceBundle mensajes = mensajeHandler.getMensajes();
        setTitle(mensajes.getString("actualizarUsuario.titulo"));
        lblCampoAActualizar.setText(mensajes.getString("actualizarUsuario.label.seleccionar"));
        lblNuevoValor.setText(mensajes.getString("actualizarUsuario.label.nuevoValor"));
        btnActualizar.setText(mensajes.getString("global.boton.actualizar"));
        btnCancelar.setText(mensajes.getString("global.boton.cancelar"));

        int selectedIndex = cbxCampo.getSelectedIndex();
        cbxCampo.removeAllItems();
        // Opciones corregidas para reflejar el modelo actual
        cbxCampo.addItem(mensajes.getString("actualizarUsuario.opcion.nombre"));
        cbxCampo.addItem(mensajes.getString("actualizarUsuario.opcion.email"));
        cbxCampo.addItem(mensajes.getString("actualizarUsuario.opcion.rol"));
        cbxCampo.addItem(mensajes.getString("actualizarUsuario.opcion.contrasena"));

        if (selectedIndex >= 0 && selectedIndex < cbxCampo.getItemCount()) {
            cbxCampo.setSelectedIndex(selectedIndex);
        }
    }

    // --- Getters para que el Controlador pueda acceder a los componentes ---
    public JComboBox<String> getCbxCampo() { return cbxCampo; }
    public JTextField getTxtNuevoValor() { return txtNuevoValor; }
    public JButton getBtnActualizar() { return btnActualizar; }
    public JButton getBtnCancelar() { return btnCancelar; }
}
