package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase UsuarioDAOArchivoBinario
 *
 * Implementación de la interfaz UsuarioDAO para persistir los datos de usuarios
 * en archivos binarios. Cada objeto Usuario se serializa y guarda en un archivo
 * individual nombrado con su cédula.
 *
 * @author Sebastian Yupangui
 * @version 1.0
 * @since 16/07/2025
 */
public class UsuarioDAOArchivoBinario implements UsuarioDAO {

    private String rutaBase;

    /**
     * Constructor de UsuarioDAOArchivoBinario.
     *
     * Inicializa el DAO, crea el directorio base si no existe y genera un
     * usuario administrador por defecto si el directorio está vacío.
     *
     * @param rutaBase La ruta a la carpeta donde se guardarán los archivos .dat.
     */
    public UsuarioDAOArchivoBinario(String rutaBase) {
        this.rutaBase = rutaBase;
        new File(this.rutaBase).mkdirs();

        File directorio = new File(rutaBase);
        if (directorio.list() == null || directorio.list().length == 0) {
            crearAdminPorDefecto();
        }
    }

    /**
     * Crea un usuario administrador por defecto.
     *
     * Este método asegura que la aplicación siempre tenga al menos un usuario
     * administrador al iniciar con una carpeta de almacenamiento vacía.
     */
    private void crearAdminPorDefecto() {
        Usuario admin = new Usuario(
                "0107271777", "Administrador Sebastian", "Admin.123@", "derlis567y@gmail.com",
                "0995399230", 19, 4, 2007, Rol.ADMINISTRADOR,
                "pregunta.mascota", "Negra", "pregunta.madre", "Isabel", "pregunta.escuela", "Francisco Alvarado"
        );
        crear(admin);
    }

    /**
     * Crea un archivo binario para un nuevo usuario.
     *
     * Serializa y escribe el objeto Usuario completo en un archivo.
     * @param usuario El objeto Usuario a persistir.
     */
    @Override
    public void crear(Usuario usuario) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaBase + File.separator + usuario.getCedula() + ".dat"))) {
            oos.writeObject(usuario);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Busca un usuario leyendo su archivo binario correspondiente.
     * @param cedula La cédula del usuario, que corresponde al nombre del archivo.
     * @return El objeto Usuario deserializado si se encuentra, de lo contrario null.
     */
    @Override
    public Usuario buscarPorCedula(String cedula) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaBase + File.separator + cedula + ".dat"))) {
            return (Usuario) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * Actualiza un usuario sobrescribiendo su archivo binario.
     * @param usuario El objeto Usuario con los datos actualizados.
     */
    @Override
    public void actualizar(Usuario usuario) {
        crear(usuario);
    }

    /**
     * Elimina el archivo binario de un usuario.
     * @param cedula La cédula del usuario a eliminar.
     */
    @Override
    public void eliminar(String cedula) {
        new File(rutaBase + File.separator + cedula + ".dat").delete();
    }

    /**
     * Lista todos los usuarios leyendo todos los archivos .dat del directorio.
     * @return Una lista con todos los usuarios.
     */
    @Override
    public List<Usuario> listarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        File[] archivos = new File(rutaBase).listFiles((dir, name) -> name.endsWith(".dat"));
        if (archivos != null) {
            for (File archivo : archivos) {
                Usuario usuario = buscarPorCedula(archivo.getName().replace(".dat", ""));
                if (usuario != null) {
                    usuarios.add(usuario);
                }
            }
        }
        return usuarios;
    }

    /**
     * Autentica a un usuario.
     * @param cedula La cédula del usuario.
     * @param contrasenia La contraseña a verificar.
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
     * Lista los usuarios que pertenecen a un rol específico.
     * @param rol El rol para filtrar la lista de usuarios.
     * @return Una lista de usuarios filtrada por rol.
     */
    @Override
    public List<Usuario> listarPorRol(Rol rol) {
        return listarTodos().stream()
                .filter(usuario -> usuario.getRol() == rol)
                .collect(Collectors.toList());
    }
}
