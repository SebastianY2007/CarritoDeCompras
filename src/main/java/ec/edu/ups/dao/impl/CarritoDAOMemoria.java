package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.Usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Clase CarritoDAOMemoria
 *
 * Esta clase implementa la interfaz CarritoDAO y proporciona una estrategia
 * de persistencia de datos en memoria para la entidad Carrito. Utiliza un
 * HashMap para almacenar los carritos, usando su código como clave.
 *
 * @author Sebastian Yupangui
 * @version 1.0
 * @since 15-07-2025
 */
public class CarritoDAOMemoria implements CarritoDAO {
    private final Map<Integer, Carrito> carritosMap;
    private int proximoCodigo = 1;

    /**
     * Constructor CarritoDAOMemoria.
     *
     * Este constructor inicializa el mapa en memoria que almacenará los carritos.
     */
    public CarritoDAOMemoria() {
        this.carritosMap = new HashMap<>();
    }

    /**
     * Crea un nuevo carrito.
     *
     * Asigna un código único y secuencial al carrito y lo almacena en el mapa.
     * @param carrito El objeto Carrito a ser creado.
     */
    @Override
    public void crear(Carrito carrito) {
        carrito.setCodigo(proximoCodigo++);
        carritosMap.put(carrito.getCodigo(), carrito);
    }

    /**
     * Lee un carrito por su código.
     *
     * @param codigo El código del carrito a buscar.
     * @return El objeto Carrito si se encuentra, de lo contrario null.
     */
    @Override
    public Carrito leer(int codigo) {
        return carritosMap.get(codigo);
    }

    /**
     * Actualiza un carrito existente.
     *
     * Si el carrito ya existe en el mapa (verificado por su código),
     * se reemplaza con la nueva versión del objeto.
     * @param carrito El objeto Carrito con los datos actualizados.
     */
    @Override
    public void actualizar(Carrito carrito) {
        if (carritosMap.containsKey(carrito.getCodigo())) {
            carritosMap.put(carrito.getCodigo(), carrito);
        }
    }

    /**
     * Elimina un carrito por su código.
     *
     * @param codigo El código del carrito a eliminar.
     */
    @Override
    public void eliminar(int codigo) {
        carritosMap.remove(codigo);
    }

    /**
     * Obtiene una lista de todos los carritos.
     *
     * @return Una lista con todos los carritos almacenados en memoria.
     */
    @Override
    public List<Carrito> obtenerTodos() {
        return new ArrayList<>(carritosMap.values());
    }

    /**
     * Busca todos los carritos pertenecientes a un usuario específico.
     *
     * @param usuario El usuario propietario de los carritos a buscar.
     * @return Una lista de carritos que pertenecen al usuario especificado.
     */
    @Override
    public List<Carrito> buscarPorUsuario(Usuario usuario) {
        return carritosMap.values().stream()
                .filter(carrito -> carrito.getUsuario().getCedula().equals(usuario.getCedula()))
                .collect(Collectors.toList());
    }
}
