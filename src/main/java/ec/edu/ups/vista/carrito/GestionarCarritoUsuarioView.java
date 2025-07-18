package ec.edu.ups.vista.carrito;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Clase GestionarCarritoUsuarioView
 *
 * Esta clase representa la vista (un JInternalFrame) donde un usuario estándar
 * puede gestionar sus propios carritos de compra. Permite listar, buscar,
 * editar (añadir/quitar productos, modificar cantidad) y eliminar sus carritos.
 *
 * @author Sebastian Yupangui
 * @version 1.0
 * @since 15/07/2025
 */
public class GestionarCarritoUsuarioView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTable tblMisCarritos;
    private JButton btnListarMisCarritos;
    private JTextField txtCodigoCarrito;
    private JButton btnBuscar;
    private JTable tblCarrito;
    private JButton btnAgregarProducto;
    private JButton btnModificarCantidad;
    private JButton btnEliminarProducto;
    private JButton btnGuardarCarrito;
    private JButton btnEliminarCarrito;
    private JLabel lblEditarCarrito;
    private JLabel lblCodigo;
    private MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler;
    private ResourceBundle mensajes;
    private DefaultTableModel modeloMisCarritos;
    private DefaultTableModel modeloCarrito;

    /**
     * Constructor de GestionarCarritoUsuarioView.
     *
     * Inicializa la ventana interna y sus componentes.
     */
    public GestionarCarritoUsuarioView() {
        setTitle("Gestionar Mis Carritos");
        setContentPane(panelPrincipal);
        setSize(800, 600);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

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
        // Asignación de iconos a cada botón
        btnListarMisCarritos.setIcon(redimensionarIcono(new ImageIcon(getClass().getResource("/icons/icono_listar.png")), 16, 16));
        btnBuscar.setIcon(redimensionarIcono(new ImageIcon(getClass().getResource("/icons/icono_buscar_carrito.png")), 16, 16));
        btnEliminarProducto.setIcon(redimensionarIcono(new ImageIcon(getClass().getResource("/icons/icono_basurero.png")), 16, 16));
        btnModificarCantidad.setIcon(redimensionarIcono(new ImageIcon(getClass().getResource("/icons/icono_modificar.png")), 16, 16));
        btnAgregarProducto.setIcon(redimensionarIcono(new ImageIcon(getClass().getResource("/icons/icono_agregar_producto.png")), 16, 16));
        btnGuardarCarrito.setIcon(redimensionarIcono(new ImageIcon(getClass().getResource("/icons/icono_guardar.png")), 16, 16));
        btnEliminarCarrito.setIcon(redimensionarIcono(new ImageIcon(getClass().getResource("/icons/icono_basurero.png")), 16, 16));
    }

    /**
     * Configura los modelos para las dos tablas de la vista.
     */
    private void configurarTablas() {
        modeloMisCarritos = new DefaultTableModel();
        tblMisCarritos.setModel(modeloMisCarritos);

        modeloCarrito = new DefaultTableModel();
        tblCarrito.setModel(modeloCarrito);
    }

    /**
     * Actualiza los textos de la interfaz según el idioma seleccionado.
     */
    public void updateTexts() {
        this.mensajes = ResourceBundle.getBundle("mensajes", new Locale(mensajeInternacionalizacionHandler.getLenguajeActual(), mensajeInternacionalizacionHandler.getPaisActual()));

        setTitle(mensajes.getString("carrito.gestionarUsuario.titulo"));
        lblEditarCarrito.setText(mensajes.getString("carrito.gestionarUsuario.lblEditarCarrito"));
        lblCodigo.setText(mensajes.getString("carrito.gestionarUsuario.lblCodigo"));

        btnListarMisCarritos.setText(mensajes.getString("carrito.gestionarUsuario.btnListar"));
        btnBuscar.setText(mensajes.getString("global.boton.buscar"));
        btnAgregarProducto.setText(mensajes.getString("carrito.gestionarUsuario.btnAgregar"));
        btnModificarCantidad.setText(mensajes.getString("carrito.gestionarUsuario.btnModificar"));
        btnEliminarProducto.setText(mensajes.getString("carrito.gestionarUsuario.btnEliminarProducto"));
        btnGuardarCarrito.setText(mensajes.getString("carrito.boton.guardar"));
        btnEliminarCarrito.setText(mensajes.getString("carrito.gestionarUsuario.btnEliminarCarrito"));

        modeloMisCarritos.setColumnIdentifiers(new Object[]{
                mensajes.getString("carrito.gestionarAdmin.tabla.codigo"),
                mensajes.getString("carrito.gestionarAdmin.tabla.fecha"),
                mensajes.getString("carrito.gestionarAdmin.tabla.total")
        });
        modeloCarrito.setColumnIdentifiers(new Object[]{
                mensajes.getString("carrito.tabla.codigo"),
                mensajes.getString("carrito.tabla.nombre"),
                mensajes.getString("carrito.tabla.precioUnitario"),
                mensajes.getString("carrito.tabla.cantidad"),
                mensajes.getString("carrito.tabla.subtotal")
        });

        revalidate();
        repaint();
    }

    // --- Getters para que el Controlador pueda acceder a los componentes ---

    public JTable getTblMisCarritos() { return tblMisCarritos; }
    public JButton getBtnListarMisCarritos() { return btnListarMisCarritos; }
    public JTextField getTxtCodigoCarrito() { return txtCodigoCarrito; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JTable getTblCarrito() { return tblCarrito; }
    public JButton getBtnAgregarProducto() { return btnAgregarProducto; }
    public JButton getBtnModificarCantidad() { return btnModificarCantidad; }
    public JButton getBtnEliminarProducto() { return btnEliminarProducto; }
    public JButton getBtnGuardarCarrito() { return btnGuardarCarrito; }
    public JButton getBtnEliminarCarrito() { return btnEliminarCarrito; }
}
