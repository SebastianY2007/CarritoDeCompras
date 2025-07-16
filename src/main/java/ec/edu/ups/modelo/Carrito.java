package ec.edu.ups.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase Carrito
 *
 * Esta clase representa el modelo de un carrito de compras. Contiene una lista
 * de productos (ItemCarrito), el usuario propietario y métodos para calcular
 * los totales. Debe ser serializable para poder guardarse en archivos binarios.
 *
 * @author Sebastian Yupangui
 * @version 1.0
 * @since 15/07/2025
 */
public class Carrito implements Serializable {

    private static final long serialVersionUID = 1L; // Identificador para la serialización
    private int codigo;
    private Usuario usuario;
    private List<ItemCarrito> items;
    public static final double TASA_IVA = 0.12;

    /**
     * Constructor de Carrito.
     *
     * Inicializa un nuevo carrito de compras para un usuario específico.
     * La lista de ítems se crea vacía.
     *
     * @param codigo El código único para el carrito.
     * @param usuario El usuario propietario del carrito.
     */
    public Carrito(int codigo, Usuario usuario) {
        this.codigo = codigo;
        this.usuario = usuario;
        this.items = new ArrayList<>();
    }

    /**
     * Obtiene el código del carrito.
     * @return el código del carrito.
     */
    public int getCodigo() { return codigo; }

    /**
     * Establece el código del carrito.
     * @param codigo El nuevo código para el carrito.
     */
    public void setCodigo(int codigo) { this.codigo = codigo; }

    /**
     * Obtiene el usuario propietario del carrito.
     * @return el objeto Usuario propietario.
     */
    public Usuario getUsuario() { return usuario; }

    /**
     * Obtiene la lista de ítems del carrito.
     * @return una lista de objetos ItemCarrito.
     */
    public List<ItemCarrito> getItems() { return items; }

    /**
     * Agrega un producto al carrito.
     *
     * Si el producto ya existe en el carrito, simplemente actualiza la cantidad.
     * Si es un producto nuevo, lo añade a la lista de ítems.
     *
     * @param producto El producto a agregar.
     * @param cantidad La cantidad del producto a agregar.
     */
    public void agregarItem(Producto producto, int cantidad) {
        for (ItemCarrito item : items) {
            if (item.getProducto().getCodigo() == producto.getCodigo()) {
                item.setCantidad(item.getCantidad() + cantidad);
                return;
            }
        }
        this.items.add(new ItemCarrito(producto, cantidad));
    }

    /**
     * Elimina un ítem del carrito por el código del producto.
     * @param productoCodigo El código del producto a eliminar.
     */
    public void eliminarItem(int productoCodigo) {
        items.removeIf(item -> item.getProducto().getCodigo() == productoCodigo);
    }

    /**
     * Calcula el subtotal del carrito.
     *
     * Suma los subtotales de todos los ítems en el carrito.
     * @return el valor del subtotal.
     */
    public double getSubtotal() {
        return items.stream().mapToDouble(ItemCarrito::getSubtotal).sum();
    }

    /**
     * Calcula el valor del IVA del carrito.
     *
     * Aplica la tasa de IVA configurada al subtotal.
     * @return el valor del IVA.
     */
    public double getIva() {
        return getSubtotal() * TASA_IVA;
    }

    /**
     * Calcula el total a pagar del carrito.
     *
     * Suma el subtotal y el IVA.
     * @return el valor total del carrito.
     */
    public double getTotal() {
        return getSubtotal() + getIva();
    }
}
