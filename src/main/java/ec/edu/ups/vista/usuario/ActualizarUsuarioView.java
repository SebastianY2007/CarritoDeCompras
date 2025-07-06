package ec.edu.ups.vista.usuario;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import javax.swing.*;
import java.awt.*; // Necesario para layouts y componentes
import java.awt.event.ItemEvent;
import java.util.Locale;
import java.util.ResourceBundle;

public class ActualizarUsuarioView extends JInternalFrame {

    private JPanel panelPrincipal; // Este será el contenedor principal
    private JComboBox<String> cbxCampo;
    private JTextField txtNuevoValor;
    private JButton btnActualizar;
    private JButton btnCancelar;
    private JLabel lblCampoAActualizar; // Este era null
    private JLabel lblNuevoValor;       // Este también sería null

    private MensajeInternacionalizacionHandler mensajeHandler;

    public ActualizarUsuarioView(MensajeInternacionalizacionHandler mensajeHandler) {
        this.mensajeHandler = mensajeHandler;

        // --- LLAMA A UN MÉTODO PARA INICIALIZAR TODOS LOS COMPONENTES ---
        // Este método 'miInitComponents()' es el que crearás.
        miInitComponents(); // <--- ¡Nueva línea!

        setContentPane(panelPrincipal);
        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setIconifiable(true);
        setSize(400, 200);
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);

        // Agrega el listener DESPUÉS de que cbxCampo haya sido inicializado
        if (cbxCampo != null) { // Verificación extra por seguridad
            cbxCampo.addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    txtNuevoValor.setText("");
                }
            });
        }


        // Llama a updateTexts una vez que TODOS los componentes hayan sido creados
        // El SwingUtilities.invokeLater es bueno si la vista se muestra inmediatamente
        // y quieres que los textos se ajusten tras el renderizado inicial.
        SwingUtilities.invokeLater(this::updateTexts);
    }

    // --- NUEVO MÉTODO PARA INICIALIZAR COMPONENTES ---
    private void miInitComponents() {
        panelPrincipal = new JPanel(new GridBagLayout()); // O el layout que desees
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espaciado

        // Inicialización de los JLabels
        lblCampoAActualizar = new JLabel("Campo a Actualizar:"); // Inicializa aquí
        lblNuevoValor = new JLabel("Nuevo Valor:");       // Inicializa aquí

        // Inicialización de los otros componentes
        cbxCampo = new JComboBox<>();
        txtNuevoValor = new JTextField(20); // Tamaño sugerido
        btnActualizar = new JButton("Actualizar");
        btnCancelar = new JButton("Cancelar");

        // Añadir componentes al panelPrincipal usando GridBagLayout (ejemplo)
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
        JPanel panelBotones = new JPanel(); // Panel para botones
        panelBotones.add(btnActualizar);
        panelBotones.add(btnCancelar);
        panelPrincipal.add(panelBotones, gbc);

        // Asegúrate de poblar el cbxCampo con items iniciales si lo necesitas
        // Esto también se hará en updateTexts(), pero para que tenga algo al inicio
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
        // La condición `if (mensajeHandler == null) return;` es buena,
        // pero la clave es que los JLabels *existan* antes de esta línea.
        if (mensajeHandler == null) return;

        ResourceBundle mensajes = ResourceBundle.getBundle("mensajes", new Locale(mensajeHandler.getLenguajeActual(), mensajeHandler.getPaisActual()));

        setTitle(mensajes.getString("actualizarUsuario.titulo"));

        // ¡Estas líneas ahora funcionarán porque lblCampoAActualizar y lblNuevoValor ya están inicializados!
        if (lblCampoAActualizar != null) // Añadir verificación extra por robustez
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
            } else if (cbxCampo.getItemCount() > 0) { // Si no hay selección previa, selecciona el primero
                cbxCampo.setSelectedIndex(0);
            }
        }

        revalidate();
        repaint();
    }
}