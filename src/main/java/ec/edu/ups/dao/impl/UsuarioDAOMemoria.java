package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UsuarioDAOMemoria implements UsuarioDAO {

    private final Map<String, Usuario> usuariosMap;

    public UsuarioDAOMemoria() {
        this.usuariosMap = new HashMap<>();

        Usuario a = new Usuario("a", "a", "a@example.com", "Administrador");
        a.setRol(Rol.ADMINISTRADOR);
        crear(a);

        Usuario admin = new Usuario("admin", "admin", "admin@example.com", "Administrador");
        admin.setRol(Rol.ADMINISTRADOR);
        crear(admin);

        Usuario usuario1 = new Usuario("usuario1", "user123", "usuario1@example.com", "Juan Pérez");
        usuario1.setRol(Rol.USUARIO);
        crear(usuario1);

        Usuario usuario2 = new Usuario("z", "z", "ana.gomez@example.com", "Ana Gómez");
        usuario2.setRol(Rol.USUARIO);
        crear(usuario2);
    }

    @Override
    public void crear(Usuario usuario) {
        usuariosMap.put(usuario.getUsername(), usuario);
    }

    @Override
    public Usuario buscarPorUsername(String username) {
        return usuariosMap.get(username);
    }

    @Override
    public Usuario autenticar(String username, String contrasenia) {
        Usuario usuario = buscarPorUsername(username);
        if (usuario != null && usuario.getContrasena().equals(contrasenia)) {
            return usuario;
        }
        return null;
    }

    @Override
    public void actualizar(Usuario usuario) {
        if (usuariosMap.containsKey(usuario.getUsername())) {
            usuariosMap.put(usuario.getUsername(), usuario);
        }
    }

    @Override
    public void eliminar(String username) {
        usuariosMap.remove(username);
    }

    @Override
    public List<Usuario> listarTodos() {
        return new ArrayList<>(usuariosMap.values());
    }

    @Override
    public List<Usuario> listarPorRol(Rol rol) {
        return usuariosMap.values().stream()
                .filter(usuario -> usuario.getRol() == rol)
                .collect(Collectors.toList());
    }

    @Override
    public List<Usuario> listarPorRol() {
        return listarTodos();
    }
}