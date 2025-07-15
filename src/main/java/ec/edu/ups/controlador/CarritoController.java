package ec.edu.ups.controlador;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.vista.carrito.AgregarProductoCarritoView;
import ec.edu.ups.vista.carrito.CrearCarritoView;
import ec.edu.ups.vista.carrito.GestionarCarritoAdministradorView;
import ec.edu.ups.vista.carrito.GestionarCarritoUsuarioView;
import ec.edu.ups.vista.carrito.ModificarCantidadProductoCarritoView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class CarritoController {

    private final CarritoDAO carritoDAO;
    private final ProductoDAO productoDAO;
    private final CrearCarritoView crearCarritoView;
    private final GestionarCarritoUsuarioView gestionarUsuarioView;
    private final GestionarCarritoAdministradorView gestionarAdminView;
    private Usuario usuarioAutenticado;
    private Carrito carritoEnCreacion;
    private Carrito carritoSeleccionado;

    public CarritoController(CarritoDAO carritoDAO, ProductoDAO productoDAO, CrearCarritoView ccv, GestionarCarritoUsuarioView gcuv, GestionarCarritoAdministradorView gcav) {
        this.carritoDAO = carritoDAO;
        this.productoDAO = productoDAO;
        this.crearCarritoView = ccv;
        this.gestionarUsuarioView = gcuv;
        this.gestionarAdminView = gcav;
    }

    public void setUsuarioAutenticado(Usuario usuario) {
        this.usuarioAutenticado = usuario;
    }

    public void initListeners() {
        crearCarritoView.getBtnBuscar().addActionListener(e -> buscarProductoParaNuevoCarrito());
        crearCarritoView.getBtnAgregar().addActionListener(e -> agregarItemANuevoCarrito());
        crearCarritoView.getBtnCrearCarrito().addActionListener(e -> guardarNuevoCarrito());

        gestionarAdminView.getBtnListar().addActionListener(e -> listarTodosLosCarritos());

        gestionarUsuarioView.getBtnListarMisCarritos().addActionListener(e -> listarCarritosDeUsuario());
        gestionarUsuarioView.getBtnBuscar().addActionListener(e -> buscarCarritoParaEditar());
        gestionarUsuarioView.getBtnEliminarProducto().addActionListener(e -> eliminarProductoDeCarrito());
        gestionarUsuarioView.getBtnModificarCantidad().addActionListener(e -> abrirDialogoModificarCantidad());
        gestionarUsuarioView.getBtnAgregarProducto().addActionListener(e -> abrirDialogoAgregarProducto());
        gestionarUsuarioView.getBtnGuardarCarrito().addActionListener(e -> guardarCambiosCarrito());
        gestionarUsuarioView.getBtnEliminarCarrito().addActionListener(e -> eliminarCarritoCompleto());
        gestionarUsuarioView.getTblCarrito().getSelectionModel().addListSelectionListener(e -> actualizarEstadoBotonesEdicion());
    }

    private void listarTodosLosCarritos() {
        DefaultTableModel model = (DefaultTableModel) gestionarAdminView.getTblCarritos().getModel();
        model.setColumnIdentifiers(new Object[]{"Código", "Productos Agregados", "Propietario", "Total"});
        model.setRowCount(0);

        List<Carrito> carritos = carritoDAO.obtenerTodos();
        for (Carrito c : carritos) {
            String propietario = (c.getUsuario() != null) ? c.getUsuario().getCedula() : "N/A";

            model.addRow(new Object[]{
                    c.getCodigo(),
                    c.getItems().size(),
                    propietario,
                    String.format("$%.2f", c.getTotal())
            });
        }
    }

    public void iniciarNuevoCarrito() {
        this.carritoEnCreacion = new Carrito(0, usuarioAutenticado);
        limpiarVistaCrearCarrito();
    }

    private void buscarProductoParaNuevoCarrito() {
        try {
            int codigo = Integer.parseInt(crearCarritoView.getTxtCodigo().getText());
            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto != null) {
                crearCarritoView.getTxtNombre().setText(producto.getNombre());
                crearCarritoView.getTxtPrecio().setText(String.format("%.2f", producto.getPrecio()));
            } else {
                JOptionPane.showMessageDialog(crearCarritoView, "Producto no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                limpiarDatosProductoCrear();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(crearCarritoView, "El código del producto debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregarItemANuevoCarrito() {
        try {
            int codigoProducto = Integer.parseInt(crearCarritoView.getTxtCodigo().getText());
            Producto producto = productoDAO.buscarPorCodigo(codigoProducto);
            if (producto == null) {
                JOptionPane.showMessageDialog(crearCarritoView, "Producto no encontrado. Por favor, busque un producto válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int cantidad = (int) crearCarritoView.getCbxCantidad().getSelectedItem();
            carritoEnCreacion.agregarItem(producto, cantidad);
            actualizarTablaYTotalesCrear();
            limpiarDatosProductoCrear();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(crearCarritoView, "El código de producto debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void guardarNuevoCarrito() {
        if (carritoEnCreacion.getItems().isEmpty()) {
            JOptionPane.showMessageDialog(crearCarritoView, "El carrito está vacío.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        carritoDAO.crear(carritoEnCreacion);
        JOptionPane.showMessageDialog(crearCarritoView, "Carrito creado exitosamente con Código: " + carritoEnCreacion.getCodigo(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
        crearCarritoView.dispose();
    }

    private void listarCarritosDeUsuario() {
        DefaultTableModel model = (DefaultTableModel) gestionarUsuarioView.getTblMisCarritos().getModel();
        model.setColumnIdentifiers(new Object[]{"Código", "# Productos", "Total"});
        model.setRowCount(0);
        List<Carrito> misCarritos = carritoDAO.buscarPorUsuario(usuarioAutenticado);
        for (Carrito c : misCarritos) {
            model.addRow(new Object[]{c.getCodigo(), c.getItems().size(), String.format("$%.2f", c.getTotal())});
        }
    }

    private void buscarCarritoParaEditar() {
        try {
            int codigo = Integer.parseInt(gestionarUsuarioView.getTxtCodigoCarrito().getText());
            Carrito carrito = carritoDAO.leer(codigo);
            if (carrito != null && carrito.getUsuario().getCedula().equals(usuarioAutenticado.getCedula())) {
                this.carritoSeleccionado = carrito;
                actualizarTablaCarritoSeleccionado();
            } else {
                JOptionPane.showMessageDialog(gestionarUsuarioView, "Carrito no encontrado o no le pertenece.", "Error", JOptionPane.ERROR_MESSAGE);
                this.carritoSeleccionado = null;
                ((DefaultTableModel) gestionarUsuarioView.getTblCarrito().getModel()).setRowCount(0);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(gestionarUsuarioView, "El código del carrito debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarProductoDeCarrito() {
        int filaSeleccionada = gestionarUsuarioView.getTblCarrito().getSelectedRow();
        if (filaSeleccionada >= 0 && carritoSeleccionado != null) {
            int codigoProducto = (int) gestionarUsuarioView.getTblCarrito().getValueAt(filaSeleccionada, 1);
            int confirm = JOptionPane.showConfirmDialog(gestionarUsuarioView, "¿Seguro que desea eliminar este producto?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                carritoSeleccionado.eliminarItem(codigoProducto);
                actualizarTablaCarritoSeleccionado();
            }
        }
    }

    private void guardarCambiosCarrito() {
        if (carritoSeleccionado != null) {
            carritoDAO.actualizar(carritoSeleccionado);
            JOptionPane.showMessageDialog(gestionarUsuarioView, "Carrito guardado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            listarCarritosDeUsuario();
        } else {
            JOptionPane.showMessageDialog(gestionarUsuarioView, "No hay ningún carrito cargado para guardar.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void eliminarCarritoCompleto() {
        if (carritoSeleccionado != null) {
            int confirm = JOptionPane.showConfirmDialog(gestionarUsuarioView, "¿Seguro que desea eliminar el carrito completo?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                carritoDAO.eliminar(carritoSeleccionado.getCodigo());
                this.carritoSeleccionado = null;
                ((DefaultTableModel) gestionarUsuarioView.getTblCarrito().getModel()).setRowCount(0);
                gestionarUsuarioView.getTxtCodigoCarrito().setText("");
                listarCarritosDeUsuario();
                JOptionPane.showMessageDialog(gestionarUsuarioView, "Carrito eliminado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(gestionarUsuarioView, "No hay ningún carrito cargado para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void abrirDialogoAgregarProducto() {
        if (carritoSeleccionado == null) {
            JOptionPane.showMessageDialog(gestionarUsuarioView, "Primero debe buscar y cargar un carrito.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        AgregarProductoCarritoView dialogo = new AgregarProductoCarritoView(null);
        final Producto[] productoEncontrado = {null};
        dialogo.getBtnBuscar().addActionListener(e -> {
            try {
                int codigo = Integer.parseInt(dialogo.getTxtCodigo().getText());
                Producto p = productoDAO.buscarPorCodigo(codigo);
                if (p != null) {
                    dialogo.getTxtNombre().setText(p.getNombre());
                    dialogo.getTxtPrecio().setText(String.format("$%.2f", p.getPrecio()));
                    productoEncontrado[0] = p;
                } else {
                    JOptionPane.showMessageDialog(dialogo, "Producto no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                    productoEncontrado[0] = null;
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialogo, "Código inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        dialogo.getBtnAgregar().addActionListener(e -> {
            if (productoEncontrado[0] != null) {
                int cantidad = (int) dialogo.getCbxCantidad().getSelectedItem();
                carritoSeleccionado.agregarItem(productoEncontrado[0], cantidad);
                actualizarTablaCarritoSeleccionado();
                dialogo.dispose();
            } else {
                JOptionPane.showMessageDialog(dialogo, "Debe buscar un producto válido primero.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });
        dialogo.getBtnCancelar().addActionListener(e -> dialogo.dispose());
        dialogo.setVisible(true);
    }

    private void abrirDialogoModificarCantidad() {
        int filaSeleccionada = gestionarUsuarioView.getTblCarrito().getSelectedRow();
        if (filaSeleccionada < 0) return;
        int codigoProducto = (int) gestionarUsuarioView.getTblCarrito().getValueAt(filaSeleccionada, 1);
        ItemCarrito itemAModificar = carritoSeleccionado.getItems().stream()
                .filter(item -> item.getProducto().getCodigo() == codigoProducto)
                .findFirst().orElse(null);
        if (itemAModificar != null) {
            ModificarCantidadProductoCarritoView dialogo = new ModificarCantidadProductoCarritoView(null);
            dialogo.getCbxCantidad().setSelectedItem(itemAModificar.getCantidad());
            dialogo.getBtnGuardar().addActionListener(e -> {
                int nuevaCantidad = (int) dialogo.getCbxCantidad().getSelectedItem();
                itemAModificar.setCantidad(nuevaCantidad);
                actualizarTablaCarritoSeleccionado();
                dialogo.dispose();
            });
            dialogo.getBtnCancelar().addActionListener(e -> dialogo.dispose());
            dialogo.setVisible(true);
        }
    }

    private void actualizarTablaYTotalesCrear() {
        DefaultTableModel model = (DefaultTableModel) crearCarritoView.getTblProductos().getModel();
        model.setColumnIdentifiers(new Object[]{"Nombre", "Código", "Cantidad", "Precio"});
        model.setRowCount(0);
        for (ItemCarrito item : carritoEnCreacion.getItems()) {
            model.addRow(new Object[]{
                    item.getProducto().getNombre(),
                    item.getProducto().getCodigo(),
                    item.getCantidad(),
                    String.format("%.2f", item.getProducto().getPrecio())
            });
        }
        crearCarritoView.getTxtSubtotal().setText(String.format("$%.2f", carritoEnCreacion.getSubtotal()));
        crearCarritoView.getTxtIVA().setText(String.format("$%.2f", carritoEnCreacion.getIva()));
        crearCarritoView.getTxtTotal().setText(String.format("$%.2f", carritoEnCreacion.getTotal()));
    }

    private void actualizarTablaCarritoSeleccionado() {
        if (carritoSeleccionado == null) return;
        DefaultTableModel model = (DefaultTableModel) gestionarUsuarioView.getTblCarrito().getModel();
        model.setColumnIdentifiers(new Object[]{"Nombre", "Código", "Cantidad", "Precio"});
        model.setRowCount(0);
        for (ItemCarrito item : carritoSeleccionado.getItems()) {
            model.addRow(new Object[]{
                    item.getProducto().getNombre(),
                    item.getProducto().getCodigo(),
                    item.getCantidad(),
                    String.format("$%.2f", item.getProducto().getPrecio())
            });
        }
        actualizarEstadoBotonesEdicion();
    }

    private void actualizarEstadoBotonesEdicion() {
        boolean seleccionado = gestionarUsuarioView.getTblCarrito().getSelectedRow() != -1;
        gestionarUsuarioView.getBtnEliminarProducto().setEnabled(seleccionado);
        gestionarUsuarioView.getBtnModificarCantidad().setEnabled(seleccionado);
    }

    private void limpiarDatosProductoCrear() {
        crearCarritoView.getTxtCodigo().setText("");
        crearCarritoView.getTxtNombre().setText("Nombre");
        crearCarritoView.getTxtPrecio().setText("Precio");
        crearCarritoView.getCbxCantidad().setSelectedIndex(0);
        crearCarritoView.getTxtCodigo().requestFocus();
    }

    private void limpiarVistaCrearCarrito(){
        limpiarDatosProductoCrear();
        ((DefaultTableModel) crearCarritoView.getTblProductos().getModel()).setRowCount(0);
        crearCarritoView.getTxtSubtotal().setText("$0.00");
        crearCarritoView.getTxtIVA().setText("$0.00");
        crearCarritoView.getTxtTotal().setText("$0.00");
    }
}