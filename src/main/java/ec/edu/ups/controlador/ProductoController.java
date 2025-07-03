package ec.edu.ups.controlador;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.vista.producto.ActualizarProductoView;
import ec.edu.ups.vista.carrito.CarritoAnadirView;
import ec.edu.ups.vista.producto.AnadirProductoView;
import ec.edu.ups.vista.producto.GestionDeProductosView;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class ProductoController {

    // --- Vistas y Modelo ---
    private final AnadirProductoView anadirProductoView;
    private final GestionDeProductosView gestionDeProductosView;
    private final CarritoAnadirView carritoAnadirView;
    private final ActualizarProductoView actualizarProductoView;
    private final JDesktopPane desktopPane;
    private final ProductoDAO productoDAO;
    private final MensajeInternacionalizacionHandler mensajeHandler;

    public ProductoController(ProductoDAO productoDAO, AnadirProductoView anadirProductoView,
                              GestionDeProductosView gestionDeProductosView, ActualizarProductoView actualizarProductoView,
                              CarritoAnadirView carritoAnadirView, JDesktopPane desktopPane,
                              MensajeInternacionalizacionHandler mensajeHandler) {
        this.productoDAO = productoDAO;
        this.anadirProductoView = anadirProductoView;
        this.gestionDeProductosView = gestionDeProductosView;
        this.carritoAnadirView = carritoAnadirView;
        this.actualizarProductoView = actualizarProductoView;
        this.desktopPane = desktopPane;
        this.mensajeHandler = mensajeHandler;

        addListeners();
    }

    private void addListeners() {
        if (gestionDeProductosView != null) {
            gestionDeProductosView.getBtnBuscar().addActionListener(e -> buscarProducto());
            gestionDeProductosView.getBtnListar().addActionListener(e -> listarProductos());
            gestionDeProductosView.getBtnEliminar().addActionListener(e -> eliminarProducto());
            gestionDeProductosView.getBtnAgregar().addActionListener(e -> abrirVentanaAnadir());
            gestionDeProductosView.getBtnActualizar().addActionListener(e -> abrirVentanaActualizar());
        }

        if (anadirProductoView != null) {
            anadirProductoView.getBtnAgregar().addActionListener(e -> guardarProducto());
            anadirProductoView.getBtnLimpiar().addActionListener(e -> anadirProductoView.limpiarCampos());
        }

        if (actualizarProductoView != null) {
            actualizarProductoView.getBtnActualizar().addActionListener(e -> actualizarCampoProducto());
            actualizarProductoView.getBtnCancelar().addActionListener(e -> actualizarProductoView.dispose());
        }
    }

    // --- Lógica de la Vista de Añadir ---
    private void guardarProducto() {
        try {
            int codigo = Integer.parseInt(anadirProductoView.getTxtCodigo().getText().trim());
            String nombre = anadirProductoView.getTxtNombre().getText().trim();
            double precio = Double.parseDouble(anadirProductoView.getTxtPrecio().getText().trim());

            if (nombre.isEmpty()) {
                anadirProductoView.mostrarMensaje("El nombre no puede estar vacío.", JOptionPane.WARNING_MESSAGE);
                return;
            }

            productoDAO.crear(new Producto(codigo, nombre, precio));
            anadirProductoView.mostrarMensaje("Producto guardado correctamente", JOptionPane.INFORMATION_MESSAGE);
            anadirProductoView.dispose();
            listarProductos();
        } catch (NumberFormatException e) {
            anadirProductoView.mostrarMensaje("El código y el precio deben ser números válidos.", JOptionPane.ERROR_MESSAGE);
        }
    }

    // --- Lógica de la Vista de Gestión ---
    public void listarProductos() {
        List<Producto> productos = productoDAO.listarTodos();
        if (gestionDeProductosView != null) {
            // Llama al método en la vista para poblar la tabla
            gestionDeProductosView.cargarDatos(productos);
        }
    }

    private void buscarProducto() {
        if (gestionDeProductosView == null) return;
        String nombre = gestionDeProductosView.getTxtBuscar().getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(gestionDeProductosView, "Ingrese un nombre de producto para buscar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        List<Producto> productosEncontrados = productoDAO.buscarPorNombre(nombre);
        gestionDeProductosView.cargarDatos(productosEncontrados);

        if (productosEncontrados.isEmpty()) {
            JOptionPane.showMessageDialog(gestionDeProductosView, "No se encontraron productos con ese nombre.", "Búsqueda", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void eliminarProducto() {
        int selectedRow = gestionDeProductosView.getTblProductos().getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(gestionDeProductosView, "Por favor, seleccione un producto para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Object codigoObj = gestionDeProductosView.getTblProductos().getModel().getValueAt(selectedRow, 0);
        int codigoProducto = Integer.parseInt(codigoObj.toString().trim());

        int confirm = JOptionPane.showConfirmDialog(gestionDeProductosView, "¿Está seguro de que desea eliminar este producto?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            productoDAO.eliminar(codigoProducto);
            listarProductos(); // Actualizar la tabla
        }
    }

    private void abrirVentanaAnadir() {
        if (anadirProductoView != null) {
            anadirProductoView.limpiarCampos();
            mostrarVentana(anadirProductoView);
        }
    }

    private void abrirVentanaActualizar() {
        int selectedRow = gestionDeProductosView.getTblProductos().getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(gestionDeProductosView, "Por favor, seleccione un producto para actualizar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int codigoActual = Integer.parseInt(gestionDeProductosView.getTblProductos().getModel().getValueAt(selectedRow, 0).toString().trim());

        if (actualizarProductoView != null) {
            actualizarProductoView.setIdActual(codigoActual);
            mostrarVentana(actualizarProductoView);
        }
    }

    private void actualizarCampoProducto() {
        try {
            int idProductoSeleccionado = actualizarProductoView.getIdActual();
            String campoSeleccionado = (String) actualizarProductoView.getCbxElegir().getSelectedItem();
            String nuevoValorStr = actualizarProductoView.getTxtElegir().getText().trim();

            if (nuevoValorStr.isEmpty()) {
                actualizarProductoView.mostrarMensaje("El nuevo valor no puede estar vacío.", JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean actualizado = false;
            ResourceBundle mensajes = ResourceBundle.getBundle("mensajes", new Locale(mensajeHandler.getLenguajeActual(), mensajeHandler.getPaisActual()));

            if (campoSeleccionado.equals(mensajes.getString("actualizarProducto.opcion.codigo"))) {
                int nuevoCodigo = Integer.parseInt(nuevoValorStr);
                actualizado = productoDAO.actualizarCodigo(idProductoSeleccionado, nuevoCodigo);
            } else if (campoSeleccionado.equals(mensajes.getString("actualizarProducto.opcion.nombre"))) {
                actualizado = productoDAO.actualizarNombre(idProductoSeleccionado, nuevoValorStr);
            } else if (campoSeleccionado.equals(mensajes.getString("actualizarProducto.opcion.precio"))) {
                double nuevoPrecio = Double.parseDouble(nuevoValorStr);
                actualizado = productoDAO.actualizarPrecio(idProductoSeleccionado, nuevoPrecio);
            }

            if (actualizado) {
                actualizarProductoView.mostrarMensaje("Producto actualizado correctamente.", JOptionPane.INFORMATION_MESSAGE);
                actualizarProductoView.dispose();
                listarProductos();
            } else {
                actualizarProductoView.mostrarMensaje("No se pudo actualizar el producto.", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            actualizarProductoView.mostrarMensaje("El valor ingresado no es un número válido para este campo.", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarVentana(JInternalFrame frame) {
        if (frame != null) {
            if (!frame.isVisible()) {
                desktopPane.add(frame);
                frame.setVisible(true);
            }
            frame.toFront();
        }
    }
}
