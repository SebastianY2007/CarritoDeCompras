package ec.edu.ups.dao;

import java.io.File;

public class FabricaDAOArchivosBinarios extends FabricaDAO {
    private String rutaBase;

    public FabricaDAOArchivosBinarios(String rutaBase) {
        this.rutaBase = rutaBase;
    }

    @Override
    public UsuarioDAO getUsuarioDAO() {
        return new UsuarioDAOBinario(rutaBase + File.separator + "usuarios_bin");
    }

    @Override
    public ProductoDAO getProductoDAO() {
        return null;
    }

    @Override
    public CarritoDAO getCarritoDAO() {
        return null;
    }

    @Override
    public PreguntaSeguridadDAO getPreguntaSeguridadDAO() {
        return null;
    }
}