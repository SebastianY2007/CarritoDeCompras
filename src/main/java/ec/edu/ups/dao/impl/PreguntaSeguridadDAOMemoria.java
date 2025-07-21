package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.PreguntaSeguridadDAO;
import ec.edu.ups.modelo.PreguntaSeguridad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase PreguntaSeguridadDAOMemoria
 *
 * Implementación de la interfaz PreguntaSeguridadDAO que utiliza un HashMap
 * para almacenar las preguntas de seguridad en memoria. Es ideal para pruebas
 * y ejecución sin necesidad de una base de datos o archivos físicos.
 *
 * @author Sebastian Yupangui
 * @version 1.0
 * @since 15-07-2025
 */
public class PreguntaSeguridadDAOMemoria implements PreguntaSeguridadDAO {
    private Map<Integer, PreguntaSeguridad> preguntas = new HashMap<>();
    private int nextId = 1;

    /**
     * Constructor de PreguntaSeguridadDAOMemoria.
     *
     * Inicializa el DAO y precarga una lista de preguntas de seguridad
     * estándar que estarán disponibles en el sistema desde el inicio.
     */
    public PreguntaSeguridadDAOMemoria() {
        create(new PreguntaSeguridad(0, "pregunta.mascota"));
        create(new PreguntaSeguridad(0, "pregunta.madre"));
        create(new PreguntaSeguridad(0, "pregunta.amigo"));
        create(new PreguntaSeguridad(0, "pregunta.escuela"));
        create(new PreguntaSeguridad(0, "pregunta.concierto"));
        create(new PreguntaSeguridad(0, "pregunta.padres"));
        create(new PreguntaSeguridad(0, "pregunta.pelicula"));
        create(new PreguntaSeguridad(0, "pregunta.superpoder"));
        create(new PreguntaSeguridad(0, "pregunta.cancion"));
        create(new PreguntaSeguridad(0, "pregunta.consejo"));
    }

    /**
     * Crea una nueva pregunta de seguridad.
     *
     * Asigna un ID autoincremental si la pregunta es nueva (ID=0) y la
     * almacena en el mapa de memoria.
     * @param pregunta El objeto PreguntaSeguridad a crear.
     */
    @Override
    public void create(PreguntaSeguridad pregunta) {
        if (pregunta.getId() == 0) {
            pregunta.setId(nextId++);
        }
        preguntas.put(pregunta.getId(), pregunta);
    }

    /**
     * Lee una pregunta de seguridad por su ID.
     *
     * @param id El ID de la pregunta a buscar.
     * @return El objeto PreguntaSeguridad si se encuentra, de lo contrario null.
     */
    @Override
    public PreguntaSeguridad read(int id) {
        return preguntas.get(id);
    }

    /**
     * Actualiza una pregunta de seguridad existente.
     *
     * @param pregunta El objeto PreguntaSeguridad con los datos actualizados.
     */
    @Override
    public void update(PreguntaSeguridad pregunta) {
        if (preguntas.containsKey(pregunta.getId())) {
            preguntas.put(pregunta.getId(), pregunta);
        }
    }

    /**
     * Elimina una pregunta de seguridad por su ID.
     *
     * @param id El ID de la pregunta a eliminar.
     */
    @Override
    public void delete(int id) {
        preguntas.remove(id);
    }

    /**
     * Devuelve una lista con todas las preguntas de seguridad.
     *
     * @return Una lista de todos los objetos PreguntaSeguridad.
     */
    @Override
    public List<PreguntaSeguridad> findAll() {
        return new ArrayList<>(preguntas.values());
    }

    /**
     * Busca una pregunta por su texto exacto.
     *
     * Este es un método específico de esta implementación para facilitar
     * la búsqueda por el contenido de la pregunta.
     *
     * @param questionText El texto de la pregunta a buscar.
     * @return El objeto PreguntaSeguridad si se encuentra, de lo contrario null.
     */
    public PreguntaSeguridad findByQuestionText(String questionText) {
        for (PreguntaSeguridad pregunta : preguntas.values()) {
            if (pregunta.getPregunta().equals(questionText)) {
                return pregunta;
            }
        }
        return null;
    }
}
