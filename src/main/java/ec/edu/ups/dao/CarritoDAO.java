package ec.edu.ups.dao;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.Usuario;
import java.util.List;

public interface CarritoDAO {
    void crear(Carrito carrito);
    Carrito leer(int codigo);
    void actualizar(Carrito carrito);
    void eliminar(int codigo);
    List<Carrito> obtenerTodos();
    List<Carrito> buscarPorUsuario(Usuario usuario);
}