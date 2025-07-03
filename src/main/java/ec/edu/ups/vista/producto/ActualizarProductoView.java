package ec.edu.ups.vista.producto;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class ActualizarProductoView extends JInternalFrame {

    // --- Componentes de la UI (Deben coincidir con tu .form) ---
    private JPanel panelPrincipal;
    private JComboBox<String> cbxElegir;
    private JTextField txtElegir;
    private JButton btnActualizar;
    private JButton btnCancelar;

    // --- Dependencias y Estado ---
    private MensajeInternacionalizacionHandler mensajeHandler;
    private ResourceBundle mensajes;
    private int idActual; // Para guardar el ID del producto que se está editando

    // Constructor vacío para el diseñador de UI
    public ActualizarProductoView() {
        // Es importante que este constructor exista si el diseñador lo necesita,
        // pero la inicialización principal se hará cuando se inyecte el handler.
    }

    // Constructor para la lógica de la aplicación
    public ActualizarProductoView(MensajeInternacionalizacionHandler mensajeHandler) {
        this.mensajeHandler = mensajeHandler;

        setContentPane(panelPrincipal);
        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setIconifiable(true);
        setSize(400, 200);
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);

        // La configuración de textos se hará al final para evitar errores
        SwingUtilities.invokeLater(this::updateTexts);
    }

    // --- Getters para que el controlador acceda a los componentes ---
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

    // --- Setters ---
    public void setMensajeInternacionalizacionHandler(MensajeInternacionalizacionHandler mensajeHandler) {
        this.mensajeHandler = mensajeHandler;
        updateTexts();
    }

    public void setIdActual(int idActual) {
        this.idActual = idActual;
        // Limpiar campo de texto al cargar un nuevo producto
        limpiarCampoElegir();
    }

    // --- Métodos de la Vista ---
    public void limpiarCampoElegir() {
        if (txtElegir != null) {
            txtElegir.setText("");
        }
    }

    public void mostrarMensaje(String mensaje, int tipoMensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Mensaje", tipoMensaje);
    }

    public void updateTexts() {
        if (mensajeHandler == null) return; // Evitar error si el handler no está listo

        mensajes = ResourceBundle.getBundle("mensajes", new Locale(mensajeHandler.getLenguajeActual(), mensajeHandler.getPaisActual()));

        setTitle(mensajes.getString("actualizarProducto.titulo"));

        btnActualizar.setText(mensajes.getString("actualizarProducto.boton.actualizar"));
        btnCancelar.setText(mensajes.getString("actualizarProducto.boton.cancelar"));

        // --- CORRECCIÓN: Lógica para llenar el JComboBox ---
        if (cbxElegir != null) {
            int selectedIndex = cbxElegir.getSelectedIndex();

            cbxElegir.removeAllItems();
            cbxElegir.addItem(mensajes.getString("actualizarProducto.opcion.codigo"));
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
