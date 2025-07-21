package ec.edu.ups.dao;

import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.modelo.Rol;
import java.util.List;

/**
 * Interfaz UsuarioDAO
 *
 * Define el contrato para las operaciones de persistencia de la entidad Usuario.
 * Abstrae los métodos para autenticar, crear, leer, actualizar, eliminar y
 * listar usuarios.
 *
 * @author Sebastian Yupangui
 * @version 1.0
 * @since 15/07/2025
 */
public interface UsuarioDAO {

    /**
     * Autentica a un usuario verificando su cédula y contraseña.
     * @param cedula La cédula del usuario que intenta iniciar sesión.
     * @param contrasenia La contraseña proporcionada por el usuario.
     * @return El objeto Usuario si la autenticación es exitosa, de lo contrario null.
     */
    Usuario autenticar(String cedula, String contrasenia);

    /**
     * Persiste un nuevo usuario en el sistema.
     * @param usuario El objeto Usuario a crear.
     */
    void crear(Usuario usuario);

    /**
     * Busca un usuario por su número de cédula.
     * @param cedula La cédula del usuario a buscar.
     * @return El objeto Usuario si se encuentra, de lo contrario null.
     */
    Usuario buscarPorCedula(String cedula);

    /**
     * Elimina un usuario por su número de cédula.
     * @param cedula La cédula del usuario a eliminar.
     */
    void eliminar(String cedula);

    /**
     * Actualiza los datos de un usuario existente.
     * @param usuario El objeto Usuario con la información actualizada.
     */
    void actualizar(Usuario usuario);

    /**
     * Obtiene una lista de todos los usuarios del sistema.
     * @return Una lista con todos los usuarios.
     */
    List<Usuario> listarTodos();

    /**
     * Obtiene una lista de usuarios filtrados por su rol.
     * @param rol El rol por el cual filtrar a los usuarios.
     * @return Una lista de usuarios que pertenecen al rol especificado.
     */
    List<Usuario> listarPorRol(Rol rol);
}
