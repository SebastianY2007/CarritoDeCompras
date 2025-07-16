package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Clase ProductoDAOMemoria
 *
 * Implementación de la interfaz ProductoDAO para manejar la persistencia
 * de productos en memoria. Utiliza un HashMap para un acceso rápido a los
 * productos a través de su código.
 *
 * @author Sebastian Yupangui
 * @version 1.0
 * @since 15-07-2025
 */
public class ProductoDAOMemoria implements ProductoDAO {

    private Map<Integer, Producto> productosMap;

    /**
     * Constructor de ProductoDAOMemoria.
     *
     * Inicializa el DAO y precarga un conjunto de productos de ejemplo
     * para que la aplicación tenga datos con los que trabajar desde el inicio.
     */
    public ProductoDAOMemoria() {
        this.productosMap = new HashMap<>();
        crear(new Producto(1, "Laptop Gamer", 1200.00));
        crear(new Producto(2, "Mouse Inalámbrico", 25.50));
        crear(new Producto(3, "Teclado Mecánico", 85.00));
        crear(new Producto(4, "Monitor 24 pulgadas", 210.00));
    }

    /**
     * Crea un nuevo producto.
     *
     * Almacena el producto en el mapa de memoria usando su código como clave.
     * @param producto El objeto Producto a crear.
     */
    @Override
    public void crear(Producto producto) {
        productosMap.put(producto.getCodigo(), producto);
    }

    /**
     * Busca un producto por su código.
     *
     * @param codigo El código del producto a buscar.
     * @return El objeto Producto si se encuentra, de lo contrario null.
     */
    @Override
    public Producto buscarPorCodigo(int codigo) {
        return productosMap.get(codigo);
    }

    /**
     * Busca productos cuyo nombre contenga una cadena de texto.
     *
     * La búsqueda no distingue entre mayúsculas y minúsculas.
     * @param nombre El texto a buscar dentro del nombre de los productos.
     * @return Una lista de productos que coinciden con el criterio de búsqueda.
     */
    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        return productosMap.values().stream()
                .filter(p -> p.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Actualiza un producto existente.
     *
     * @param producto El objeto Producto con los datos actualizados.
     */
    @Override
    public void actualizar(Producto producto) {
        if (productosMap.containsKey(producto.getCodigo())) {
            productosMap.put(producto.getCodigo(), producto);
        }
    }

    /**
     * Elimina un producto por su código.
     *
     * @param codigo El código del producto a eliminar.
     * @return true si el producto fue eliminado, false en caso contrario.
     */
    @Override
    public boolean eliminar(int codigo) {
        return productosMap.remove(codigo) != null;
    }

    /**
     * Devuelve una lista con todos los productos.
     *
     * @return Una lista de todos los productos almacenados.
     */
    @Override
    public List<Producto> listarTodos() {
        return new ArrayList<>(productosMap.values());
    }

    /**
     * Actualiza el nombre de un producto específico.
     *
     * @param productoId El ID del producto a actualizar.
     * @param nuevoValorStr El nuevo nombre para el producto.
     * @return true si la actualización fue exitosa, false si no se encontró el producto.
     */
    @Override
    public boolean actualizarNombre(int productoId, String nuevoValorStr) {
        Producto p = buscarPorCodigo(productoId);
        if (p != null) {
            p.setNombre(nuevoValorStr);
            actualizar(p);
            return true;
        }
        return false;
    }

    /**
     * Actualiza el código de un producto.
     *
     * Este método es más complejo ya que el código es la clave del mapa.
     * Requiere eliminar la entrada antigua y crear una nueva.
     *
     * @param codigoOriginal El código actual del producto.
     * @param nuevoCodigo El nuevo código para el producto.
     * @return true si la actualización fue exitosa, false si no se encontró el producto.
     */
    @Override
    public boolean actualizarCodigo(int codigoOriginal, int nuevoCodigo) {
        Producto p = buscarPorCodigo(codigoOriginal);
        if (p != null) {
            productosMap.remove(codigoOriginal);
            p.setCodigo(nuevoCodigo);
            crear(p);
            return true;
        }
        return false;
    }

    /**
     * Actualiza el precio de un producto específico.
     *
     * @param productoId El ID del producto a actualizar.
     * @param nuevoPrecio El nuevo precio para el producto.
     * @return true si la actualización fue exitosa, false si no se encontró el producto.
     */
    @Override
    public boolean actualizarPrecio(int productoId, double nuevoPrecio) {
        Producto p = buscarPorCodigo(productoId);
        if (p != null) {
            p.setPrecio(nuevoPrecio);
            actualizar(p);
            return true;
        }
        return false;
    }
}
