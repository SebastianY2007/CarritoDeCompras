package ec.edu.ups.vista.producto;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

public class ActualizarProductoView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JComboBox<String> cbxElegir;
    private JTextField txtElegir;
    private JButton btnActualizar;
    private JButton btnCancelar;

    private MensajeInternacionalizacionHandler mensajeHandler;

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

    private ImageIcon redimensionarIcono(ImageIcon icono, int ancho, int alto) {
        Image imagen = icono.getImage();
        Image imagenRedimensionada = imagen.getScaledInstance(ancho, alto, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(imagenRedimensionada);
    }

    private void configurarIconos() {
        java.net.URL urlIconoActualizar = getClass().getResource("/icons/icono_modificar.png");
        java.net.URL urlIconoCancelar = getClass().getResource("/icons/icono_cancelar.png");

        if (urlIconoActualizar != null) {
            ImageIcon iconoOriginal = new ImageIcon(urlIconoActualizar);

            ImageIcon iconoAjustado = redimensionarIcono(iconoOriginal, 16, 16);

            btnActualizar.setIcon(iconoAjustado);
        } else {
            System.err.println("Icono no encontrado: /icons/icono_modificar.png");
        }

        if (urlIconoCancelar != null) {
            ImageIcon iconoOriginal = new ImageIcon(urlIconoCancelar);

            ImageIcon iconoAjustado = redimensionarIcono(iconoOriginal, 16, 16);

            btnCancelar.setIcon(iconoAjustado);
        } else {
            System.err.println("Icono no encontrado: /icons/icono_cancelar.png");
        }
    }

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

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

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

    public MensajeInternacionalizacionHandler getMensajeHandler() {
        return mensajeHandler;
    }
}