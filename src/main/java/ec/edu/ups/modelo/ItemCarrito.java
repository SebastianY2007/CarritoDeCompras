package ec.edu.ups.modelo;

import java.io.Serializable;

/**
 * Clase ItemCarrito
 *
 * Esta clase representa un ítem o línea de producto dentro de un carrito de compras.
 * Contiene una referencia al producto y la cantidad seleccionada. Es serializable
 * para poder ser guardada junto con el objeto Carrito.
 *
 * @author Sebastian Yupangui
 * @version 1.0
 * @since 15/07/2025
 */
public class ItemCarrito implements Serializable {

    private static final long serialVersionUID = 1L;
    private Producto producto;
    private int cantidad;

    /**
     * Constructor de ItemCarrito.
     *
     * @param producto El objeto Producto que se añade al carrito.
     * @param cantidad La cantidad de dicho producto.
     */
    public ItemCarrito(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    /**
     * Obtiene el producto de este ítem.
     * @return el objeto Producto.
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * Obtiene la cantidad de este ítem.
     * @return la cantidad del producto.
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad de este ítem.
     * @param cantidad La nueva cantidad del producto.
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Calcula el subtotal para este ítem.
     *
     * Multiplica el precio del producto por la cantidad.
     * @return el subtotal del ítem.
     */
    public double getSubtotal() {
        return producto.getPrecio() * cantidad;
    }
}
