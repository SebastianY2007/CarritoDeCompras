package ec.edu.ups.dao;

import ec.edu.ups.modelo.PreguntaSeguridad;
import java.util.List;

/**
 * Interfaz PreguntaSeguridadDAO
 *
 * Define el contrato para las operaciones de persistencia de la entidad
 * PreguntaSeguridad. Abstrae los métodos CRUD básicos.
 *
 * @author Sebastian Yupangui
 * @version 1.0
 * @since 15/07/2025
 */
public interface PreguntaSeguridadDAO {
    /**
     * Persiste una nueva pregunta de seguridad.
     * @param pregunta El objeto PreguntaSeguridad a crear.
     */
    void create(PreguntaSeguridad pregunta);

    /**
     * Lee una pregunta de seguridad por su ID.
     * @param id El ID de la pregunta a buscar.
     * @return El objeto PreguntaSeguridad si se encuentra, de lo contrario null.
     */
    PreguntaSeguridad read(int id);

    /**
     * Actualiza una pregunta de seguridad existente.
     * @param pregunta El objeto PreguntaSeguridad con los datos actualizados.
     */
    void update(PreguntaSeguridad pregunta);

    /**
     * Elimina una pregunta de seguridad por su ID.
     * @param id El ID de la pregunta a eliminar.
     */
    void delete(int id);

    /**
     * Devuelve una lista con todas las preguntas de seguridad del sistema.
     * @return Una lista de todos los objetos PreguntaSeguridad.
     */
    List<PreguntaSeguridad> findAll();
}
