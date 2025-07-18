package ec.edu.ups.vista.producto;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

/**
 * Clase ActualizarProductoView
 *
 * Esta clase representa la vista (un JInternalFrame) que permite al administrador
 * actualizar un campo específico (nombre o precio) de un producto existente.
 *
 * @author Sebastian Yupangui
 * @version 1.0
 * @since 15/07/2025
 */
public class ActualizarProductoView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JComboBox<String> cbxElegir;
    private JTextField txtElegir;
    private JButton btnActualizar;
    private JButton btnCancelar;

    private MensajeInternacionalizacionHandler mensajeHandler;

    /**
     * Constructor de ActualizarProductoView.
     *
     * @param handler El manejador de internacionalización para los textos de la UI.
     */
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
        configurarIconos();
    }

    /**
     * Redimensiona un icono a un tamaño específico.
     * @param icono El ImageIcon original.
     * @param ancho El nuevo ancho del icono.
     * @param alto El nuevo alto del icono.
     * @return un nuevo ImageIcon redimensionado.
     */
    private ImageIcon redimensionarIcono(ImageIcon icono, int ancho, int alto) {
        Image imagen = icono.getImage();
        Image imagenRedimensionada = imagen.getScaledInstance(ancho, alto, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(imagenRedimensionada);
    }

    /**
     * Configura los iconos para los botones de la vista.
     */
    private void configurarIconos() {
        java.net.URL urlIconoActualizar = getClass().getResource("/icons/icono_modificar.png");
        java.net.URL urlIconoCancelar = getClass().getResource("/icons/icono_cancelar.png");

        if (urlIconoActualizar != null) {
            btnActualizar.setIcon(redimensionarIcono(new ImageIcon(urlIconoActualizar), 16, 16));
        }
        if (urlIconoCancelar != null) {
            btnCancelar.setIcon(redimensionarIcono(new ImageIcon(urlIconoCancelar), 16, 16));
        }
    }

    /**
     * Actualiza los textos de la interfaz según el idioma seleccionado.
     * Mantiene la selección actual del JComboBox al recargar los ítems.
     */
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

    // --- Getters para que el Controlador pueda acceder a los componentes ---

    /**
     * Obtiene el JComboBox para elegir el campo a actualizar.
     * @return el JComboBox de campos.
     */
    public JComboBox<String> getCbxElegir() {
        return cbxElegir;
    }

    /**
     * Obtiene el JTextField para ingresar el nuevo valor.
     * @return el JTextField del nuevo valor.
     */
    public JTextField getTxtElegir() {
        return txtElegir;
    }

    /**
     * Obtiene el botón de actualizar.
     * @return el JButton de actualizar.
     */
    public JButton getBtnActualizar() {
        return btnActualizar;
    }

    /**
     * Obtiene el botón de cancelar.
     * @return el JButton de cancelar.
     */
    public JButton getBtnCancelar() {
        return btnCancelar;
    }
}
