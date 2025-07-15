package ec.edu.ups.dao;

import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOArchivosTexto implements UsuarioDAO {

    private String rutaBase;

    public UsuarioDAOArchivosTexto(String rutaBase) {
        this.rutaBase = rutaBase;
        new File(this.rutaBase).mkdirs();
    }

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

    @Override
    public void actualizar(Usuario usuario) {
        crear(usuario);
    }

    @Override
    public void eliminar(String cedula) {
        new File(rutaBase + File.separator + cedula + ".txt").delete();
    }

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
