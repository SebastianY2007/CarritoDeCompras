package ec.edu.ups.controlador;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.vista.carrito.CarritoAnadirView;

import javax.swing.JOptionPane; // ¡Asegúrate de que esta importación exista!
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CarritoController {

    private final CarritoDAO carritoDAO;
    private final ProductoDAO productoDAO;
    private final CarritoAnadirView carritoAnadirView;
    private Carrito carrito;

    public CarritoController(CarritoDAO carritoDAO,
                             ProductoDAO productoDAO,
                             CarritoAnadirView carritoAnadirView) {
        this.carritoDAO = carritoDAO;
        this.productoDAO = productoDAO;
        this.carritoAnadirView = carritoAnadirView;
        this.carrito = new Carrito();
        configurarEventosEnVistas();
    }

    private void configurarEventosEnVistas() {
        carritoAnadirView.getBtnAnadir().addActionListener(e -> anadirProducto());
        carritoAnadirView.getBtnGuardar().addActionListener(e -> guardarCarrito());
        carritoAnadirView.getBtnBuscar().addActionListener(e -> buscarProductoParaAnadir());
        carritoAnadirView.getBtnLimpiar().addActionListener(e -> limpiarCarrito());
    }

    private void buscarProductoParaAnadir() {
        String codigoStr = carritoAnadirView.getTxtCodigo().getText();
        if (codigoStr.isEmpty()) {
            // Mensaje de advertencia
            carritoAnadirView.mostrarMensaje("Por favor, ingrese un código de producto.", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int codigo = Integer.parseInt(codigoStr);
            Producto productoEncontrado = productoDAO.buscarPorCodigo(codigo);

            if (productoEncontrado != null) {
                carritoAnadirView.getTxtNombre().setText(productoEncontrado.getNombre());
                carritoAnadirView.getTxtPrecio().setText(String.valueOf(productoEncontrado.getPrecio()));
                // Mensaje de información
                carritoAnadirView.mostrarMensaje("Producto encontrado: " + productoEncontrado.getNombre(), JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Mensaje de advertencia
                carritoAnadirView.mostrarMensaje("Producto con código " + codigo + " no encontrado.", JOptionPane.WARNING_MESSAGE);
                carritoAnadirView.limpiarCamposProducto();
            }
        } catch (NumberFormatException ex) {
            // Mensaje de error
            carritoAnadirView.mostrarMensaje("El código debe ser un número válido.", JOptionPane.ERROR_MESSAGE);
            carritoAnadirView.limpiarCamposProducto();
        }
    }

    private void guardarCarrito() {
        if (carrito.obtenerItems().isEmpty()) {
            // Mensaje de advertencia
            carritoAnadirView.mostrarMensaje("El carrito está vacío. Añada productos antes de guardar.", JOptionPane.WARNING_MESSAGE);
            return;
        }
        carritoDAO.crear(carrito);
        // Mensaje de información
        carritoAnadirView.mostrarMensaje("Carrito creado correctamente.", JOptionPane.INFORMATION_MESSAGE);
        System.out.println("Carritos guardados actualmente: " + carritoDAO.listarTodos());
        carritoAnadirView.limpiarCarritoCompleto();
        this.carrito = new Carrito();
    }

    private void anadirProducto() {
        String codigoStr = carritoAnadirView.getTxtCodigo().getText();
        String cantidadStr = carritoAnadirView.getCbxCantidad().getSelectedItem().toString();

        if (codigoStr.isEmpty()) {
            // Mensaje de advertencia
            carritoAnadirView.mostrarMensaje("Por favor, busque un producto por su código primero.", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int codigo = Integer.parseInt(codigoStr);
            int cantidad = Integer.parseInt(cantidadStr);

            Producto producto = productoDAO.buscarPorCodigo(codigo);

            if (producto == null) {
                // Mensaje de error
                carritoAnadirView.mostrarMensaje("Producto con código " + codigo + " no encontrado. Use el botón 'Buscar'.", JOptionPane.ERROR_MESSAGE);
                carritoAnadirView.limpiarCamposProducto();
                return;
            }

            if (cantidad <= 0) {
                // Mensaje de advertencia
                carritoAnadirView.mostrarMensaje("La cantidad debe ser mayor que cero.", JOptionPane.WARNING_MESSAGE);
                return;
            }

            carrito.agregarProducto(producto, cantidad);
            // Mensaje de información
            carritoAnadirView.mostrarMensaje("Producto '" + producto.getNombre() + "' añadido al carrito.", JOptionPane.INFORMATION_MESSAGE);
            carritoAnadirView.limpiarCamposProducto();

            cargarProductosEnTabla();
            mostrarTotales();

        } catch (NumberFormatException ex) {
            // Mensaje de error
            carritoAnadirView.mostrarMensaje("Error en formato de código o cantidad. Asegúrese de que sean números válidos.", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            // Mensaje de error
            carritoAnadirView.mostrarMensaje("Error al añadir producto al carrito: " + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void cargarProductosEnTabla() {
        List<ItemCarrito> items = carrito.obtenerItems();
        DefaultTableModel modelo = (DefaultTableModel) carritoAnadirView.getTblProductos().getModel();
        modelo.setRowCount(0);
        for (ItemCarrito item : items) {
            modelo.addRow(new Object[]{
                    item.getProducto().getCodigo(),
                    item.getProducto().getNombre(),
                    item.getProducto().getPrecio(),
                    item.getCantidad(),
                    item.getProducto().getPrecio() * item.getCantidad()
            });
        }
    }

    private void mostrarTotales() {
        carritoAnadirView.getTxtSubtotal().setText(String.format("%.2f", carrito.calcularSubtotal()));
        carritoAnadirView.getTxtIva().setText(String.format("%.2f", carrito.calcularIVA()));
        carritoAnadirView.getTxtTotal().setText(String.format("%.2f", carrito.calcularTotal()));
    }

    private void limpiarCarrito() {
        this.carrito = new Carrito();
        carritoAnadirView.limpiarCarritoCompleto();
        // Mensaje de información
        carritoAnadirView.mostrarMensaje("Carrito limpiado.", JOptionPane.INFORMATION_MESSAGE);
    }
}