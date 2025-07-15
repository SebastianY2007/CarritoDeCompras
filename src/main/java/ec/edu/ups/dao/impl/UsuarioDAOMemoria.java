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
        crear(admin);

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

    @Override
    public void crear(Usuario usuario) {
        usuariosMap.put(usuario.getCedula(), usuario);
    }

    @Override
    public Usuario buscarPorCedula(String cedula) {
        return usuariosMap.get(cedula);
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
    public void actualizar(Usuario usuario) {
        if (usuariosMap.containsKey(usuario.getCedula())) {
            usuariosMap.put(usuario.getCedula(), usuario);
        }
    }

    @Override
    public void eliminar(String cedula) {
        usuariosMap.remove(cedula);
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
}
