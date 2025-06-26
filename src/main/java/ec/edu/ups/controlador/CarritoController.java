package ec.edu.ups.controlador;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.vista.CarritoAnadirView;

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
        carritoAnadirView.getBtnAnadir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                anadirProducto();
            }
        });

        carritoAnadirView.getBtnGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCarrito();
            }
        });

        carritoAnadirView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProductoParaAnadir();
            }
        });

        carritoAnadirView.getBtnLimpiar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCarrito();
            }
        });
    }

    private void buscarProductoParaAnadir() {
        String codigoStr = carritoAnadirView.getTxtCodigo().getText();
        if (codigoStr.isEmpty()) {
            carritoAnadirView.mostrarMensaje("Por favor, ingrese un código de producto.");
            return;
        }

        try {
            int codigo = Integer.parseInt(codigoStr);
            Producto productoEncontrado = productoDAO.buscarPorCodigo(codigo);

            if (productoEncontrado != null) {
                carritoAnadirView.getTxtNombre().setText(productoEncontrado.getNombre());
                carritoAnadirView.getTxtPrecio().setText(String.valueOf(productoEncontrado.getPrecio()));
                carritoAnadirView.mostrarMensaje("Producto encontrado: " + productoEncontrado.getNombre());
            } else {
                carritoAnadirView.mostrarMensaje("Producto con código " + codigo + " no encontrado.");
                carritoAnadirView.limpiarCamposProducto();
            }
        } catch (NumberFormatException ex) {
            carritoAnadirView.mostrarMensaje("El código debe ser un número válido.");
            carritoAnadirView.limpiarCamposProducto();
        }
    }

    private void guardarCarrito() {
        if (carrito.obtenerItems().isEmpty()) {
            carritoAnadirView.mostrarMensaje("El carrito está vacío. Añada productos antes de guardar.");
            return;
        }
        carritoDAO.crear(carrito);
        carritoAnadirView.mostrarMensaje("Carrito creado correctamente.");
        System.out.println("Carritos guardados actualmente: " + carritoDAO.listarTodos());
        carritoAnadirView.limpiarCarritoCompleto();
        this.carrito = new Carrito();
    }

    private void anadirProducto() {
        String codigoStr = carritoAnadirView.getTxtCodigo().getText();
        String cantidadStr = carritoAnadirView.getCbxCantidad().getSelectedItem().toString();

        if (codigoStr.isEmpty()) {
            carritoAnadirView.mostrarMensaje("Por favor, busque un producto por su código primero.");
            return;
        }

        try {
            int codigo = Integer.parseInt(codigoStr);
            int cantidad = Integer.parseInt(cantidadStr);

            Producto producto = productoDAO.buscarPorCodigo(codigo);

            if (producto == null) {
                carritoAnadirView.mostrarMensaje("Producto con código " + codigo + " no encontrado. Use el botón 'Buscar'.");
                carritoAnadirView.limpiarCamposProducto();
                return;
            }

            if (cantidad <= 0) {
                carritoAnadirView.mostrarMensaje("La cantidad debe ser mayor que cero.");
                return;
            }

            carrito.agregarProducto(producto, cantidad);
            carritoAnadirView.mostrarMensaje("Producto '" + producto.getNombre() + "' añadido al carrito.");
            carritoAnadirView.limpiarCamposProducto();

            cargarProductosEnTabla();
            mostrarTotales();

        } catch (NumberFormatException ex) {
            carritoAnadirView.mostrarMensaje("Error en formato de código o cantidad. Asegúrese de que sean números válidos.");
        } catch (Exception ex) {
            carritoAnadirView.mostrarMensaje("Error al añadir producto al carrito: " + ex.getMessage());
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
        carritoAnadirView.mostrarMensaje("Carrito limpiado.");
    }
}