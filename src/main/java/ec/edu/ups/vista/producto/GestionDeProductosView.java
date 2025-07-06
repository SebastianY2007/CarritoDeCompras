package ec.edu.ups.vista.producto;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ResourceBundle;

public class GestionDeProductosView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JTable tblProductos;
    private JButton btnListar;
    private JButton btnEliminar;
    private JButton btnActualizar;
    private JButton btnAgregar;
    private JLabel lblNombre;

    private DefaultTableModel tableModel;
    private MensajeInternacionalizacionHandler mensajeHandler;

    public GestionDeProductosView(MensajeInternacionalizacionHandler handler) {
        this.mensajeHandler = handler;

        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
        setSize(1000, 600);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        configurarTabla();
        updateTexts();
        configurarIconos();
    }

    private ImageIcon redimensionarIcono(ImageIcon icono, int ancho, int alto) {
        Image imagen = icono.getImage();
        Image imagenRedimensionada = imagen.getScaledInstance(ancho, alto, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(imagenRedimensionada);
    }

    private void configurarIconos() {
        java.net.URL urlIconoAgregar = getClass().getResource("/icons/icono_agregar_producto.png");
        java.net.URL urlIconoEliminar = getClass().getResource("/icons/icono_basurero.png");
        java.net.URL urlIconoActualizar = getClass().getResource("/icons/icono_modificar.png");
        java.net.URL urlIconoBuscar = getClass().getResource("/icons/icono_buscar_producto.png");
        java.net.URL urlIconoListar = getClass().getResource("/icons/icono_listar.png");

        if (urlIconoAgregar != null) {
            ImageIcon iconoOriginal = new ImageIcon(urlIconoAgregar);

            ImageIcon iconoAjustado = redimensionarIcono(iconoOriginal, 16, 16);

            btnAgregar.setIcon(iconoAjustado);
        } else {
            System.err.println("Icono no encontrado: /icons/icono_agregar_producto" +
                    ".png");
        }

        if (urlIconoEliminar != null) {
            ImageIcon iconoOriginal = new ImageIcon(urlIconoEliminar);

            ImageIcon iconoAjustado = redimensionarIcono(iconoOriginal, 16, 16);

            btnEliminar.setIcon(iconoAjustado);
        } else {
            System.err.println("Icono no encontrado: /icons/icono_basurero.png");
        }

        if (urlIconoActualizar != null) {
            ImageIcon iconoOriginal = new ImageIcon(urlIconoActualizar);

            ImageIcon iconoAjustado = redimensionarIcono(iconoOriginal, 16, 16);

            btnActualizar.setIcon(iconoAjustado);
        } else {
            System.err.println("Icono no encontrado: /icons/icono_modificar.png");
        }

        if (urlIconoBuscar != null) {
            ImageIcon iconoOriginal = new ImageIcon(urlIconoBuscar);

            ImageIcon iconoAjustado = redimensionarIcono(iconoOriginal, 16, 16);

            btnBuscar.setIcon(iconoAjustado);
        } else {
            System.err.println("Icono no encontrado: /icons/icono_buscar_producto.png");
        }

        if (urlIconoListar != null) {
            ImageIcon iconoOriginal = new ImageIcon(urlIconoListar);

            ImageIcon iconoAjustado = redimensionarIcono(iconoOriginal, 16, 16);

            btnListar.setIcon(iconoAjustado);
        } else {
            System.err.println("Icono no encontrado: /icons/icono_listar.png");
        }
    }

    private void configurarTabla() {
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblProductos.setModel(tableModel);
        tblProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public void updateTexts() {
        ResourceBundle mensajes = mensajeHandler.getMensajes();

        setTitle(mensajes.getString("gestionProductos.titulo"));
        lblNombre.setText(mensajes.getString("gestionProductos.label.buscar"));
        btnBuscar.setText(mensajes.getString("global.boton.buscar"));
        btnListar.setText(mensajes.getString("global.boton.listar"));
        btnAgregar.setText(mensajes.getString("global.boton.agregar"));
        btnActualizar.setText(mensajes.getString("global.boton.actualizar"));
        btnEliminar.setText(mensajes.getString("global.boton.eliminar"));

        tableModel.setColumnIdentifiers(new Object[]{
                mensajes.getString("gestionProductos.tabla.codigo"),
                mensajes.getString("gestionProductos.tabla.nombre"),
                mensajes.getString("gestionProductos.tabla.precio")
        });
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JTable getTblProductos() {
        return tblProductos;
    }

    public JButton getBtnListar() {
        return btnListar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public JButton getBtnActualizar() {
        return btnActualizar;
    }

    public JButton getBtnAgregar() {
        return btnAgregar;
    }

    public JLabel getLblNombre() {
        return lblNombre;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public MensajeInternacionalizacionHandler getMensajeHandler() {
        return mensajeHandler;
    }
}