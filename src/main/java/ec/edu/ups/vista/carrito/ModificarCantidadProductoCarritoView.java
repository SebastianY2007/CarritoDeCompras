package ec.edu.ups.vista.carrito;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import java.util.ResourceBundle;

/**
 * Clase ModificarCantidadProductoCarritoView
 *
 * Esta clase representa la vista (un JDialog modal) que permite al usuario
 * modificar la cantidad de un producto que ya se encuentra en su carrito.
 *
 * @author Sebastian Yupangui
 * @version 1.0
 * @since 15/07/2025
 */
public class ModificarCantidadProductoCarritoView extends JDialog {
    private JPanel panelPrincipal;
    private JComboBox<Integer> cbxCantidad;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private JLabel lblNuevaCantidad;
    private MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler;
    private ResourceBundle mensajes;

    /**
     * Constructor de ModificarCantidadProductoCarritoView.
     *
     * @param parent El JFrame padre sobre el cual este diálogo será modal.
     */
    public ModificarCantidadProductoCarritoView(JFrame parent) {
        super(parent, "Modificar Cantidad", true);
        this.setContentPane(panelPrincipal);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(parent);

        for (int i = 1; i <= 20; i++) {
            cbxCantidad.addItem(i);
        }
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
        java.net.URL urlIconoGuardar = getClass().getResource("/icons/icono_guardar.png");
        java.net.URL urlIconoCancelar = getClass().getResource("/icons/icono_cancelar.png");

        if (urlIconoGuardar != null) {
            btnGuardar.setIcon(redimensionarIcono(new ImageIcon(urlIconoGuardar), 16, 16));
        }
        if (urlIconoCancelar != null) {
            btnCancelar.setIcon(redimensionarIcono(new ImageIcon(urlIconoCancelar), 16, 16));
        }
    }

    /**
     * Actualiza los textos de la interfaz según el idioma seleccionado.
     */
    public void updateTexts() {
        this.mensajes = ResourceBundle.getBundle("mensajes", new Locale(mensajeInternacionalizacionHandler.getLenguajeActual(), mensajeInternacionalizacionHandler.getPaisActual()));

        setTitle(mensajes.getString("carrito.modificar.titulo"));
        lblNuevaCantidad.setText(mensajes.getString("carrito.modificar.lblNuevaCantidad"));
        btnGuardar.setText(mensajes.getString("global.boton.guardar"));
        btnCancelar.setText(mensajes.getString("global.boton.cancelar"));

        revalidate();
        repaint();
    }

    // --- Getters para que el Controlador pueda acceder a los componentes ---

    public JComboBox<Integer> getCbxCantidad() { return cbxCantidad; }
    public JButton getBtnGuardar() { return btnGuardar; }
    public JButton getBtnCancelar() { return btnCancelar; }
}
