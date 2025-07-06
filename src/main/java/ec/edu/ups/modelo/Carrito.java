package ec.edu.ups.modelo;

import java.util.ArrayList;
import java.util.List;

public class Carrito {

    private int codigo;
    private Usuario usuario;
    private List<ItemCarrito> items;
    public static final double TASA_IVA = 0.12;

    public Carrito(int codigo, Usuario usuario) {
        this.codigo = codigo;
        this.usuario = usuario;
        this.items = new ArrayList<>();
    }

    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }
    public Usuario getUsuario() { return usuario; }
    public List<ItemCarrito> getItems() { return items; }

    public void agregarItem(Producto producto, int cantidad) {
        for (ItemCarrito item : items) {
            if (item.getProducto().getCodigo() == producto.getCodigo()) {
                item.setCantidad(item.getCantidad() + cantidad);
                return;
            }
        }
        this.items.add(new ItemCarrito(producto, cantidad));
    }

    public void eliminarItem(int productoCodigo) {
        items.removeIf(item -> item.getProducto().getCodigo() == productoCodigo);
    }

    public double getSubtotal() {
        return items.stream().mapToDouble(ItemCarrito::getSubtotal).sum();
    }

    public double getIva() {
        return getSubtotal() * TASA_IVA;
    }

    public double getTotal() {
        return getSubtotal() + getIva();
    }
}