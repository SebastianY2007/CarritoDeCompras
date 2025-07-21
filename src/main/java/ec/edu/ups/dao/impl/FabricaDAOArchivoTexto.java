package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.*;
import java.io.File;

/**
 * Clase FabricaDAOArchivoTexto
 *
 * Implementación concreta de FabricaDAO para el almacenamiento en archivos de texto plano.
 * Esta fábrica crea instancias de los DAOs que guardan y leen datos línea por línea.
 *
 * @author Sebastian Yupangui
 * @version 1.1
 * @since 16/07/2025
 */
public class FabricaDAOArchivoTexto extends FabricaDAO {
    private String rutaBase;

    public FabricaDAOArchivoTexto(String rutaBase) {
        this.rutaBase = rutaBase;
    }

    @Override
    public UsuarioDAO getUsuarioDAO() {
        return new UsuarioDAOArchivoTexto(rutaBase + File.separator + "usuarios_txt");
    }

    @Override
    public ProductoDAO getProductoDAO() {
        // CORREGIDO: Ahora devuelve la implementación correcta
        return new ProductoDAOArchivoTexto(rutaBase + File.separator + "productos_txt");
    }

    @Override
    public CarritoDAO getCarritoDAO() {
        // CORREGIDO: Ahora devuelve la implementación correcta
        return new CarritoDAOArchivoTexto(rutaBase + File.separator + "carritos_txt", this);
    }

    @Override
    public PreguntaSeguridadDAO getPreguntaSeguridadDAO() {
        // CORREGIDO: Ahora devuelve la implementación correcta
        return new PreguntaSeguridadDAOArchivoTexto(rutaBase + File.separator + "preguntas_txt");
    }
}
    