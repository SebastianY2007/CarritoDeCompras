package ec.edu.ups.controlador;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.vista.ActualizarProductoView;
import ec.edu.ups.vista.CarritoAnadirView;
import ec.edu.ups.vista.ProductoAnadirView;
import ec.edu.ups.vista.GestionDeProductosView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProductoController {

    private final ProductoAnadirView productoAnadirView;
    private final GestionDeProductosView gestionDeProductosView;
    private final CarritoAnadirView carritoAnadirView;
    private ActualizarProductoView actualizarProductoView = new ActualizarProductoView()  ;
    private final JDesktopPane desktopPane;

    private final ProductoDAO productoDAO;

    private static final double IVA_RATE = 0.12;

    public ProductoController(ProductoDAO productoDAO,
                              ProductoAnadirView productoAnadirView,
                              GestionDeProductosView gestionDeProductosView,
                              CarritoAnadirView carritoAnadirView,
                              JDesktopPane desktopPane) {

        this.productoDAO = productoDAO;
        this.productoAnadirView = productoAnadirView;
        this.gestionDeProductosView = gestionDeProductosView;
        this.carritoAnadirView = carritoAnadirView;
        this.actualizarProductoView = actualizarProductoView;
        this.desktopPane = desktopPane;

        this.configurarEventosEnVistas();
    }

    private void configurarEventosEnVistas() {
        productoAnadirView.getBtnAceptar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarProducto();
            }
        });

        gestionDeProductosView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProducto();
            }
        });

        gestionDeProductosView.getBtnListar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarProductos();
            }
        });

        gestionDeProductosView.getBtnEliminar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gestionDeProductosView.getTblProductos().getRowCount() > 0) {
                    Object codigoObj = gestionDeProductosView.getTblProductos().getModel().getValueAt(0, 0);
                    int codigoProducto = Integer.parseInt(codigoObj.toString().trim());

                    productoDAO.eliminar(codigoProducto);

                    listarProductos();
                    gestionDeProductosView.getTxtBuscar().setText("");

                } else {
                    System.out.println("No hay producto para eliminar en la tabla.");
                }
            }
        });

        gestionDeProductosView.getBtnAgregar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirProductoAnadirView();
            }
        });

        carritoAnadirView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProductoPorCodigo();
            }
        });

        gestionDeProductosView.getBtnActualizar().addActionListener(new ActionListener() {
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
                buscarProductoParaCalcular();
            }
        });

        carritoAnadirView.getBtnAnadir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularTotalesDeProductoSeleccionado();
            }
        });

        carritoAnadirView.getBtnLimpiar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carritoAnadirView.limpiarCamposProductoYTotales();
            }
        });

        carritoAnadirView.getBtnGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carritoAnadirView.mostrarMensaje("No hay un carrito para guardar en este modo simplificado.");
            }
        });
    }

    private void abrirProductoAnadirView() {
        if (desktopPane != null && !productoAnadirView.isVisible()) {
            desktopPane.add(productoAnadirView);
            productoAnadirView.setVisible(true);
            productoAnadirView.toFront();
        } else if (productoAnadirView.isVisible()) {
            productoAnadirView.toFront();
        }
    }


    private void guardarProducto() {
        int codigo = Integer.parseInt(productoAnadirView.getTxtCodigo().getText().trim());
        String nombre = productoAnadirView.getTxtNombre().getText().trim();
        double precio = Double.parseDouble(productoAnadirView.getTxtPrecio().getText().trim());

        productoDAO.crear(new Producto(codigo, nombre, precio));
        productoAnadirView.mostrarMensaje("Producto guardado correctamente");
        productoAnadirView.limpiarCampos();
        productoAnadirView.mostrarProductos(productoDAO.listarTodos());
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

    private void buscarProductoPorCodigo() {
        String codigoStr = carritoAnadirView.getTxtCodigo().getText().trim();
        if (codigoStr.isEmpty()) {
            carritoAnadirView.mostrarMensaje("Por favor, ingrese un código para buscar.");
            return;
        }

        int codigo = Integer.parseInt(codigoStr);
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
        int selectedRow = gestionDeProductosView.getTblProductos().getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(gestionDeProductosView, "Por favor, seleccione un producto de la tabla para actualizar.", "Producto no seleccionado", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int codigoActual = Integer.parseInt(gestionDeProductosView.getTblProductos().getModel().getValueAt(selectedRow, 0).toString().trim());

        actualizarProductoView.setIdActual(codigoActual);
        productoAnadirView.limpiarCampos();
        if (desktopPane != null && actualizarProductoView.getParent() == null) {
            desktopPane.add(actualizarProductoView);
        }
        actualizarProductoView.setVisible(true);
        actualizarProductoView.moveToFront();
        actualizarProductoView.requestFocus();
    }

    private void actualizarCampoProducto() {
        int idProductoSeleccionado = actualizarProductoView.getIdActual();

        String campoSeleccionado = (String) actualizarProductoView.getCbxElegir().getSelectedItem();
        String nuevoValorStr = actualizarProductoView.getTxtElegir().getText().trim();

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
        String codigoStr = carritoAnadirView.getTxtCodigo().getText().trim();
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
        String precioStr = carritoAnadirView.getTxtPrecio().getText().trim();
        String cantidadStr = String.valueOf(carritoAnadirView.getCbxCantidad().getSelectedItem()).trim();

        if (precioStr.isEmpty() || cantidadStr.isEmpty() || carritoAnadirView.getTxtNombre().getText().isEmpty()) {
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