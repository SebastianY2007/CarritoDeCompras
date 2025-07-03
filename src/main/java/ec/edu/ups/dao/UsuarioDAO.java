package ec.edu.ups.dao;

import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.modelo.Rol; // ¡IMPORTANTE! Asegúrate de que Rol esté importado si lo usas en el DAO

import java.util.List;

public interface UsuarioDAO {

    // Métodos que ya tienes en UsuarioDAOMemoria y son necesarios para el controlador
    Usuario autenticar(String username, String contrasenia); // Usas 'contrasenia' en DAOMemoria
    void crear(Usuario usuario);
    Usuario buscarPorUsername(String username);
    void eliminar(String username);
    void actualizar(Usuario usuario);
    List<Usuario> listarTodos(); // Este es tu 'findAll'

    // Métodos adicionales que tienes en UsuarioDAOMemoria (sobrecargado o específico)
    List<Usuario> listarPorRol(); // Tu método sin parámetros de rol
    List<Usuario> listarPorRol(Rol rol); // Tu método con parámetro de rol (¡muy útil!)
}