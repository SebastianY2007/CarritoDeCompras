package ec.edu.ups.dao;

import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase UsuarioDAOBinario
 *
 * Implementación de la interfaz UsuarioDAO para persistir los datos de usuarios
 * en archivos binarios. Cada objeto Usuario se serializa y guarda en un archivo
 * individual nombrado con su cédula.
 *
 * @author Sebastian Yupangui
 * @version 1.0
 * @since 15/07/2025
 */
public class UsuarioDAOBinario implements UsuarioDAO {

    private String rutaBase;

    /**
     * Constructor de UsuarioDAOBinario.
     * @param rutaBase La ruta a la carpeta donde se guardarán los archivos .dat.
     */
    public UsuarioDAOBinario(String rutaBase) {
        this.rutaBase = rutaBase;
        new File(this.rutaBase).mkdirs();
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
        List<Usuario> filtrados = new ArrayList<>();
        for (Usuario usuario : listarTodos()) {
            if (usuario.getRol() == rol) {
                filtrados.add(usuario);
            }
        }
        return filtrados;
    }
}
