package ec.edu.ups.dao;

import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOBinario implements UsuarioDAO {

    private String rutaBase;

    public UsuarioDAOBinario(String rutaBase) {
        this.rutaBase = rutaBase;
        new File(this.rutaBase).mkdirs();
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
        List<Usuario> filtrados = new ArrayList<>();
        for (Usuario usuario : listarTodos()) {
            if (usuario.getRol() == rol) {
                filtrados.add(usuario);
            }
        }
        return filtrados;
    }
}
