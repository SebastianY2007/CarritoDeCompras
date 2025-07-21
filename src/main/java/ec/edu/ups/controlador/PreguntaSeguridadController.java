package ec.edu.ups.controlador;

import ec.edu.ups.dao.PreguntaSeguridadDAO;
import ec.edu.ups.modelo.PreguntaSeguridad;

import java.util.List;

/**
 * Clase PreguntaSeguridadController
 *
 * Esta clase actúa como el controlador para la lógica de negocio relacionada
 * con las preguntas de seguridad. Se encarga de comunicar la capa de datos (DAO)
 * con cualquier vista que necesite gestionar las preguntas.
 *
 * @author Sebastian Yupangui
 * @version 1.0
 * @since 15-07-2025
 */
public class PreguntaSeguridadController {
    private PreguntaSeguridadDAO preguntaSeguridadDAO;

    /**
     * Constructor del PreguntaSeguridadController.
     *
     * Inicializa el controlador inyectando la dependencia del DAO de preguntas
     * de seguridad, permitiendo la abstracción de la capa de persistencia.
     *
     * @param preguntaSeguridadDAO El objeto de acceso a datos para las preguntas.
     */
    public PreguntaSeguridadController(PreguntaSeguridadDAO preguntaSeguridadDAO) {
        this.preguntaSeguridadDAO = preguntaSeguridadDAO;
    }

    /**
     * Crea una nueva pregunta de seguridad.
     *
     * Recibe el texto de una pregunta, crea un nuevo objeto PreguntaSeguridad
     * y lo persiste utilizando el DAO.
     *
     * @param preguntaTexto El texto de la nueva pregunta a crear.
     */
    public void crearPregunta(String preguntaTexto) {
        PreguntaSeguridad pregunta = new PreguntaSeguridad();
        pregunta.setPregunta(preguntaTexto);
        preguntaSeguridadDAO.create(pregunta);
    }

    /**
     * Busca una pregunta de seguridad por su ID.
     *
     * @param id El identificador único de la pregunta a buscar.
     * @return El objeto PreguntaSeguridad si se encuentra, de lo contrario null.
     */
    public PreguntaSeguridad buscarPregunta(int id) {
        return preguntaSeguridadDAO.read(id);
    }

    /**
     * Busca una pregunta de seguridad por su texto exacto.
     *
     * Este método recorre la lista de todas las preguntas para encontrar una
     * coincidencia exacta con el texto proporcionado. Incluye una optimización
     * para la implementación en memoria.
     *
     * @param textoPregunta El texto de la pregunta a buscar.
     * @return El objeto PreguntaSeguridad si se encuentra, de lo contrario null.
     */
    public PreguntaSeguridad buscarPreguntaPorTexto(String textoPregunta) {
        // Optimización para la implementación en memoria
        if (preguntaSeguridadDAO instanceof ec.edu.ups.dao.impl.PreguntaSeguridadDAOMemoria) {
            return ((ec.edu.ups.dao.impl.PreguntaSeguridadDAOMemoria) preguntaSeguridadDAO).findByQuestionText(textoPregunta);
        }
        // Búsqueda genérica para otras implementaciones
        for (PreguntaSeguridad pregunta : preguntaSeguridadDAO.findAll()) {
            if (pregunta.getPregunta().equals(textoPregunta)) {
                return pregunta;
            }
        }
        return null;
    }

    /**
     * Actualiza el texto de una pregunta de seguridad existente.
     *
     * Busca la pregunta por su ID y, si la encuentra, actualiza su texto
     * con el nuevo valor proporcionado.
     *
     * @param id El ID de la pregunta a actualizar.
     * @param nuevaPreguntaTexto El nuevo texto para la pregunta.
     */
    public void actualizarPregunta(int id, String nuevaPreguntaTexto) {
        PreguntaSeguridad pregunta = preguntaSeguridadDAO.read(id);
        if (pregunta != null) {
            pregunta.setPregunta(nuevaPreguntaTexto);
            preguntaSeguridadDAO.update(pregunta);
        } else {
            System.out.println("DEBUG: No se encontró la pregunta con ID " + id + " para actualizar.");
        }
    }

    /**
     * Elimina una pregunta de seguridad por su ID.
     *
     * @param id El ID de la pregunta a eliminar.
     */
    public void eliminarPregunta(int id) {
        preguntaSeguridadDAO.delete(id);
    }

    /**
     * Obtiene una lista de todas las preguntas de seguridad.
     *
     * @return Una lista que contiene todos los objetos PreguntaSeguridad del sistema.
     */
    public List<PreguntaSeguridad> listarPreguntas() {
        return preguntaSeguridadDAO.findAll();
    }
}
