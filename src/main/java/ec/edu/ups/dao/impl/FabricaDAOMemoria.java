package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.*;

public class FabricaDAOMemoria extends FabricaDAO {

    @Override
    public UsuarioDAO getUsuarioDAO() {
        return new UsuarioDAOMemoria();
    }

    @Override
    public ProductoDAO getProductoDAO() {
        return new ProductoDAOMemoria();
    }

    @Override
    public CarritoDAO getCarritoDAO() {
        return new CarritoDAOMemoria();
    }

    @Override
    public PreguntaSeguridadDAO getPreguntaSeguridadDAO() {
        return new PreguntaSeguridadDAOMemoria();
    }
}
