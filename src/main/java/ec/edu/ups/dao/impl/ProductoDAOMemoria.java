package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductoDAOMemoria implements ProductoDAO {

    private Map<Integer, Producto> productosMap;

    public ProductoDAOMemoria() {
        this.productosMap = new HashMap<>();
        crear(new Producto(1, "Laptop Gamer", 1200.00));
        crear(new Producto(2, "Mouse Inalámbrico", 25.50));
        crear(new Producto(3, "Teclado Mecánico", 85.00));
        crear(new Producto(4, "Monitor 24 pulgadas", 210.00));
    }

    @Override
    public void crear(Producto producto) {
        productosMap.put(producto.getCodigo(), producto);
    }

    @Override
    public Producto buscarPorCodigo(int codigo) {
        return productosMap.get(codigo);
    }

    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        return productosMap.values().stream()
                .filter(p -> p.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public void actualizar(Producto producto) {
        if (productosMap.containsKey(producto.getCodigo())) {
            productosMap.put(producto.getCodigo(), producto);
        }
    }

    @Override
    public boolean eliminar(int codigo) {
        return productosMap.remove(codigo) != null;
    }

    @Override
    public List<Producto> listarTodos() {
        return new ArrayList<>(productosMap.values());
    }

    @Override
    public boolean actualizarNombre(int productoId, String nuevoValorStr) {
        Producto p = buscarPorCodigo(productoId);
        if (p != null) {
            p.setNombre(nuevoValorStr);
            actualizar(p);
            return true;
        }
        return false;
    }

    @Override
    public boolean actualizarCodigo(int codigoOriginal, int nuevoCodigo) {
        Producto p = buscarPorCodigo(codigoOriginal);
        if (p != null) {
            productosMap.remove(codigoOriginal);
            p.setCodigo(nuevoCodigo);
            crear(p);
            return true;
        }
        return false;
    }

    @Override
    public boolean actualizarPrecio(int productoId, double nuevoPrecio) {
        Producto p = buscarPorCodigo(productoId);
        if (p != null) {
            p.setPrecio(nuevoPrecio);
            actualizar(p);
            return true;
        }
        return false;
    }
}