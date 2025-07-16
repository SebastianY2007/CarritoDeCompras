package ec.edu.ups.dao;

import ec.edu.ups.dao.impl.FabricaDAOMemoria;

/**
 * Clase Abstracta FabricaDAO
 *
 * Esta clase implementa el patrón de diseño Abstract Factory. Su propósito es
 * proporcionar un punto de acceso único para crear familias de objetos DAO
 * relacionados (UsuarioDAO, ProductoDAO, etc.) sin especificar sus clases concretas.
 *
 * @author Sebastian Yupangui
 * @version 1.0
 * @since 15/07/2025
 */
public abstract class FabricaDAO {

    /** Constante para identificar el tipo de almacenamiento en memoria. */
    public static final int MEMORIA = 1;
    /** Constante para identificar el tipo de almacenamiento en archivos de texto. */
    public static final int ARCHIVOS_TEXTO = 2;
    /** Constante para identificar el tipo de almacenamiento en archivos binarios. */
    public static final int ARCHIVOS_BINARIOS = 3;

    /**
     * Obtiene la implementación del DAO para la entidad Usuario.
     * @return una instancia de un objeto que implementa UsuarioDAO.
     */
    public abstract UsuarioDAO getUsuarioDAO();

    /**
     * Obtiene la implementación del DAO para la entidad Producto.
     * @return una instancia de un objeto que implementa ProductoDAO.
     */
    public abstract ProductoDAO getProductoDAO();

    /**
     * Obtiene la implementación del DAO para la entidad Carrito.
     * @return una instancia de un objeto que implementa CarritoDAO.
     */
    public abstract CarritoDAO getCarritoDAO();

    /**
     * Obtiene la implementación del DAO para la entidad PreguntaSeguridad.
     * @return una instancia de un objeto que implementa PreguntaSeguridadDAO.
     */
    public abstract PreguntaSeguridadDAO getPreguntaSeguridadDAO();

    /**
     * Método estático de fábrica para obtener la implementación concreta de la fábrica.
     *
     * Basado en el tipo de almacenamiento seleccionado, este método devuelve la
     * fábrica correspondiente que a su vez creará el conjunto de DAOs apropiado.
     *
     * @param tipo El tipo de almacenamiento (MEMORIA, ARCHIVOS_TEXTO, ARCHIVOS_BINARIOS).
     * @param rutaBase La ruta a la carpeta de almacenamiento (usada solo para archivos).
     * @return Una instancia de una subclase de FabricaDAO.
     */
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
