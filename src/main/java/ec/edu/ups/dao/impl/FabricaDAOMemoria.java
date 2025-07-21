package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.*;

/**
 * Clase FabricaDAOMemoria
 *
 * Esta clase es la implementación concreta del patrón Abstract Factory para
 * el tipo de persistencia en memoria. Su responsabilidad es crear e instanciar
 * todas las clases DAO que operan con datos en memoria.
 *
 * @author Sebastian Yupangui
 * @version 1.0
 * @since 15-07-2025
 */
public class FabricaDAOMemoria extends FabricaDAO {

    /**
     * Obtiene la implementación del DAO para Usuario en memoria.
     * @return Una instancia de UsuarioDAOMemoria.
     */
    @Override
    public UsuarioDAO getUsuarioDAO() {
        return new UsuarioDAOMemoria();
    }

    /**
     * Obtiene la implementación del DAO para Producto en memoria.
     * @return Una instancia de ProductoDAOMemoria.
     */
    @Override
    public ProductoDAO getProductoDAO() {
        return new ProductoDAOMemoria();
    }

    /**
     * Obtiene la implementación del DAO para Carrito en memoria.
     * @return Una instancia de CarritoDAOMemoria.
     */
    @Override
    public CarritoDAO getCarritoDAO() {
        return new CarritoDAOMemoria();
    }

    /**
     * Obtiene la implementación del DAO para PreguntaSeguridad en memoria.
     * @return Una instancia de PreguntaSeguridadDAOMemoria.
     */
    @Override
    public PreguntaSeguridadDAO getPreguntaSeguridadDAO() {
        return new PreguntaSeguridadDAOMemoria();
    }
}
