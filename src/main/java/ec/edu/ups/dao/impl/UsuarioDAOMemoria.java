package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Clase UsuarioDAOMemoria
 *
 * Implementación de la interfaz UsuarioDAO que gestiona la persistencia de
 * usuarios en memoria. Utiliza un HashMap donde la clave es la cédula del
 * usuario, garantizando un acceso rápido y eficiente.
 *
 * @author Sebastian Yupangui
 * @version 1.0
 * @since 15-07-2025
 */
public class UsuarioDAOMemoria implements UsuarioDAO {

    private final Map<String, Usuario> usuariosMap;

    /**
     * Constructor de UsuarioDAOMemoria.
     *
     * Inicializa el DAO y precarga un conjunto de usuarios de ejemplo,
     * incluyendo un administrador y varios usuarios estándar, para facilitar
     * las pruebas y el uso inicial de la aplicación.
     */
    public UsuarioDAOMemoria() {
        this.usuariosMap = new HashMap<>();

        Usuario admin = new Usuario(
                "0107271777", "Administrador Sebastian", "admin.123@", "derlis567y@gmail.com",
                "0995399230", 19, 4, 2007, Rol.ADMINISTRADOR,
                "p1", "r1", "p2", "r2", "p3", "r3"
        );
        crear(admin);

        Usuario admin1 = new Usuario(
                "0101010101", "Administrador Principal", "admin", "admin@example.com",
                "0999999999", 1, 1, 1990, Rol.ADMINISTRADOR,
                "p1", "r1", "p2", "r2", "p3", "r3"
        );
        // CORRECCIÓN: Se debe crear el objeto correcto.
        crear(admin1);

        Usuario usuario1 = new Usuario(
                "0102030405", "Juan Perez", "user123", "juan.perez@example.com",
                "0988888888", 10, 5, 1995, Rol.USUARIO,
                "p1", "r1", "p2", "r2", "p3", "r3"
        );
        crear(usuario1);

        Usuario usuario2 = new Usuario(
                "0304050607", "Ana Gomez", "user456", "ana.gomez@example.com",
                "0977777777", 20, 8, 2000, Rol.USUARIO,
                "p1", "r1", "p2", "r2", "p3", "r3"
        );
        crear(usuario2);
    }

    /**
     * Crea un nuevo usuario.
     *
     * Almacena el usuario en el mapa de memoria usando su cédula como clave.
     * @param usuario El objeto Usuario a crear.
     */
    @Override
    public void crear(Usuario usuario) {
        usuariosMap.put(usuario.getCedula(), usuario);
    }

    /**
     * Busca un usuario por su cédula.
     *
     * @param cedula La cédula del usuario a buscar.
     * @return El objeto Usuario si se encuentra, de lo contrario null.
     */
    @Override
    public Usuario buscarPorCedula(String cedula) {
        return usuariosMap.get(cedula);
    }

    /**
     * Autentica a un usuario por su cédula y contraseña.
     *
     * @param cedula La cédula del usuario.
     * @param contrasenia La contraseña del usuario.
     * @return El objeto Usuario si las credenciales son correctas, de lo contrario null.
     */
    @Override
    public Usuario autenticar(String cedula, String contrasenia) {
        Usuario usuario = buscarPorCedula(cedula);
        if (usuario != null && usuario.getContrasena().equals(contrasenia)) {
            return usuario;
        }
        return null;
    }

    /**
     * Actualiza un usuario existente.
     *
     * @param usuario El objeto Usuario con los datos actualizados.
     */
    @Override
    public void actualizar(Usuario usuario) {
        if (usuariosMap.containsKey(usuario.getCedula())) {
            usuariosMap.put(usuario.getCedula(), usuario);
        }
    }

    /**
     * Elimina un usuario por su cédula.
     *
     * @param cedula La cédula del usuario a eliminar.
     */
    @Override
    public void eliminar(String cedula) {
        usuariosMap.remove(cedula);
    }

    /**
     * Devuelve una lista con todos los usuarios.
     *
     * @return Una lista de todos los usuarios almacenados.
     */
    @Override
    public List<Usuario> listarTodos() {
        return new ArrayList<>(usuariosMap.values());
    }

    /**
     * Devuelve una lista de usuarios filtrados por un rol específico.
     *
     * @param rol El rol por el cual filtrar a los usuarios.
     * @return Una lista de usuarios que pertenecen al rol especificado.
     */
    @Override
    public List<Usuario> listarPorRol(Rol rol) {
        return usuariosMap.values().stream()
                .filter(usuario -> usuario.getRol() == rol)
                .collect(Collectors.toList());
    }
}
