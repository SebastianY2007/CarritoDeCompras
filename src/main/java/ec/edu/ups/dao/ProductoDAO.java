package ec.edu.ups.dao;

import ec.edu.ups.modelo.Producto;
import java.util.List;

/**
 * Interfaz ProductoDAO
 *
 * Define el contrato para las operaciones de persistencia de la entidad Producto.
 * Incluye métodos CRUD y operaciones de búsqueda y actualización específicas.
 *
 * @author Sebastian Yupangui
 * @version 1.0
 * @since 15/07/2025
 */
public interface ProductoDAO {
    /**
     * Persiste un nuevo producto.
     * @param producto El objeto Producto a crear.
     */
    void crear(Producto producto);

    /**
     * Busca un producto por su código único.
     * @param codigo El código del producto a buscar.
     * @return El objeto Producto si se encuentra, de lo contrario null.
     */
    Producto buscarPorCodigo(int codigo);

    /**
     * Busca productos cuyo nombre contenga una cadena de texto.
     * @param nombre El texto a buscar en el nombre de los productos.
     * @return Una lista de productos que coinciden con la búsqueda.
     */
    List<Producto> buscarPorNombre(String nombre);

    /**
     * Actualiza los datos de un producto existente.
     * @param producto El objeto Producto con la información actualizada.
     */
    void actualizar(Producto producto);

    /**
     * Elimina un producto por su código.
     * @param codigo El código del producto a eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
    boolean eliminar(int codigo);

    /**
     * Obtiene una lista de todos los productos del sistema.
     * @return Una lista con todos los productos.
     */
    List<Producto> listarTodos();

    /**
     * Actualiza únicamente el nombre de un producto.
     * @param productoId El ID del producto a modificar.
     * @param nuevoValorStr El nuevo nombre para el producto.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    boolean actualizarNombre(int productoId, String nuevoValorStr);

    /**
     * Actualiza únicamente el código de un producto.
     * @param codigoOriginal El código actual del producto.
     * @param nuevoCodigo El nuevo código para el producto.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    boolean actualizarCodigo(int codigoOriginal, int nuevoCodigo);

    /**
     * Actualiza únicamente el precio de un producto.
     * @param productoId El ID del producto a modificar.
     * @param nuevoPrecio El nuevo precio para el producto.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    boolean actualizarPrecio(int productoId, double nuevoPrecio);
}
