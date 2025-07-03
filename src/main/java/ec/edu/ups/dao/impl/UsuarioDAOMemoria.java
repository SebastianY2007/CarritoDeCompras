package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.UsuarioDAO; // Asegúrate de que apunte a la interfaz corregida
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class UsuarioDAOMemoria implements UsuarioDAO {

    private Map<String, Usuario> usuarios; // Usando Map para eficiencia

    public UsuarioDAOMemoria() {
        usuarios = new HashMap<>();
        // ¡IMPORTANTE! Las llamadas a new Usuario() deben coincidir con el constructor de Usuario.java
        // Usamos el constructor: Usuario(username, contrasena, correoElectronico, nombre, apellido)
        // Y luego asignamos el rol si es diferente del predeterminado.

        // Usuario Administrador
        Usuario admin = new Usuario("admin", "admin", "admin@example.com", "Administrador");
        admin.setRol(Rol.ADMINISTRADOR); // Aseguramos que el rol sea ADMINISTRADOR
        crear(admin); // Usamos el método 'crear' para añadirlo al mapa

        // Otro usuario Administrador (si es necesario para pruebas)
        Usuario aUser = new Usuario("a", "a", "a@example.com", "NombreA");
        aUser.setRol(Rol.ADMINISTRADOR);
        crear(aUser);

        // Usuario Normal
        Usuario normalUser = new Usuario("user", "12345", "user@example.com", "Usuario");
        normalUser.setRol(Rol.USUARIO); // El rol por defecto del constructor es USUARIO, pero lo asignamos explícitamente para claridad
        crear(normalUser);
    }

    @Override
    public List<Usuario> listarPorRol() {
        // Devuelve todos los usuarios con rol USUARIO (como se definió en el constructor de Usuario y en Rol.java)
        List<Usuario> lista = new ArrayList<>();
        for (Usuario usuario : usuarios.values()) {
            if (usuario.getRol() == Rol.USUARIO) {
                lista.add(usuario);
            }
        }
        return lista;
    }

    @Override
    public Usuario autenticar(String username, String contrasenia) {
        Usuario usuario = usuarios.get(username);
        if(usuario != null && usuario.getContrasena().equals(contrasenia)){
            return usuario;
        }
        return null;
    }

    @Override
    public void crear(Usuario usuario) {
        if (usuarios.containsKey(usuario.getUsername())) {
            System.out.println("Error: El usuario " + usuario.getUsername() + " ya existe.");
            return;
        }
        usuarios.put(usuario.getUsername(), usuario);
    }

    @Override
    public Usuario buscarPorUsername(String username) {
        return usuarios.get(username);
    }

    @Override
    public void eliminar(String username) {
        if (!usuarios.containsKey(username)) {
            System.out.println("Error: El usuario " + username + " no existe para eliminar.");
            return;
        }
        usuarios.remove(username);
    }

    @Override
    public void actualizar(Usuario usuario) {
        if (!usuarios.containsKey(usuario.getUsername())) {
            System.out.println("Error: El usuario " + usuario.getUsername() + " no existe para actualizar.");
            return;
        }
        usuarios.put(usuario.getUsername(), usuario);
    }

    @Override
    public List<Usuario> listarTodos() {
        return new ArrayList<>(usuarios.values());
    }

    @Override
    public List<Usuario> listarPorRol(Rol rol) {
        List<Usuario> usuariosEncontrados = new ArrayList<>();
        for (Usuario usuario : usuarios.values()) {
            if (usuario.getRol().equals(rol)) {
                usuariosEncontrados.add(usuario);
            }
        }
        return usuariosEncontrados;
    }
}