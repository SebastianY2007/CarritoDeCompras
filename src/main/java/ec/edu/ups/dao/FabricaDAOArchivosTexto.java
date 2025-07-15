package ec.edu.ups.dao;

import java.io.File;

public class FabricaDAOArchivosTexto extends FabricaDAO {
    private String rutaBase;

    public FabricaDAOArchivosTexto(String rutaBase) {
        this.rutaBase = rutaBase;
    }

    @Override
    public UsuarioDAO getUsuarioDAO() {
        return new UsuarioDAOArchivosTexto(rutaBase + File.separator + "usuarios_txt");
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
