package ec.edu.ups.dao;

import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase UsuarioDAOArchivosTexto
 *
 * Implementación de la interfaz UsuarioDAO para persistir los datos de usuarios
 * en archivos de texto plano. Cada usuario se guarda en un archivo individual
 * nombrado con su cédula.
 *
 * @author Sebastian Yupangui
 * @version 1.0
 * @since 15/07/2025
 */
public class UsuarioDAOArchivosTexto implements UsuarioDAO {

    private String rutaBase;

    /**
     * Constructor de UsuarioDAOArchivosTexto.
     * @param rutaBase La ruta a la carpeta donde se guardarán los archivos de usuario.
     */
    public UsuarioDAOArchivosTexto(String rutaBase) {
        this.rutaBase = rutaBase;
        new File(this.rutaBase).mkdirs();
    }

    /**
     * Crea un archivo de texto para un nuevo usuario.
     *
     * Escribe cada atributo del usuario en una nueva línea dentro del archivo.
     * @param usuario El objeto Usuario a persistir.
     */
    @Override
    public void crear(Usuario usuario) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(rutaBase + File.separator + usuario.getCedula() + ".txt"))) {
            writer.println(usuario.getCedula());
            writer.println(usuario.getNombre());
            writer.println(usuario.getContrasena());
            writer.println(usuario.getCorreoElectronico());
            writer.println(usuario.getTelefono());
            writer.println(usuario.getDiaNacimiento());
            writer.println(usuario.getMesNacimiento());
            writer.println(usuario.getAnioNacimiento());
            writer.println(usuario.getRol().name());
            writer.println(usuario.getPreguntaSeguridad1());
            writer.println(usuario.getRespuestaSeguridad1());
            writer.println(usuario.getPreguntaSeguridad2());
            writer.println(usuario.getRespuestaSeguridad2());
            writer.println(usuario.getPreguntaSeguridad3());
            writer.println(usuario.getRespuestaSeguridad3());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Busca un usuario leyendo su archivo de texto correspondiente.
     * @param cedula La cédula del usuario, que corresponde al nombre del archivo.
     * @return El objeto Usuario reconstruido si se encuentra, de lo contrario null.
     */
    @Override
    public Usuario buscarPorCedula(String cedula) {
        try (BufferedReader reader = new BufferedReader(new FileReader(rutaBase + File.separator + cedula + ".txt"))) {
            return new Usuario(
                    reader.readLine(),
                    reader.readLine(),
                    reader.readLine(),
                    reader.readLine(),
                    reader.readLine(),
                    Integer.parseInt(reader.readLine()),
                    Integer.parseInt(reader.readLine()),
                    Integer.parseInt(reader.readLine()),
                    Rol.valueOf(reader.readLine()),
                    reader.readLine(),
                    reader.readLine(),
                    reader.readLine(),
                    reader.readLine(),
                    reader.readLine(),
                    reader.readLine()
            );
        } catch (IOException | NumberFormatException e) {
            return null;
        }
    }

    /**
     * Actualiza un usuario sobrescribiendo su archivo de texto.
     * @param usuario El objeto Usuario con los datos actualizados.
     */
    @Override
    public void actualizar(Usuario usuario) {
        crear(usuario);
    }

    /**
     * Elimina el archivo de texto de un usuario.
     * @param cedula La cédula del usuario a eliminar.
     */
    @Override
    public void eliminar(String cedula) {
        new File(rutaBase + File.separator + cedula + ".txt").delete();
    }

    /**
     * Lista todos los usuarios leyendo todos los archivos .txt del directorio.
     * @return Una lista con todos los usuarios.
     */
    @Override
    public List<Usuario> listarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        File[] archivos = new File(rutaBase).listFiles((dir, name) -> name.endsWith(".txt"));
        if (archivos != null) {
            for (File archivo : archivos) {
                Usuario usuario = buscarPorCedula(archivo.getName().replace(".txt", ""));
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
