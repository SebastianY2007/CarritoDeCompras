package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProductoDAOMemoria implements ProductoDAO {

    private List<Producto> productos;

    public ProductoDAOMemoria() {
        productos = new ArrayList<Producto>();
    }

    @Override
    public void crear(Producto producto) {
        productos.add(producto);
    }

    @Override
    public Producto buscarPorCodigo(int codigo) {
        for (Producto producto : productos) {
            if (producto.getCodigo() == codigo) {
                return producto;
            }
        }
        return null;
    }

    @Override
    public boolean actualizarCodigo(int codigoOriginal, int nuevoCodigo) {

        for (Producto p : productos) {
            if (p.getCodigo() == nuevoCodigo) {
                if (nuevoCodigo != codigoOriginal) {
                    return false;
                }
            }
        }

        for (Producto producto : productos) {
            if (producto.getCodigo() == codigoOriginal) {
                producto.setCodigo(nuevoCodigo);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> productosEncontrados = new ArrayList<>();
        for (Producto producto : productos) {
            if (producto.getNombre().startsWith(nombre)) {
                productosEncontrados.add(producto);
            }
        }
        return productosEncontrados;
    }

    @Override
    public void actualizar(Producto producto) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo() == producto.getCodigo()) {
                productos.set(i, producto);
                return;
            }
        }
    }

    @Override
    public boolean actualizarNombre(int id, String nuevoNombre) {
        for (Producto producto : productos) {
            if (producto.getCodigo() == id) {
                producto.setNombre(nuevoNombre);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean actualizarPrecio(int id, double nuevoPrecio) {
        for (Producto producto : productos) {
            if (producto.getCodigo() == id) {
                producto.setPrecio(nuevoPrecio);
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean eliminar(int codigo) {
        Iterator<Producto> iterator = productos.iterator();
        while (iterator.hasNext()) {
            Producto producto = iterator.next();
            if (producto.getCodigo() == codigo) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Producto> listarTodos() {
        return new ArrayList<>(productos);
    }
}