package ec.edu.ups.controlador;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.producto.ActualizarProductoView;
import ec.edu.ups.vista.producto.AnadirProductoView;
import ec.edu.ups.vista.producto.GestionDeProductosView;
import ec.edu.ups.vista.carrito.CrearCarritoView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ProductoController {

    private final ProductoDAO productoDAO;
    private final AnadirProductoView anadirProductoView;
    private final GestionDeProductosView gestionDeProductosView;
    private final ActualizarProductoView actualizarProductoView;
    private final JDesktopPane desktopPane;
    private final MensajeInternacionalizacionHandler mensajeHandler;

    private Producto productoParaActualizar;

    public ProductoController(ProductoDAO productoDAO, AnadirProductoView anadirProductoView, GestionDeProductosView gestionDeProductosView, ActualizarProductoView actualizarProductoView, CrearCarritoView crearCarritoView, JDesktopPane desktopPane, MensajeInternacionalizacionHandler mensajeHandler) {
        this.productoDAO = productoDAO;
        this.anadirProductoView = anadirProductoView;
        this.gestionDeProductosView = gestionDeProductosView;
        this.actualizarProductoView = actualizarProductoView;
        this.desktopPane = desktopPane;
        this.mensajeHandler = mensajeHandler;
    }

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

    public void listarProductos() {
        List<Producto> productos = productoDAO.listarTodos();
        DefaultTableModel model = (DefaultTableModel) gestionDeProductosView.getTblProductos().getModel();
        model.setColumnIdentifiers(new Object[]{"Código", "Nombre", "Precio"});
        model.setRowCount(0);
        for (Producto p : productos) {
            model.addRow(new Object[]{p.getCodigo(), p.getNombre(), String.format("%.2f", p.getPrecio())});
        }
    }

    private void buscarProductoPorNombre() {
        String nombre = gestionDeProductosView.getTxtBuscar().getText();
        List<Producto> productos = productoDAO.buscarPorNombre(nombre);
        DefaultTableModel model = (DefaultTableModel) gestionDeProductosView.getTblProductos().getModel();
        model.setRowCount(0);
        for (Producto p : productos) {
            model.addRow(new Object[]{p.getCodigo(), p.getNombre(), String.format("%.2f", p.getPrecio())});
        }
    }

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

    private void abrirAnadirProducto() {
        limpiarCamposAnadirProducto();
        if (!anadirProductoView.isVisible()) {
            desktopPane.add(anadirProductoView);
            anadirProductoView.setVisible(true);
        }
        anadirProductoView.toFront();
    }

    private void crearNuevoProducto() {
        try {
            int codigo = Integer.parseInt(anadirProductoView.getTxtCodigo().getText());
            String nombre = anadirProductoView.getTxtNombre().getText();
            double precio = Double.parseDouble(anadirProductoView.getTxtPrecio().getText());

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

    private void limpiarCamposAnadirProducto() {
        anadirProductoView.getTxtCodigo().setText("");
        anadirProductoView.getTxtNombre().setText("");
        anadirProductoView.getTxtPrecio().setText("");
    }

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
                double nuevoPrecio = Double.parseDouble(nuevoValorStr);
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