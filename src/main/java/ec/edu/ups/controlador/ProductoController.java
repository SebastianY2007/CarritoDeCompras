package ec.edu.ups.controlador;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.vista.producto.ActualizarProductoView;
import ec.edu.ups.vista.carrito.CarritoAnadirView;
import ec.edu.ups.vista.producto.AnadirProductoView;
import ec.edu.ups.vista.producto.GestionDeProductosView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProductoController {

    private final AnadirProductoView anadirProductoView;
    private final GestionDeProductosView gestionDeProductosView;
    private final CarritoAnadirView carritoAnadirView;
    private ActualizarProductoView actualizarProductoView = new ActualizarProductoView();
    private final JDesktopPane desktopPane;

    private final ProductoDAO productoDAO;

    private static final double IVA_RATE = 0.12;

    public ProductoController(ProductoDAO productoDAO,
                              AnadirProductoView anadirProductoView,
                              GestionDeProductosView gestionDeProductosView,
                              CarritoAnadirView carritoAnadirView,
                              JDesktopPane desktopPane) {

        this.productoDAO = productoDAO;
        this.anadirProductoView = anadirProductoView;
        this.gestionDeProductosView = gestionDeProductosView;
        this.carritoAnadirView = carritoAnadirView;
        this.actualizarProductoView = actualizarProductoView;
        this.desktopPane = desktopPane;

        this.configurarEventosEnVistas();
    }

    private void configurarEventosEnVistas() {
        anadirProductoView.getBtnAgregar().addActionListener(e -> guardarProducto());

        gestionDeProductosView.getBtnBuscar().addActionListener(e -> buscarProducto());

        gestionDeProductosView.getBtnListar().addActionListener(e -> listarProductos());

        gestionDeProductosView.getBtnEliminar().addActionListener(e -> {
            if (gestionDeProductosView.getTblProductos().getRowCount() > 0) {
                // Se asume que la fila seleccionada es la que se quiere eliminar
                int selectedRow = gestionDeProductosView.getTblProductos().getSelectedRow();
                if (selectedRow >= 0) {
                    Object codigoObj = gestionDeProductosView.getTblProductos().getModel().getValueAt(selectedRow, 0);
                    int codigoProducto = Integer.parseInt(codigoObj.toString().trim());

                    productoDAO.eliminar(codigoProducto);
                    listarProductos();
                    gestionDeProductosView.getTxtBuscar().setText("");
                } else {
                    // Si no hay fila seleccionada, se podría mostrar un mensaje
                    JOptionPane.showMessageDialog(gestionDeProductosView, "Por favor, seleccione un producto para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                System.out.println("No hay productos en la tabla para eliminar.");
            }
        });

        gestionDeProductosView.getBtnAgregar().addActionListener(e -> abrirProductoAnadirView());

        gestionDeProductosView.getBtnActualizar().addActionListener(e -> ventanaActualizarProducto());

        actualizarProductoView.getBtnActualizar().addActionListener(e -> actualizarCampoProducto());

        actualizarProductoView.getBtnCancelar().addActionListener(e -> actualizarProductoView.dispose());

        // Listener para el botón de buscar en la vista del carrito
        carritoAnadirView.getBtnBuscar().addActionListener(e -> buscarProductoParaCalcular());

        carritoAnadirView.getBtnAnadir().addActionListener(e -> calcularTotalesDeProductoSeleccionado());

        carritoAnadirView.getBtnLimpiar().addActionListener(e -> carritoAnadirView.limpiarCamposProductoYTotales());

        carritoAnadirView.getBtnGuardar().addActionListener(e ->
                carritoAnadirView.mostrarMensaje("No hay un carrito para guardar en este modo simplificado.", JOptionPane.INFORMATION_MESSAGE)
        );
    }

    private void abrirProductoAnadirView() {
        if (desktopPane != null && !anadirProductoView.isVisible()) {
            desktopPane.add(anadirProductoView);
            anadirProductoView.setVisible(true);
            anadirProductoView.toFront();
        } else if (anadirProductoView.isVisible()) {
            anadirProductoView.toFront();
        }
    }

    private void guardarProducto() {
        try {
            int codigo = Integer.parseInt(anadirProductoView.getTxtCodigo().getText().trim());
            String nombre = anadirProductoView.getTxtNombre().getText().trim();
            double precio = Double.parseDouble(anadirProductoView.getTxtPrecio().getText().trim());

            productoDAO.crear(new Producto(codigo, nombre, precio));
            anadirProductoView.mostrarMensaje("Producto guardado correctamente", JOptionPane.INFORMATION_MESSAGE);
            anadirProductoView.limpiarCampos();
            // Llama a listarProductos en la vista de gestión para actualizar la tabla
            listarProductos();
        } catch (NumberFormatException e) {
            anadirProductoView.mostrarMensaje("El código y el precio deben ser números válidos.", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarProducto() {
        String nombre = gestionDeProductosView.getTxtBuscar().getText().trim();
        List<Producto> productosEncontrados = productoDAO.buscarPorNombre(nombre);
        gestionDeProductosView.cargarDatos(productosEncontrados);
    }

    public void listarProductos() {
        List<Producto> productos = productoDAO.listarTodos();
        gestionDeProductosView.cargarDatos(productos);
    }

    private void ventanaActualizarProducto() {
        int selectedRow = gestionDeProductosView.getTblProductos().getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(gestionDeProductosView, "Por favor, seleccione un producto de la tabla para actualizar.", "Producto no seleccionado", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int codigoActual = Integer.parseInt(gestionDeProductosView.getTblProductos().getModel().getValueAt(selectedRow, 0).toString().trim());

        actualizarProductoView.setIdActual(codigoActual);
        if (desktopPane != null && actualizarProductoView.getParent() == null) {
            desktopPane.add(actualizarProductoView);
        }
        actualizarProductoView.setVisible(true);
        actualizarProductoView.moveToFront();
        actualizarProductoView.requestFocus();
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

            switch (campoSeleccionado) {
                case "Codigo":
                    int nuevoCodigo = Integer.parseInt(nuevoValorStr);
                    actualizado = productoDAO.actualizarCodigo(idProductoSeleccionado, nuevoCodigo);
                    break;
                case "Nombre":
                    actualizado = productoDAO.actualizarNombre(idProductoSeleccionado, nuevoValorStr);
                    break;
                case "Precio":
                    double nuevoPrecio = Double.parseDouble(nuevoValorStr);
                    actualizado = productoDAO.actualizarPrecio(idProductoSeleccionado, nuevoPrecio);
                    break;
                default:
                    return;
            }

            if (actualizado) {
                actualizarProductoView.mostrarMensaje("Producto actualizado correctamente.", JOptionPane.INFORMATION_MESSAGE);
                actualizarProductoView.dispose();
                listarProductos();
                actualizarProductoView.limpiarCampoElegir();
            } else {
                actualizarProductoView.mostrarMensaje("No se pudo actualizar el producto. Verifique los datos o si el producto existe.", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            actualizarProductoView.mostrarMensaje("El valor ingresado no tiene el formato correcto (número).", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarProductoParaCalcular() {
        String codigoStr = carritoAnadirView.getTxtCodigo().getText().trim();
        if (codigoStr.isEmpty()) {
            carritoAnadirView.mostrarMensaje("Por favor, ingrese un código para buscar.", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int codigo = Integer.parseInt(codigoStr);
            Producto producto = productoDAO.buscarPorCodigo(codigo);

            if (producto != null) {
                carritoAnadirView.getTxtNombre().setText(producto.getNombre());
                carritoAnadirView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
                carritoAnadirView.mostrarMensaje("Producto encontrado.", JOptionPane.INFORMATION_MESSAGE);
            } else {
                carritoAnadirView.mostrarMensaje("Producto no encontrado.", JOptionPane.WARNING_MESSAGE);
                carritoAnadirView.limpiarCamposProductoYTotales();
            }
        } catch (NumberFormatException e) {
            carritoAnadirView.mostrarMensaje("El código debe ser un número.", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void calcularTotalesDeProductoSeleccionado() {
        String precioStr = carritoAnadirView.getTxtPrecio().getText().trim();
        String cantidadStr = String.valueOf(carritoAnadirView.getCbxCantidad().getSelectedItem()).trim();

        if (precioStr.isEmpty() || cantidadStr.isEmpty() || carritoAnadirView.getTxtNombre().getText().isEmpty()) {
            carritoAnadirView.mostrarMensaje("Por favor, busque un producto y seleccione una cantidad válida.", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            double precioUnitario = Double.parseDouble(precioStr);
            int cantidad = Integer.parseInt(cantidadStr);

            double subtotalProducto = precioUnitario * cantidad;
            double ivaProducto = subtotalProducto * IVA_RATE;
            double totalProducto = subtotalProducto + ivaProducto;

            carritoAnadirView.getTxtSubtotal().setText(String.format("%.2f", subtotalProducto));
            carritoAnadirView.getTxtIva().setText(String.format("%.2f", ivaProducto));
            carritoAnadirView.getTxtTotal().setText(String.format("%.2f", totalProducto));

            carritoAnadirView.mostrarMensaje("Totales calculados para el producto.", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            carritoAnadirView.mostrarMensaje("El precio o la cantidad no son números válidos.", JOptionPane.ERROR_MESSAGE);
        }
    }
}