package ec.edu.ups.vista.producto;

import ec.edu.ups.util.MensajeInternacionalizacionHandler; // Importar el handler
import javax.swing.JOptionPane;
import javax.swing.*;

public class ActualizarProductoView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JComboBox<String> cbxElegir; // Se recomienda especificar el tipo del JComboBox
    private JTextField txtElegir;
    private JButton btnActualizar;
    private JButton btnCancelar;
    private int idActual; // Usado para el ID del producto a actualizar
    // private int codigoActual; // Este campo parece redundante si ya tienes idActual

    private MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler; // Instancia del handler

    public ActualizarProductoView() {
        setContentPane(panelPrincipal);
        setTitle("Actualizar Producto"); // Este título se actualizará con updateTexts
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        // Los ítems del JComboBox se añadirán y actualizarán en updateTexts()
        // para que sean internacionalizados
    }

    // Setter para inyectar el MensajeInternacionalizacionHandler
    public void setMensajeInternacionalizacionHandler(MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler) {
        this.mensajeInternacionalizacionHandler = mensajeInternacionalizacionHandler;
        updateTexts(); // Llamar a updateTexts cuando se establece el handler
    }

    public void updateTexts() {
        if (mensajeInternacionalizacionHandler != null) {
            setTitle(mensajeInternacionalizacionHandler.get("actualizarProducto.titulo"));
            btnActualizar.setText(mensajeInternacionalizacionHandler.get("actualizarProducto.btnActualizar"));
            btnCancelar.setText(mensajeInternacionalizacionHandler.get("actualizarProducto.btnCancelar"));

            // Limpiar y añadir los ítems del ComboBox con los textos internacionalizados
            cbxElegir.removeAllItems();
            cbxElegir.addItem(mensajeInternacionalizacionHandler.get("actualizarProducto.cbx.codigo"));
            cbxElegir.addItem(mensajeInternacionalizacionHandler.get("actualizarProducto.cbx.nombre"));
            cbxElegir.addItem(mensajeInternacionalizacionHandler.get("actualizarProducto.cbx.precio"));
            // Podrías añadir más si hay otros campos en el futuro
        }
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JComboBox<String> getCbxElegir() {
        return cbxElegir;
    }

    public void setCbxElegir(JComboBox<String> cbxElegir) {
        this.cbxElegir = cbxElegir;
    }

    public JTextField getTxtElegir() {
        return txtElegir;
    }

    public void setTxtElegir(JTextField txtElegir) {
        this.txtElegir = txtElegir;
    }

    // Este método `actualizarProducto()` en la vista es una función vacía,
    // la lógica de actualización está en el controlador. Podrías eliminarlo si no se usa.
    public void actualizarProducto() {
        // La lógica de actualización la maneja el controlador
    }

    public int getIdActual() {
        return idActual;
    }

    public void setIdActual(int idActual) {
        this.idActual = idActual;
    }

    // Este getter/setter para codigoActual parece redundante si ya tienes idActual
    // y el controlador usa idActual como la referencia.
    /*
    public int getCodigoActual() {
        return codigoActual;
    }

    public void setCodigoActual(int codigoActual) {
        this.codigoActual = codigoActual;
    }
    */

    public JButton getBtnActualizar() {
        return btnActualizar;
    }

    public void setBtnActualizar(JButton btnActualizar) {
        this.btnActualizar = btnActualizar;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    public void setBtnCancelar(JButton btnCancelar) {
        this.btnCancelar = btnCancelar;
    }

    public void mostrarMensaje(String mensaje, int tipoDeMensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Mensaje del Sistema", tipoDeMensaje);
    }

    public void limpiarCampoElegir() {
        if (txtElegir != null) {
            txtElegir.setText("");
        }
        if (cbxElegir != null && cbxElegir.getItemCount() > 0) {
            cbxElegir.setSelectedIndex(0);
        }
    }
}