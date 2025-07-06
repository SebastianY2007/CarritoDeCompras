package ec.edu.ups.vista.carrito;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

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

    private ImageIcon redimensionarIcono(ImageIcon icono, int ancho, int alto) {
        Image imagen = icono.getImage();
        Image imagenRedimensionada = imagen.getScaledInstance(ancho, alto, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(imagenRedimensionada);
    }

    private void configurarIconos() {
        java.net.URL urlIconoListar = getClass().getResource("/icons/icono_listar.png");
        java.net.URL urlIconoBuscar = getClass().getResource("/icons/icono_buscar_carrito.png");
        java.net.URL urlIconoEliminar = getClass().getResource("/icons/icono_basurero.png");
        java.net.URL urlIconoModificar = getClass().getResource("/icons/icono_modificar.png");
        java.net.URL urlIconoAgregar = getClass().getResource("/icons/icono_agregar_producto.png");
        java.net.URL urlIconoGuardarCarrito = getClass().getResource("/icons/icono_guardar.png");
        java.net.URL urlIconoEliminarCarrito = getClass().getResource("/icons/icono_basurero.png");

        if (urlIconoListar != null) {
            ImageIcon iconoOriginal = new ImageIcon(urlIconoListar);

            ImageIcon iconoAjustado = redimensionarIcono(iconoOriginal, 16, 16);

            btnListarMisCarritos.setIcon(iconoAjustado);
        } else {
            System.err.println("Icono no encontrado: /icons/icono_listar.png");
        }

        if (urlIconoBuscar != null) {
            ImageIcon iconoOriginal = new ImageIcon(urlIconoBuscar);

            ImageIcon iconoAjustado = redimensionarIcono(iconoOriginal, 16, 16);

            btnListarMisCarritos.setIcon(iconoAjustado);
        } else {
            System.err.println("Icono no encontrado: /icons/icono_buscar_carrito.png");
        }

        if (urlIconoEliminar != null) {
            ImageIcon iconoOriginal = new ImageIcon(urlIconoEliminar);

            ImageIcon iconoAjustado = redimensionarIcono(iconoOriginal, 16, 16);

            btnEliminarProducto.setIcon(iconoAjustado);
        } else {
            System.err.println("Icono no encontrado: /icons/icono_basurero.png");
        }

        if (urlIconoModificar != null) {
            ImageIcon iconoOriginal = new ImageIcon(urlIconoModificar);

            ImageIcon iconoAjustado = redimensionarIcono(iconoOriginal, 16, 16);

            btnEliminarProducto.setIcon(iconoAjustado);
        } else {
            System.err.println("Icono no encontrado: /icons/icono_modificar.png");
        }

        if (urlIconoAgregar != null) {
            ImageIcon iconoOriginal = new ImageIcon(urlIconoAgregar);

            ImageIcon iconoAjustado = redimensionarIcono(iconoOriginal, 16, 16);

            btnEliminarProducto.setIcon(iconoAjustado);
        } else {
            System.err.println("Icono no encontrado: /icons/icono_agregar_producto.png");
        }

        if (urlIconoGuardarCarrito != null) {
            ImageIcon iconoOriginal = new ImageIcon(urlIconoGuardarCarrito);

            ImageIcon iconoAjustado = redimensionarIcono(iconoOriginal, 16, 16);

            btnEliminarProducto.setIcon(iconoAjustado);
        } else {
            System.err.println("Icono no encontrado: /icons/icono_guardar.png");
        }

        if (urlIconoEliminarCarrito != null) {
            ImageIcon iconoOriginal = new ImageIcon(urlIconoEliminarCarrito);

            ImageIcon iconoAjustado = redimensionarIcono(iconoOriginal, 16, 16);

            btnEliminarProducto.setIcon(iconoAjustado);
        } else {
            System.err.println("Icono no encontrado: /icons/icono_basurero.png");
        }
    }

    private void configurarTablas() {
        modeloMisCarritos = new DefaultTableModel();
        tblMisCarritos.setModel(modeloMisCarritos);

        modeloCarrito = new DefaultTableModel();
        tblCarrito.setModel(modeloCarrito);
    }

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

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public JTable getTblMisCarritos() {
        return tblMisCarritos;
    }

    public JButton getBtnListarMisCarritos() {
        return btnListarMisCarritos;
    }

    public JTextField getTxtCodigoCarrito() {
        return txtCodigoCarrito;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JTable getTblCarrito() {
        return tblCarrito;
    }

    public JButton getBtnAgregarProducto() {
        return btnAgregarProducto;
    }

    public JButton getBtnModificarCantidad() {
        return btnModificarCantidad;
    }

    public JButton getBtnEliminarProducto() {
        return btnEliminarProducto;
    }

    public JButton getBtnGuardarCarrito() {
        return btnGuardarCarrito;
    }

    public JButton getBtnEliminarCarrito() {
        return btnEliminarCarrito;
    }

    public JLabel getLblEditarCarrito() {
        return lblEditarCarrito;
    }

    public JLabel getLblCodigo() {
        return lblCodigo;
    }
}