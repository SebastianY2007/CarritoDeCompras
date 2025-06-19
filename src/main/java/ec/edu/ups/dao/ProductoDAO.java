package ec.edu.ups.dao;

import ec.edu.ups.modelo.Producto;

import java.util.List;

public interface ProductoDAO {

    void crear(Producto producto);

    Producto buscarPorCodigo(int codigo);

    List<Producto> buscarPorNombre(String nombre);

    void actualizar(Producto producto);

    boolean eliminar(int codigo);

    List<Producto> listarTodos();

    boolean actualizarNombre(int productoId, String nuevoValorStr);

    boolean actualizarCodigo(int codigoOriginal, int nuevoCodigo);

    boolean actualizarPrecio(int productoId, double nuevoPrecio);
}
