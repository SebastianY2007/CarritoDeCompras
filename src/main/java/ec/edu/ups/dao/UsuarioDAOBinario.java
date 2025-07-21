package ec.edu.ups.dao;

import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UsuarioDAOBinario implements UsuarioDAO {

    private String rutaBase;

    public UsuarioDAOBinario(String rutaBase) {
        this.rutaBase = rutaBase;
        new File(this.rutaBase).mkdirs();

        // NUEVA LÓGICA: Si la carpeta de usuarios está vacía, crear un admin por defecto.
        File directorio = new File(rutaBase);
        if (directorio.list() == null || directorio.list().length == 0) {
            crearAdminPorDefecto();
        }
    }

    /**
     * NUEVO MÉTODO: Crea un usuario administrador si no existe ninguno.
     */
    private void crearAdminPorDefecto() {
        Usuario admin = new Usuario(
                "0107271777", "Administrador Sebastian", "admin.123@", "derlis567y@gmail.com",
                "0995399230", 19, 4, 2007, Rol.ADMINISTRADOR,
                "pregunta.mascota", "boby", "pregunta.madre", "maria", "pregunta.escuela", "salesianas"
        );
        crear(admin);
    }

    @Override
    public void crear(Usuario usuario) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaBase + File.separator + usuario.getCedula() + ".dat"))) {
            oos.writeObject(usuario);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Usuario buscarPorCedula(String cedula) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaBase + File.separator + cedula + ".dat"))) {
            return (Usuario) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    @Override
    public void actualizar(Usuario usuario) {
        crear(usuario);
    }

    @Override
    public void eliminar(String cedula) {
        new File(rutaBase + File.separator + cedula + ".dat").delete();
    }

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

    @Override
    public Usuario autenticar(String cedula, String contrasenia) {
        Usuario usuario = buscarPorCedula(cedula);
        if (usuario != null && usuario.getContrasena().equals(contrasenia)) {
            return usuario;
        }
        return null;
    }

    @Override
    public List<Usuario> listarPorRol(Rol rol) {
        return listarTodos().stream()
                .filter(usuario -> usuario.getRol() == rol)
                .collect(Collectors.toList());
    }
}
