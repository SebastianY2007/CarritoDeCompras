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
        // Es CRÍTICO que, si el código (ID) es una clave primaria y se usa en una base de datos,
        // este tipo de actualización sea manejada con EXTREMA PRECAUCIÓN o evitada.
        // En una base de datos real, cambiar una clave primaria puede romper la integridad referencial.
        // Aquí, en una implementación en memoria, es más simple, pero sigue siendo un cambio de ID.

        // Primero, verifica si el nuevo código ya existe para evitar duplicados
        for (Producto p : productos) {
            if (p.getCodigo() == nuevoCodigo) {
                // Si el nuevo código ya existe y no es el mismo que el original, no se puede actualizar
                if (nuevoCodigo != codigoOriginal) {
                    return false; // El nuevo código ya está en uso
                }
            }
        }

        // Busca el producto por su código original y actualiza su código al nuevo
        for (Producto producto : productos) {
            if (producto.getCodigo() == codigoOriginal) {
                producto.setCodigo(nuevoCodigo); // Asumiendo que tienes un setter para 'codigo' en tu clase Producto
                return true; // Código actualizado exitosamente
            }
        }
        return false; // Producto con el código original no encontrado
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
                // Es buena práctica salir del bucle una vez que el producto es encontrado y actualizado
                return;
            }
        }
    }

    @Override
    public boolean actualizarNombre(int id, String nuevoNombre) {
        for (Producto producto : productos) {
            if (producto.getCodigo() == id) {
                producto.setNombre(nuevoNombre);
                return true; // Nombre actualizado exitosamente
            }
        }
        return false; // Producto no encontrado
    }

    // >>>>>>>>>>> SOLUCIÓN AL ERROR: Implementar el método actualizarPrecio <<<<<<<<<<<
    @Override
    public boolean actualizarPrecio(int id, double nuevoPrecio) {
        for (Producto producto : productos) {
            if (producto.getCodigo() == id) {
                producto.setPrecio(nuevoPrecio);
                return true; // Precio actualizado exitosamente
            }
        }
        return false; // Producto no encontrado
    }


    @Override
    public boolean eliminar(int codigo) {
        Iterator<Producto> iterator = productos.iterator();
        while (iterator.hasNext()) {
            Producto producto = iterator.next();
            if (producto.getCodigo() == codigo) {
                iterator.remove();
                return true; // Retorna true si se encontró y eliminó el producto
            }
        }
        return false; // Retorna false si no se encontró el producto para eliminar
    }

    @Override
    public List<Producto> listarTodos() {
        // Es buena práctica devolver una copia para evitar modificaciones externas directas a la lista interna
        return new ArrayList<>(productos);
    }
}