package ec.edu.ups.vista.carrito;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class GestionarCarritoAdministradorView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTable tblCarritos;
    private JButton btnListar;
    private MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler;
    private ResourceBundle mensajes;
    private DefaultTableModel tableModel;

    public GestionarCarritoAdministradorView() {
        setTitle("Gestion de Carritos (Administrador)");
        setContentPane(panelPrincipal);
        setSize(600, 450);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        configurarIconos();
    }

    private ImageIcon redimensionarIcono(ImageIcon icono, int ancho, int alto) {
        Image imagen = icono.getImage();
        Image imagenRedimensionada = imagen.getScaledInstance(ancho, alto, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(imagenRedimensionada);
    }

    private void configurarIconos() {
        java.net.URL urlIconoListar = getClass().getResource("/icons/icono_listar.png");

        if (urlIconoListar != null) {
            ImageIcon iconoOriginal = new ImageIcon(urlIconoListar);

            ImageIcon iconoAjustado = redimensionarIcono(iconoOriginal, 16, 16);

            btnListar.setIcon(iconoAjustado);
        } else {
            System.err.println("Icono no encontrado: /icons/icono_listar.png");
        }
    }

    private void configurarTabla() {
        tableModel = new DefaultTableModel();
        tblCarritos.setModel(tableModel);
    }

    public void updateTexts() {
        this.mensajes = ResourceBundle.getBundle("mensajes", new Locale(mensajeInternacionalizacionHandler.getLenguajeActual(), mensajeInternacionalizacionHandler.getPaisActual()));

        setTitle(mensajes.getString("carrito.gestionarAdmin.titulo"));
        btnListar.setText(mensajes.getString("global.boton.listar"));

        tableModel.setColumnIdentifiers(new Object[]{
                mensajes.getString("carrito.gestionarAdmin.tabla.codigo"),
                mensajes.getString("carrito.gestionarAdmin.tabla.usuario"),
                mensajes.getString("carrito.gestionarAdmin.tabla.fecha"),
                mensajes.getString("carrito.gestionarAdmin.tabla.total")
        });

        revalidate();
        repaint();
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public JTable getTblCarritos() {
        return tblCarritos;
    }

    public JButton getBtnListar() {
        return btnListar;
    }
}