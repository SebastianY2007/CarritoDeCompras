package ec.edu.ups.controlador;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.vista.ActualizarProductoView;
import ec.edu.ups.vista.CarritoAnadirView;
import ec.edu.ups.vista.ProductoAnadirView;
import ec.edu.ups.vista.ProductoListaView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProductoController {

    private final ProductoAnadirView productoAnadirView;
    private final ProductoListaView productoListaView;
    private final CarritoAnadirView carritoAnadirView;
    private final ActualizarProductoView actualizarProductoView;

    private final ProductoDAO productoDAO;

    private static final double IVA_RATE = 0.12;

    public ProductoController(ProductoDAO productoDAO,
                              ProductoAnadirView productoAnadirView,
                              ProductoListaView productoListaView,
                              CarritoAnadirView carritoAnadirView,
                              ActualizarProductoView actualizarProductoView) {

        this.productoDAO = productoDAO;
        this.productoAnadirView = productoAnadirView;
        this.productoListaView = productoListaView;
        this.carritoAnadirView = carritoAnadirView;
        this.actualizarProductoView = actualizarProductoView;

        this.configurarEventosEnVistas();
    }

    private void configurarEventosEnVistas() {
        productoAnadirView.getBtnAceptar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarProducto();
            }
        });

        productoListaView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProducto();
            }
        });

        productoListaView.getBtnListar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarProductos();
            }
        });

        productoListaView.getBtnEliminar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (productoListaView.getTblProductos().getRowCount() > 0) {
                    Object codigoObj = productoListaView.getTblProductos().getModel().getValueAt(0, 0);
                    int codigoProducto = Integer.parseInt(codigoObj.toString());

                    productoDAO.eliminar(codigoProducto);

                    listarProductos();
                    productoListaView.getTxtBuscar().setText("");

                } else {
                    System.out.println("No hay producto para eliminar en la tabla.");
                }
            }
        });

        carritoAnadirView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProductoPorCodigo();
            }
        });

        productoListaView.getBtnActualizar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventanaActualizarProducto();
            }
        });

        actualizarProductoView.getBtnActualizar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarCampoProducto();
            }
        });

        actualizarProductoView.getBtnCancelar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarProductoView.dispose();
            }
        });

        carritoAnadirView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProductoParaCalcular(); // Método para buscar producto y cargarlo en los campos
            }
        });

        carritoAnadirView.getBtnAnadir().addActionListener(new ActionListener() { // Ahora este botón hace el cálculo
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularTotalesDeProductoSeleccionado(); // Este método realizará el cálculo
            }
        });

        carritoAnadirView.getBtnLimpiar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carritoAnadirView.limpiarCamposProductoYTotales(); // Limpia campos de producto y totales
            }
        });

        // El botón Guardar no tiene una lógica de "carrito" en este enfoque simplificado,
        // simplemente se podría usar para un mensaje o se puede eliminar si no se necesita.
        carritoAnadirView.getBtnGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carritoAnadirView.mostrarMensaje("No hay un carrito para guardar en este modo simplificado.");
            }
        });
    }

    private void guardarProducto() {
        int codigo = Integer.parseInt(productoAnadirView.getTxtCodigo().getText());
        String nombre = productoAnadirView.getTxtNombre().getText();
        double precio = Double.parseDouble(productoAnadirView.getTxtPrecio().getText());

        productoDAO.crear(new Producto(codigo, nombre, precio));
        productoAnadirView.mostrarMensaje("Producto guardado correctamente");
        productoAnadirView.limpiarCampos();
        productoAnadirView.mostrarProductos(productoDAO.listarTodos());
    }

    private void buscarProducto() {
        String nombre = productoListaView.getTxtBuscar().getText();

        List<Producto> productosEncontrados = productoDAO.buscarPorNombre(nombre);
        productoListaView.cargarDatos(productosEncontrados);
    }

    private void listarProductos() {
        List<Producto> productos = productoDAO.listarTodos();
        productoListaView.cargarDatos(productos);
    }

    private void buscarProductoPorCodigo() {
        int codigo = Integer.parseInt(carritoAnadirView.getTxtCodigo().getText());
        Producto producto = productoDAO.buscarPorCodigo(codigo);
        if (producto == null) {
            carritoAnadirView.mostrarMensaje("No se encontro el producto");
            carritoAnadirView.getTxtNombre().setText("");
            carritoAnadirView.getTxtPrecio().setText("");
        } else {
            carritoAnadirView.getTxtNombre().setText(producto.getNombre());
            carritoAnadirView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
        }

    }

    private void ventanaActualizarProducto() {
        int selectedRow = productoListaView.getTblProductos().getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(productoListaView, "Por favor, seleccione un producto de la tabla para actualizar.", "Producto no seleccionado", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int codigoActual = Integer.parseInt(productoListaView.getTblProductos().getModel().getValueAt(selectedRow, 0).toString());

        actualizarProductoView.setIdActual(codigoActual);
        productoAnadirView.limpiarCampos();
        productoListaView.getDesktopPane().add(actualizarProductoView);
        actualizarProductoView.setVisible(true);
        actualizarProductoView.moveToFront();
        actualizarProductoView.requestFocus();
    }

    private void actualizarCampoProducto() {
        int idProductoSeleccionado = actualizarProductoView.getIdActual();

        String campoSeleccionado = (String) actualizarProductoView.getCbxElegir().getSelectedItem();
        String nuevoValorStr = actualizarProductoView.getTxtElegir().getText();

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
            actualizarProductoView.mostrarMensaje("Producto actualizado correctamente.");

            actualizarProductoView.dispose();
            listarProductos();

            actualizarProductoView.limpiarCampoElegir();

        } else {
            actualizarProductoView.mostrarMensaje("No se pudo actualizar el producto. Verifique los datos o si el producto existe.");
        }
    }

    private void buscarProductoParaCalcular() {
        String codigoStr = carritoAnadirView.getTxtCodigo().getText();
        if (codigoStr.isEmpty()) {
            carritoAnadirView.mostrarMensaje("Por favor, ingrese un código para buscar.");
            return;
        }

        int codigo = Integer.parseInt(codigoStr);
        Producto producto = productoDAO.buscarPorCodigo(codigo);

        if (producto != null) {
            carritoAnadirView.getTxtNombre().setText(producto.getNombre());
            carritoAnadirView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
            carritoAnadirView.mostrarMensaje("Producto encontrado.");
        } else {
            carritoAnadirView.mostrarMensaje("Producto no encontrado.");
            carritoAnadirView.limpiarCamposProductoYTotales();
        }
    }

    private void calcularTotalesDeProductoSeleccionado() {
        String precioStr = carritoAnadirView.getTxtPrecio().getText();
        String cantidadStr = (String) carritoAnadirView.getCbxCantidad().getSelectedItem();

        if (precioStr.isEmpty() || cantidadStr == null || cantidadStr.isEmpty() || carritoAnadirView.getTxtNombre().getText().isEmpty()) {
            carritoAnadirView.mostrarMensaje("Por favor, busque un producto y seleccione una cantidad válida.");
            return;
        }

        double precioUnitario = Double.parseDouble(precioStr);
        int cantidad = Integer.parseInt(cantidadStr);

        double subtotalProducto = precioUnitario * cantidad;
        double ivaProducto = subtotalProducto * IVA_RATE;
        double totalProducto = subtotalProducto + ivaProducto;

        carritoAnadirView.getTxtSubtotal().setText(String.format("%.2f", subtotalProducto));
        carritoAnadirView.getTxtIva().setText(String.format("%.2f", ivaProducto));
        carritoAnadirView.getTxtTotal().setText(String.format("%.2f", totalProducto));

        carritoAnadirView.mostrarMensaje("Totales calculados para el producto.");
    }
}