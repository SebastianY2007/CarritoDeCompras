package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.Usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CarritoDAOMemoria implements CarritoDAO {
    private final Map<Integer, Carrito> carritosMap;
    private int proximoCodigo = 1;

    public CarritoDAOMemoria() {
        this.carritosMap = new HashMap<>();
    }

    @Override
    public void crear(Carrito carrito) {
        carrito.setCodigo(proximoCodigo++);
        carritosMap.put(carrito.getCodigo(), carrito);
    }

    @Override
    public Carrito leer(int codigo) {
        return carritosMap.get(codigo);
    }

    @Override
    public void actualizar(Carrito carrito) {
        if (carritosMap.containsKey(carrito.getCodigo())) {
            carritosMap.put(carrito.getCodigo(), carrito);
        }
    }

    @Override
    public void eliminar(int codigo) {
        carritosMap.remove(codigo);
    }

    @Override
    public List<Carrito> obtenerTodos() {
        return new ArrayList<>(carritosMap.values());
    }

    @Override
    public List<Carrito> buscarPorUsuario(Usuario usuario) {
        return carritosMap.values().stream().filter(carrito -> carrito.getUsuario().getUsername().equals(usuario.getUsername())).collect(Collectors.toList());
    }
}