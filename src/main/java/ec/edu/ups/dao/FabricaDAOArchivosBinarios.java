package ec.edu.ups.dao;

import java.io.File;

/**
 * Clase FabricaDAOArchivosBinarios
 *
 * Implementación concreta de FabricaDAO para el almacenamiento en archivos binarios.
 * Esta fábrica crea instancias de los DAOs que guardan y leen objetos serializados.
 *
 * @author Sebastian Yupangui
 * @version 1.0
 * @since 15/07/2025
 */
public class FabricaDAOArchivosBinarios extends FabricaDAO {
    private String rutaBase;

    /**
     * Constructor de la fábrica de archivos binarios.
     * @param rutaBase La ruta principal donde se crearán las subcarpetas de datos.
     */
    public FabricaDAOArchivosBinarios(String rutaBase) {
        this.rutaBase = rutaBase;
    }

    /**
     * Obtiene la implementación del DAO de Usuario para archivos binarios.
     * @return Una instancia de UsuarioDAOBinario.
     */
    @Override
    public UsuarioDAO getUsuarioDAO() {
        return new UsuarioDAOBinario(rutaBase + File.separator + "usuarios_bin");
    }

    /**
     * Obtiene la implementación del DAO de Producto para archivos binarios.
     * @return Una instancia de ProductoDAOBinario (cuando se implemente).
     */
    @Override
    public ProductoDAO getProductoDAO() {
        return null;
    }

    /**
     * Obtiene la implementación del DAO de Carrito para archivos binarios.
     * @return Una instancia de CarritoDAOBinario (cuando se implemente).
     */
    @Override
    public CarritoDAO getCarritoDAO() {
        return null;
    }

    /**
     * Obtiene la implementación del DAO de PreguntaSeguridad para archivos binarios.
     * @return Una instancia de PreguntaSeguridadDAOBinario (cuando se implemente).
     */
    @Override
    public PreguntaSeguridadDAO getPreguntaSeguridadDAO() {
        return null;
    }
}
