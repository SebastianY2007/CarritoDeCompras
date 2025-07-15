package ec.edu.ups.dao;

import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.modelo.Rol;

import java.util.List;

public interface UsuarioDAO {

    Usuario autenticar(String cedula, String contrasenia);

    void crear(Usuario usuario);

    Usuario buscarPorCedula(String cedula);

    void eliminar(String cedula);

    void actualizar(Usuario usuario);

    List<Usuario> listarTodos();

    List<Usuario> listarPorRol(Rol rol);

}
