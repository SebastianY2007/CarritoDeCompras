package ec.edu.ups.poo.modelo;

import java.util.ArrayList;
import java.util.List;

public class Carrito {
    private List<ItemCarrito> items;

    public Carrito(){
        this.items = new ArrayList<>();
    }

    public void agregarProducto(ItemCarrito item){
        items.add(item);
    }

    public double calcularTotal(){
        double total = 0;
        for (ItemCarrito item : items){
            total += item.subtotal();
        }
        return total;
    }

    public void vaciarCarrito(){
        items.clear();
    }

    public List<ItemCarrito> getItems() {
        return items;
    }
}
