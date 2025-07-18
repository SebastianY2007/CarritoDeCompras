package ec.edu.ups.modelo;

import java.io.Serializable;

/**
 * Clase Producto
 *
 * Este clase contiene el modelo que representa a un producto en el sistema
 * de carrito de compras. Almacena su código, nombre y precio.
 * Es serializable para permitir su persistencia en archivos binarios.
 *
 * @author Sebastian Yupangui
 * @version 1.0
 * @since 15/07/2025
 */
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;
    private int codigo;
    private String nombre;
    private double precio;

    /**
     * Constructor vacío de Producto.
     * Permite la creación de una instancia de Producto sin inicializar sus atributos.
     */
    public Producto() {
    }

    /**
     * Constructor de Producto con parámetros.
     *
     * @param codigo El código único del producto.
     * @param nombre El nombre del producto.
     * @param precio El precio del producto.
     */
    public Producto(int codigo, String nombre, double precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
    }

    /**
     * Establece el código del producto.
     * @param codigo El nuevo código del producto.
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * Establece el nombre del producto.
     * @param nombre El nuevo nombre del producto.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Establece el precio del producto.
     * @param precio El nuevo precio del producto.
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Obtiene el código del producto.
     * @return el código del producto.
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Obtiene el nombre del producto.
     * @return el nombre del producto.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el precio del producto.
     * @return el precio del producto.
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Devuelve una representación en String del producto.
     * @return una cadena con el nombre y el precio del producto.
     */
    @Override
    public String toString() {
        return nombre + " - $" + precio;
    }
}
