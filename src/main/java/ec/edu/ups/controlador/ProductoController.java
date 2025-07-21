package ec.edu.ups.controlador;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.producto.ActualizarProductoView;
import ec.edu.ups.vista.producto.AnadirProductoView;
import ec.edu.ups.vista.producto.GestionDeProductosView;
import ec.edu.ups.vista.carrito.CrearCarritoView;
import ec.edu.ups.util.FormatoUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Locale;

/**
 * Clase ProductoController
 *
 * Esta clase controla toda la lógica de negocio para la gestión de productos.
 * Actúa como intermediario entre las vistas de productos (añadir, gestionar, actualizar)
 * y la capa de acceso a datos (ProductoDAO), siguiendo el patrón MVC.
 *
 * @author Sebastian Yupangui
 * @version 1.1
 * @since 16/07/2025
 */
public class ProductoController {

    private final ProductoDAO productoDAO;
    private final AnadirProductoView anadirProductoView;
    private final GestionDeProductosView gestionDeProductosView;
    private final ActualizarProductoView actualizarProductoView;
    private final JDesktopPane desktopPane;
    private final MensajeInternacionalizacionHandler mensajeHandler;

    private Producto productoParaActualizar;

    /**
     * Constructor del ProductoController.
     *
     * Inicializa el controlador con todas las dependencias necesarias, incluyendo
     * el DAO de productos y las vistas de la interfaz gráfica con las que interactuará.
     *
     * @param productoDAO El DAO para la persistencia de productos.
     * @param anadirProductoView La vista para añadir nuevos productos.
     * @param gestionDeProductosView La vista principal para gestionar productos.
     * @param actualizarProductoView La vista para actualizar productos existentes.
     * @param crearCarritoView No se usa directamente pero se pasa por consistencia.
     * @param desktopPane El panel de escritorio principal para mostrar las ventanas internas.
     * @param mensajeHandler El manejador para la internacionalización de mensajes.
     */
    public ProductoController(ProductoDAO productoDAO, AnadirProductoView anadirProductoView, GestionDeProductosView gestionDeProductosView, ActualizarProductoView actualizarProductoView, CrearCarritoView crearCarritoView, JDesktopPane desktopPane, MensajeInternacionalizacionHandler mensajeHandler) {
        this.productoDAO = productoDAO;
        this.anadirProductoView = anadirProductoView;
        this.gestionDeProductosView = gestionDeProductosView;
        this.actualizarProductoView = actualizarProductoView;
        this.desktopPane = desktopPane;
        this.mensajeHandler = mensajeHandler;
    }

    /**
     * Inicializa los listeners de eventos para las vistas de productos.
     *
     * Conecta las acciones del usuario (clics en botones) con los métodos
     * correspondientes de este controlador.
     */
    public void initListeners() {
        gestionDeProductosView.getBtnListar().addActionListener(e -> listarProductos());
        gestionDeProductosView.getBtnAgregar().addActionListener(e -> abrirAnadirProducto());
        gestionDeProductosView.getBtnBuscar().addActionListener(e -> buscarProductoPorNombre());
        gestionDeProductosView.getBtnEliminar().addActionListener(e -> eliminarProductoSeleccionado());
        gestionDeProductosView.getBtnActualizar().addActionListener(e -> abrirDialogoActualizar());

        anadirProductoView.getBtnAgregar().addActionListener(e -> crearNuevoProducto());
        anadirProductoView.getBtnLimpiar().addActionListener(e -> limpiarCamposAnadirProducto());

        actualizarProductoView.getBtnActualizar().addActionListener(e -> actualizarCampoSeleccionado());
        actualizarProductoView.getBtnCancelar().addActionListener(e -> actualizarProductoView.dispose());
    }

    /**
     * Carga y muestra todos los productos en la tabla de la vista principal.
     * Formatea los precios de los productos según la configuración regional actual.
     */
    public void listarProductos() {
        List<Producto> productos = productoDAO.listarTodos();
        DefaultTableModel model = (DefaultTableModel) gestionDeProductosView.getTblProductos().getModel();
        model.setColumnIdentifiers(new Object[]{"Código", "Nombre", "Precio"});
        model.setRowCount(0);

        Locale localeActual = mensajeHandler.getLocaleActual();

        for (Producto p : productos) {
            String precioFormateado = FormatoUtils.formatearMoneda(p.getPrecio(), localeActual);
            model.addRow(new Object[]{p.getCodigo(), p.getNombre(), precioFormateado});
        }
    }

    /**
     * Busca productos cuyo nombre contenga el texto ingresado y los muestra en la tabla.
     * Formatea los precios de los productos encontrados.
     */
    private void buscarProductoPorNombre() {
        String nombre = gestionDeProductosView.getTxtBuscar().getText();
        List<Producto> productos = productoDAO.buscarPorNombre(nombre);
        DefaultTableModel model = (DefaultTableModel) gestionDeProductosView.getTblProductos().getModel();
        model.setRowCount(0);

        Locale localeActual = mensajeHandler.getLocaleActual();

        for (Producto p : productos) {
            String precioFormateado = FormatoUtils.formatearMoneda(p.getPrecio(), localeActual);
            model.addRow(new Object[]{p.getCodigo(), p.getNombre(), precioFormateado});
        }
    }

    /**
     * Elimina el producto que está seleccionado en la tabla de gestión.
     */
    private void eliminarProductoSeleccionado() {
        int filaSeleccionada = gestionDeProductosView.getTblProductos().getSelectedRow();
        if (filaSeleccionada >= 0) {
            int codigo = (int) gestionDeProductosView.getTblProductos().getValueAt(filaSeleccionada, 0);
            int confirm = JOptionPane.showConfirmDialog(gestionDeProductosView, "¿Seguro que desea eliminar el producto con código " + codigo + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                productoDAO.eliminar(codigo);
                listarProductos();
            }
        } else {
            JOptionPane.showMessageDialog(gestionDeProductosView, "Por favor, seleccione un producto de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Abre la ventana interna para añadir un nuevo producto.
     */
    private void abrirAnadirProducto() {
        limpiarCamposAnadirProducto();
        if (!anadirProductoView.isVisible()) {
            desktopPane.add(anadirProductoView);
            anadirProductoView.setVisible(true);
        }
        anadirProductoView.toFront();
    }

    /**
     * Crea un nuevo producto a partir de los datos ingresados en la vista.
     */
    private void crearNuevoProducto() {
        try {
            int codigo = Integer.parseInt(anadirProductoView.getTxtCodigo().getText());
            String nombre = anadirProductoView.getTxtNombre().getText();
            // Se reemplaza la coma por punto para asegurar la correcta conversión a double
            double precio = Double.parseDouble(anadirProductoView.getTxtPrecio().getText().replace(',', '.'));

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(anadirProductoView, "El nombre no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Producto nuevoProducto = new Producto(codigo, nombre, precio);
            productoDAO.crear(nuevoProducto);
            JOptionPane.showMessageDialog(anadirProductoView, "Producto creado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            anadirProductoView.dispose();
            listarProductos();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(anadirProductoView, "El código y el precio deben ser números válidos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Limpia los campos del formulario para añadir un nuevo producto.
     */
    private void limpiarCamposAnadirProducto() {
        anadirProductoView.getTxtCodigo().setText("");
        anadirProductoView.getTxtNombre().setText("");
        anadirProductoView.getTxtPrecio().setText("");
    }

    /**
     * Abre el diálogo para actualizar un producto seleccionado.
     */
    private void abrirDialogoActualizar() {
        int filaSeleccionada = gestionDeProductosView.getTblProductos().getSelectedRow();
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(gestionDeProductosView, "Por favor, seleccione un producto de la tabla para actualizar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int codigoProducto = (int) gestionDeProductosView.getTblProductos().getValueAt(filaSeleccionada, 0);
        this.productoParaActualizar = productoDAO.buscarPorCodigo(codigoProducto);

        if (productoParaActualizar != null) {
            if (!actualizarProductoView.isVisible()) {
                desktopPane.add(actualizarProductoView);
                actualizarProductoView.setVisible(true);
            }
            actualizarProductoView.toFront();
        }
    }

    /**
     * Actualiza el campo seleccionado (Nombre o Precio) del producto en edición.
     */
    private void actualizarCampoSeleccionado() {
        if (productoParaActualizar == null) {
            JOptionPane.showMessageDialog(actualizarProductoView, "No hay un producto seleccionado para actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String campo = (String) actualizarProductoView.getCbxElegir().getSelectedItem();
        String nuevoValorStr = actualizarProductoView.getTxtElegir().getText();

        if (nuevoValorStr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(actualizarProductoView, "El nuevo valor no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            if (campo.equalsIgnoreCase("Nombre") || campo.equalsIgnoreCase("Name") || campo.equalsIgnoreCase("Nom")) {
                productoParaActualizar.setNombre(nuevoValorStr);
            } else if (campo.equalsIgnoreCase("Precio") || campo.equalsIgnoreCase("Price") || campo.equalsIgnoreCase("Prix")) {
                double nuevoPrecio = Double.parseDouble(nuevoValorStr.replace(',', '.'));
                productoParaActualizar.setPrecio(nuevoPrecio);
            }

            productoDAO.actualizar(productoParaActualizar);
            JOptionPane.showMessageDialog(actualizarProductoView, "Producto actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            actualizarProductoView.dispose();
            listarProductos();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(actualizarProductoView, "El valor ingresado no es válido para el campo 'Precio'.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }
}
