package ec.edu.ups.poo.dao;

import ec.edu.ups.poo.modelo.Producto;

import java.util.List;

public interface ProductoDAO {

    void crear(Producto producto);

    Producto buscarPorCodigo(int codigo);

    Producto buscarPorCodigo(String codigo);

    List<Producto> buscarPorNombre(String nombre);

    void actualizar(Producto producto);

    void eliminar(int codigo);

    void eliminar(String codigo);

    List<Producto> listarTodos();

}