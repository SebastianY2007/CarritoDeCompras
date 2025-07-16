package ec.edu.ups.dao;

import java.io.File;

/**
 * Clase FabricaDAOArchivosTexto
 *
 * Implementación concreta de FabricaDAO para el almacenamiento en archivos de texto plano.
 * Esta fábrica crea instancias de los DAOs que guardan y leen datos línea por línea.
 *
 * @author Sebastian Yupangui
 * @version 1.0
 * @since 15/07/2025
 */
public class FabricaDAOArchivosTexto extends FabricaDAO {
    private String rutaBase;

    /**
     * Constructor de la fábrica de archivos de texto.
     * @param rutaBase La ruta principal donde se crearán las subcarpetas de datos.
     */
    public FabricaDAOArchivosTexto(String rutaBase) {
        this.rutaBase = rutaBase;
    }

    /**
     * Obtiene la implementación del DAO de Usuario para archivos de texto.
     * @return Una instancia de UsuarioDAOArchivosTexto.
     */
    @Override
    public UsuarioDAO getUsuarioDAO() {
        return new UsuarioDAOArchivosTexto(rutaBase + File.separator + "usuarios_txt");
    }

    /**
     * Obtiene la implementación del DAO de Producto para archivos de texto.
     * @return Una instancia de ProductoDAOArchivosTexto (cuando se implemente).
     */
    @Override
    public ProductoDAO getProductoDAO() {
        return null;
    }

    /**
     * Obtiene la implementación del DAO de Carrito para archivos de texto.
     * @return Una instancia de CarritoDAOArchivosTexto (cuando se implemente).
     */
    @Override
    public CarritoDAO getCarritoDAO() {
        return null;
    }

    /**
     * Obtiene la implementación del DAO de PreguntaSeguridad para archivos de texto.
     * @return Una instancia de PreguntaSeguridadDAOArchivosTexto (cuando se implemente).
     */
    @Override
    public PreguntaSeguridadDAO getPreguntaSeguridadDAO() {
        return null;
    }
}
