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

/**
 * Clase CarritoController
 *
 * Esta clase actúa como el controlador en el patrón MVC para toda la lógica
 * relacionada con los carritos de compra. Se encarga de comunicar las vistas
 * del carrito con la capa de acceso a datos (DAO), manejando la creación,
 * gestión y visualización de los carritos.
 *
 * @author Sebastian Yupangui
 * @version 1.0
 * @since 15-07-2025
 */
public class CarritoController {

    // Atributos de la clase que conectan con el DAO y las Vistas
    private final CarritoDAO carritoDAO;
    private final ProductoDAO productoDAO;
    private final CrearCarritoView crearCarritoView;
    private final GestionarCarritoUsuarioView gestionarUsuarioView;
    private final GestionarCarritoAdministradorView gestionarAdminView;
    private Usuario usuarioAutenticado;
    private Carrito carritoEnCreacion;
    private Carrito carritoSeleccionado;

    /**
     * Constructor del CarritoController.
     *
     * Este constructor inicializa el controlador inyectando las dependencias
     * necesarias, como los DAOs para carritos y productos, y las vistas
     * con las que interactuará.
     *
     * @param carritoDAO El objeto de acceso a datos para los carritos.
     * @param productoDAO El objeto de acceso a datos para los productos.
     * @param ccv La vista para crear un nuevo carrito.
     * @param gcuv La vista para que un usuario gestione sus carritos.
     * @param gcav La vista para que un administrador gestione todos los carritos.
     */
    public CarritoController(CarritoDAO carritoDAO, ProductoDAO productoDAO, CrearCarritoView ccv, GestionarCarritoUsuarioView gcuv, GestionarCarritoAdministradorView gcav) {
        this.carritoDAO = carritoDAO;
        this.productoDAO = productoDAO;
        this.crearCarritoView = ccv;
        this.gestionarUsuarioView = gcuv;
        this.gestionarAdminView = gcav;
    }

    /**
     * Establece el usuario que ha iniciado sesión.
     *
     * Este método es fundamental para asociar los carritos con el usuario
     * correcto y para aplicar la lógica de permisos.
     *
     * @param usuario El objeto Usuario que ha sido autenticado.
     */
    public void setUsuarioAutenticado(Usuario usuario) {
        this.usuarioAutenticado = usuario;
    }

    /**
     * Inicializa todos los listeners de eventos.
     *
     * Este método centraliza la configuración de todos los ActionListeners
     * para los botones de las diferentes vistas del carrito, conectando las
     * acciones del usuario con los métodos de este controlador.
     */
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

    /**
     * Lista todos los carritos del sistema.
     *
     * Este método es para la vista del administrador. Obtiene todos los carritos
     * desde el DAO y los muestra en la tabla correspondiente.
     */
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

    /**
     * Inicia un nuevo carrito de compras.
     *
     * Crea una nueva instancia de Carrito asociada al usuario autenticado y
     * limpia la vista de creación para prepararla para un nuevo carrito.
     */
    public void iniciarNuevoCarrito() {
        this.carritoEnCreacion = new Carrito(0, usuarioAutenticado);
        limpiarVistaCrearCarrito();
    }

    /**
     * Busca un producto por su código para añadirlo al nuevo carrito.
     *
     * Obtiene el código del producto desde la vista, lo busca usando el
     * ProductoDAO y, si lo encuentra, muestra sus datos en la vista.
     */
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

    /**
     * Agrega un producto al carrito que se está creando.
     *
     * Toma el producto buscado y la cantidad seleccionada, los añade al objeto
     * `carritoEnCreacion` y actualiza la tabla y los totales en la vista.
     */
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

    /**
     * Guarda el nuevo carrito en el sistema de persistencia.
     *
     * Verifica que el carrito no esté vacío y luego lo pasa al CarritoDAO
     * para ser creado, mostrando un mensaje de éxito al final.
     */
    private void guardarNuevoCarrito() {
        if (carritoEnCreacion.getItems().isEmpty()) {
            JOptionPane.showMessageDialog(crearCarritoView, "El carrito está vacío.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        carritoDAO.crear(carritoEnCreacion);
        JOptionPane.showMessageDialog(crearCarritoView, "Carrito creado exitosamente con Código: " + carritoEnCreacion.getCodigo(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
        crearCarritoView.dispose();
    }

    /**
     * Lista los carritos pertenecientes al usuario autenticado.
     *
     * Obtiene desde el DAO solo los carritos del usuario actual y los
     * muestra en la tabla de la vista de gestión de usuario.
     */
    private void listarCarritosDeUsuario() {
        DefaultTableModel model = (DefaultTableModel) gestionarUsuarioView.getTblMisCarritos().getModel();
        model.setColumnIdentifiers(new Object[]{"Código", "# Productos", "Total"});
        model.setRowCount(0);
        List<Carrito> misCarritos = carritoDAO.buscarPorUsuario(usuarioAutenticado);
        for (Carrito c : misCarritos) {
            model.addRow(new Object[]{c.getCodigo(), c.getItems().size(), String.format("$%.2f", c.getTotal())});
        }
    }

    /**
     * Busca y carga un carrito específico para su edición.
     *
     * Obtiene el código del carrito desde la vista, lo busca en el DAO y
     * verifica que pertenezca al usuario actual. Si es así, lo carga en la
     * vista para su edición.
     */
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

    /**
     * Elimina un producto seleccionado del carrito en edición.
     *
     * Obtiene la fila seleccionada de la tabla, identifica el producto y lo
     * elimina del objeto `carritoSeleccionado`, actualizando la vista.
     */
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

    /**
     * Guarda los cambios realizados en un carrito existente.
     *
     * Llama al método `actualizar` del DAO para persistir los cambios
     * (productos añadidos, eliminados o cantidades modificadas) del carrito
     * que se está editando.
     */
    private void guardarCambiosCarrito() {
        if (carritoSeleccionado != null) {
            carritoDAO.actualizar(carritoSeleccionado);
            JOptionPane.showMessageDialog(gestionarUsuarioView, "Carrito guardado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            listarCarritosDeUsuario();
        } else {
            JOptionPane.showMessageDialog(gestionarUsuarioView, "No hay ningún carrito cargado para guardar.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Elimina por completo un carrito seleccionado.
     *
     * Tras confirmación del usuario, llama al método `eliminar` del DAO
     * y limpia la vista de edición.
     */
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

    /**
     * Abre un diálogo para agregar un nuevo producto a un carrito existente.
     *
     * Muestra una ventana modal donde el usuario puede buscar y añadir un
     * nuevo producto al carrito que está actualmente en edición.
     */
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

    /**
     * Abre un diálogo para modificar la cantidad de un producto en el carrito.
     *
     * Muestra una ventana modal que permite al usuario cambiar la cantidad
     * de un ítem ya existente en el carrito en edición.
     */
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

    /**
     * Actualiza la tabla y los totales en la vista de creación de carrito.
     *
     * Recalcula y muestra el subtotal, IVA y total, y refresca la tabla
     * de productos cada vez que se añade un nuevo ítem.
     */
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

    /**
     * Actualiza la tabla de productos del carrito seleccionado en la vista de gestión.
     *
     * Refresca la tabla que muestra los detalles del carrito que el usuario
     * está editando actualmente.
     */
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

    /**
     * Habilita o deshabilita los botones de edición.
     *
     * Comprueba si hay una fila seleccionada en la tabla de edición de
     * carritos para habilitar o deshabilitar los botones de "Eliminar Producto"
     * y "Modificar Cantidad".
     */
    private void actualizarEstadoBotonesEdicion() {
        boolean seleccionado = gestionarUsuarioView.getTblCarrito().getSelectedRow() != -1;
        gestionarUsuarioView.getBtnEliminarProducto().setEnabled(seleccionado);
        gestionarUsuarioView.getBtnModificarCantidad().setEnabled(seleccionado);
    }

    /**
     * Limpia los campos de entrada de producto en la vista de creación.
     *
     * Resetea los campos de texto y el ComboBox de cantidad después de que
     * un producto ha sido agregado al carrito.
     */
    private void limpiarDatosProductoCrear() {
        crearCarritoView.getTxtCodigo().setText("");
        crearCarritoView.getTxtNombre().setText("Nombre");
        crearCarritoView.getTxtPrecio().setText("Precio");
        crearCarritoView.getCbxCantidad().setSelectedIndex(0);
        crearCarritoView.getTxtCodigo().requestFocus();
    }

    /**
     * Limpia y resetea completamente la vista de creación de carrito.
     *
     * Llama a otros métodos de limpieza para dejar la ventana lista para
     * un nuevo carrito desde cero.
     */
    private void limpiarVistaCrearCarrito(){
        limpiarDatosProductoCrear();
        ((DefaultTableModel) crearCarritoView.getTblProductos().getModel()).setRowCount(0);
        crearCarritoView.getTxtSubtotal().setText("$0.00");
        crearCarritoView.getTxtIVA().setText("$0.00");
        crearCarritoView.getTxtTotal().setText("$0.00");
    }
}
