package ec.edu.ups.dao;

import ec.edu.ups.dao.impl.FabricaDAOMemoria;

public abstract class FabricaDAO {

    public static final int MEMORIA = 1;
    public static final int ARCHIVOS_TEXTO = 2;
    public static final int ARCHIVOS_BINARIOS = 3;

    public abstract UsuarioDAO getUsuarioDAO();
    public abstract ProductoDAO getProductoDAO();
    public abstract CarritoDAO getCarritoDAO();
    public abstract PreguntaSeguridadDAO getPreguntaSeguridadDAO();

    public static FabricaDAO getFactory(int tipo, String rutaBase) {
        switch (tipo) {
            case MEMORIA:
                return new FabricaDAOMemoria();
            case ARCHIVOS_TEXTO:
                return new FabricaDAOArchivosTexto(rutaBase);
            case ARCHIVOS_BINARIOS:
                return new FabricaDAOArchivosBinarios(rutaBase);
            default:
                throw new IllegalArgumentException("El tipo de almacenamiento especificado no es válido.");
        }
    }
}
