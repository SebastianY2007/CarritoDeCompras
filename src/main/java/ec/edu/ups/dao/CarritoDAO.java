package ec.edu.ups.dao;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.Usuario;
import java.util.List;

/**
 * Interfaz CarritoDAO
 *
 * Esta interfaz define el contrato para las operaciones de persistencia de la
 * entidad Carrito. Abstrae los métodos CRUD (Crear, Leer, Actualizar, Eliminar)
 * y otras operaciones específicas para los carritos de compra.
 *
 * @author Sebastian Yupangui
 * @version 1.0
 * @since 15/07/2025
 */
public interface CarritoDAO {
    /**
     * Persiste un nuevo carrito en el sistema.
     * @param carrito El objeto Carrito a ser creado.
     */
    void crear(Carrito carrito);

    /**
     * Lee un carrito desde la persistencia usando su código.
     * @param codigo El código único del carrito a buscar.
     * @return El objeto Carrito si se encuentra, de lo contrario null.
     */
    Carrito leer(int codigo);

    /**
     * Actualiza los datos de un carrito existente.
     * @param carrito El objeto Carrito con la información actualizada.
     */
    void actualizar(Carrito carrito);

    /**
     * Elimina un carrito de la persistencia por su código.
     * @param codigo El código del carrito a eliminar.
     */
    void eliminar(int codigo);

    /**
     * Obtiene una lista de todos los carritos del sistema.
     * @return Una lista con todos los carritos.
     */
    List<Carrito> obtenerTodos();

    /**
     * Busca y devuelve todos los carritos que pertenecen a un usuario específico.
     * @param usuario El usuario propietario de los carritos.
     * @return Una lista de carritos pertenecientes al usuario.
     */
    List<Carrito> buscarPorUsuario(Usuario usuario);
}
