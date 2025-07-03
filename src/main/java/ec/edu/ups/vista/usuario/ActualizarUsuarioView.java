package ec.edu.ups.vista.usuario;

import ec.edu.ups.controlador.UsuarioController;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class ActualizarUsuarioView extends JInternalFrame {

    // --- Componentes de la UI ---
    // Estas variables DEBEN coincidir con el 'fieldName' de tu diseño.
    private JPanel panelPrincipal;
    private JComboBox<String> cbxElegir;
    private JTextField txtElegir;
    private JButton btnActualizar;
    private JButton btnCancelar;

    // --- Dependencias ---
    private UsuarioController usuarioController;
    private MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler;
    private ResourceBundle mensajes;
    private String usernameActual; // Para guardar el username del usuario que se está editando

    public ActualizarUsuarioView(MensajeInternacionalizacionHandler msgHandler) {
        this.mensajeInternacionalizacionHandler = msgHandler;
        this.mensajes = ResourceBundle.getBundle("mensajes", new Locale(msgHandler.getLenguajeActual(), msgHandler.getPaisActual()));

        // CORRECCIÓN: Se usa la clave "titulo" en lugar de "title".
        setTitle(mensajes.getString("actualizarUsuario.titulo"));

        setContentPane(panelPrincipal);
        setSize(400, 200);
        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // La llamada a updateTexts() se hará externamente para evitar NullPointerExceptions.
    }

    // --- Getters para el Controlador ---
    public JComboBox<String> getCbxElegir() { return cbxElegir; }
    public JTextField getTxtValorNuevo() { return txtElegir; } // Renombrado para más claridad
    public JButton getBtnActualizar() { return btnActualizar; }
    public JButton getBtnCancelar() { return btnCancelar; }
    public String getUsernameActual() { return usernameActual; }

    // --- Setters ---
    public void setUsuarioController(UsuarioController usuarioController) {
        this.usuarioController = usuarioController;
    }

    public void setMensajeInternacionalizacionHandler(MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler) {
        this.mensajeInternacionalizacionHandler = mensajeInternacionalizacionHandler;
        updateTexts();
    }

    // --- Métodos de la Vista ---
    public void cargarDatosUsuario(ec.edu.ups.modelo.Usuario usuario) {
        this.usernameActual = usuario.getUsername();
        // Opcional: podrías llenar el campo de texto con un valor inicial si lo deseas
        // txtElegir.setText(usuario.getNombre());
    }

    public void limpiarCampos() {
        cbxElegir.setSelectedIndex(0);
        txtElegir.setText("");
        usernameActual = null;
    }

    public void mostrarMensaje(String mensaje, int tipoMensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Mensaje", tipoMensaje);
    }

    public void updateTexts() {
        this.mensajes = ResourceBundle.getBundle("mensajes", new Locale(mensajeInternacionalizacionHandler.getLenguajeActual(), mensajeInternacionalizacionHandler.getPaisActual()));

        setTitle(mensajes.getString("actualizarUsuario.titulo"));

        // CORRECCIÓN: Se eliminaron las referencias a las etiquetas que no existen.
        // No hay etiquetas que actualizar en este diseño.

        // Actualización de Botones
        btnActualizar.setText(mensajes.getString("actualizarUsuario.boton.actualizar"));
        btnCancelar.setText(mensajes.getString("actualizarUsuario.boton.cancelar"));

        // Actualización de Opciones del ComboBox
        if (cbxElegir != null) {
            int selectedIndex = cbxElegir.getSelectedIndex();

            cbxElegir.removeAllItems();
            cbxElegir.addItem(mensajes.getString("actualizarUsuario.opcion.username"));
            cbxElegir.addItem(mensajes.getString("actualizarUsuario.opcion.email"));
            cbxElegir.addItem(mensajes.getString("actualizarUsuario.opcion.contrasena"));
            cbxElegir.addItem(mensajes.getString("actualizarUsuario.opcion.rol"));
            cbxElegir.addItem(mensajes.getString("actualizarUsuario.opcion.nombre"));
            cbxElegir.addItem(mensajes.getString("actualizarUsuario.opcion.apellido"));
            cbxElegir.addItem(mensajes.getString("actualizarUsuario.opcion.telefono"));

            if (selectedIndex >= 0 && selectedIndex < cbxElegir.getItemCount()) {
                cbxElegir.setSelectedIndex(selectedIndex);
            }
        }

        revalidate();
        repaint();
    }
}
