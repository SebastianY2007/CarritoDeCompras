package ec.edu.ups.vista.producto;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ResourceBundle;

/**
 * Clase GestionDeProductosView
 *
 * Esta clase representa la vista principal (un JInternalFrame) para la gestión
 * de productos por parte del administrador. Permite listar, buscar, agregar,
 * actualizar y eliminar productos.
 *
 * @author Sebastian Yupangui
 * @version 1.0
 * @since 15/07/2025
 */
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

    /**
     * Constructor de GestionDeProductosView.
     *
     * @param handler El manejador de internacionalización para los textos de la UI.
     */
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
     * Configura los iconos para todos los botones de la vista.
     */
    private void configurarIconos() {
        java.net.URL urlIconoAgregar = getClass().getResource("/icons/icono_agregar_producto.png");
        java.net.URL urlIconoEliminar = getClass().getResource("/icons/icono_basurero.png");
        java.net.URL urlIconoActualizar = getClass().getResource("/icons/icono_modificar.png");
        java.net.URL urlIconoBuscar = getClass().getResource("/icons/icono_buscar_producto.png");
        java.net.URL urlIconoListar = getClass().getResource("/icons/icono_listar.png");

        if (urlIconoAgregar != null) {
            btnAgregar.setIcon(redimensionarIcono(new ImageIcon(urlIconoAgregar), 16, 16));
        }
        if (urlIconoEliminar != null) {
            btnEliminar.setIcon(redimensionarIcono(new ImageIcon(urlIconoEliminar), 16, 16));
        }
        if (urlIconoActualizar != null) {
            btnActualizar.setIcon(redimensionarIcono(new ImageIcon(urlIconoActualizar), 16, 16));
        }
        if (urlIconoBuscar != null) {
            btnBuscar.setIcon(redimensionarIcono(new ImageIcon(urlIconoBuscar), 16, 16));
        }
        if (urlIconoListar != null) {
            btnListar.setIcon(redimensionarIcono(new ImageIcon(urlIconoListar), 16, 16));
        }
    }

    /**
     * Configura el modelo inicial de la tabla de productos.
     * Establece que las celdas no sean editables directamente.
     */
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

    /**
     * Actualiza los textos de la interfaz según el idioma seleccionado.
     */
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

    // --- Getters para que el Controlador pueda acceder a los componentes ---

    /**
     * Obtiene el JTextField para buscar productos.
     * @return el JTextField de búsqueda.
     */
    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    /**
     * Obtiene el botón de buscar.
     * @return el JButton de buscar.
     */
    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    /**
     * Obtiene la tabla de productos.
     * @return el JTable de productos.
     */
    public JTable getTblProductos() {
        return tblProductos;
    }

    /**
     * Obtiene el botón de listar todos los productos.
     * @return el JButton de listar.
     */
    public JButton getBtnListar() {
        return btnListar;
    }

    /**
     * Obtiene el botón de eliminar producto.
     * @return el JButton de eliminar.
     */
    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    /**
     * Obtiene el botón de actualizar producto.
     * @return el JButton de actualizar.
     */
    public JButton getBtnActualizar() {
        return btnActualizar;
    }

    /**
     * Obtiene el botón de agregar nuevo producto.
     * @return el JButton de agregar.
     */
    public JButton getBtnAgregar() {
        return btnAgregar;
    }
}
