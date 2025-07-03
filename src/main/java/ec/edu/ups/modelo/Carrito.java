package ec.edu.ups.modelo;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

public class Carrito {
    private final double IVA = 0.12;
    private int codigo;
    private GregorianCalendar fechaCreacion;
    private List<ItemCarrito> items;

    public Carrito(GregorianCalendar fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
        this.items = new ArrayList<>(); // ¡CORRECCIÓN: Inicializar la lista de ítems!
    }

    public Carrito() {
        this.items = new ArrayList<>(); // Ya estaba bien aquí, pero lo dejo explícito
    }

    public void agregarProducto(Producto producto, int cantidad) {
        // Mejora opcional: Si el producto ya está en el carrito, actualiza la cantidad.
        // Si prefieres añadir un nuevo ItemCarrito incluso si el producto ya existe,
        // puedes eliminar este bucle y simplemente dejar la línea items.add(new ItemCarrito(producto, cantidad));
        for (ItemCarrito item : items) {
            if (item.getProducto().getCodigo() == producto.getCodigo()) {
                item.setCantidad(item.getCantidad() + cantidad);
                return; // Cantidad actualizada, salir del método
            }
        }
        // Si el producto no se encontró en el carrito, añadirlo como un nuevo ítem
        items.add(new ItemCarrito(producto, cantidad));
    }

    public void eliminarProducto(int codigoProducto) {
        Iterator<ItemCarrito> it = items.iterator();
        while (it.hasNext()) {
            if (it.next().getProducto().getCodigo() == codigoProducto) {
                it.remove();
                break; // Se asume que solo quieres eliminar la primera ocurrencia si hay duplicados
            }
        }
    }

    public void vaciarCarrito() {
        items.clear();
    }

    public double calcularTotal() {
        double total = 0;
        for (ItemCarrito item : items) {
            total += item.getProducto().getPrecio() * item.getCantidad();
        }
        return total + calcularIVA(); // El total incluye el subtotal más el IVA
    }

    public List<ItemCarrito> obtenerItems() {
        return items;
    }

    public boolean estaVacio() {
        return items.isEmpty();
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public double calcularSubtotal() {
        double subtotal = 0;
        for (ItemCarrito item : items) {
            subtotal += item.getProducto().getPrecio() * item.getCantidad();
        }
        return subtotal;
    }

    public double calcularIVA() {
        double subtotal = calcularSubtotal();
        return subtotal * IVA;
    }

    public GregorianCalendar getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(GregorianCalendar fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}