package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.*;

import java.io.File;

/**
 * Clase FabricaDAOArchivoBinario
 *
 * Implementación concreta de FabricaDAO para el almacenamiento en archivos binarios.
 * Esta fábrica crea instancias de los DAOs que guardan y leen objetos serializados.
 *
 * @author Sebastian Yupangui
 * @version 1.0
 * @since 15/07/2025
 */
public class FabricaDAOArchivoBinario extends FabricaDAO {
    private String rutaBase;

    /**
     * Constructor de la fábrica de archivos binarios.
     * @param rutaBase La ruta principal donde se crearán las subcarpetas de datos.
     */
    public FabricaDAOArchivoBinario(String rutaBase) {
        this.rutaBase = rutaBase;
    }

    /**
     * Obtiene la implementación del DAO de Usuario para archivos binarios.
     * @return Una instancia de UsuarioDAOArchivoBinario.
     */
    @Override
    public UsuarioDAO getUsuarioDAO() {
        return new UsuarioDAOArchivoBinario(rutaBase + File.separator + "usuarios_bin");
    }

    /**
     * Obtiene la implementación del DAO de Producto para archivos binarios.
     * @return Una instancia de ProductoDAOArchivoBinario.
     */
    @Override
    public ProductoDAO getProductoDAO() {
        return new ProductoDAOArchivoBinario(rutaBase + File.separator + "productos_bin");
    }

    /**
     * Obtiene la implementación del DAO de Carrito para archivos binarios.
     * @return Una instancia de CarritoDAOArchivoBinario.
     */
    @Override
    public CarritoDAO getCarritoDAO() {
        return new CarritoDAOArchivoBinario(rutaBase + File.separator + "carritos_bin");
    }

    /**
     * Obtiene la implementación del DAO de PreguntaSeguridad para archivos binarios.
     * @return Una instancia de PreguntaSeguridadDAOArchivoBinario.
     */
    @Override
    public PreguntaSeguridadDAO getPreguntaSeguridadDAO() {
        return new PreguntaSeguridadDAOArchivoBinario(rutaBase + File.separator + "preguntas_bin");
    }
}
