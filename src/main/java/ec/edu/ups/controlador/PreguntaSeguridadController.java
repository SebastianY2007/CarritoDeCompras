package ec.edu.ups.controlador;

import ec.edu.ups.dao.PreguntaSeguridadDAO;
import ec.edu.ups.modelo.PreguntaSeguridad;

import java.util.List;

public class PreguntaSeguridadController {
    private PreguntaSeguridadDAO preguntaSeguridadDAO;

    public PreguntaSeguridadController(PreguntaSeguridadDAO preguntaSeguridadDAO) {
        this.preguntaSeguridadDAO = preguntaSeguridadDAO;
    }

    public void crearPregunta(String preguntaTexto) {
        PreguntaSeguridad pregunta = new PreguntaSeguridad();
        pregunta.setPregunta(preguntaTexto);
        preguntaSeguridadDAO.create(pregunta);
    }

    public PreguntaSeguridad buscarPregunta(int id) {
        return preguntaSeguridadDAO.read(id);
    }

    // Agregamos este método para que la UI pueda buscar la pregunta por su texto y obtener el objeto completo
    public PreguntaSeguridad buscarPreguntaPorTexto(String textoPregunta) {
        // La implementación del DAO deberá tener este método si quieres que sea eficiente.
        // Por ahora, asumiremos que PreguntaSeguridadDAOImpl lo tiene o lo haremos aquí de forma simple.
        // Si el DAO es solo una interfaz, necesitas castearlo o añadir el método a la interfaz
        // o pasar un DAO de tipo PreguntaSeguridadDAOImpl
        if (preguntaSeguridadDAO instanceof ec.edu.ups.dao.impl.PreguntaSeguridadDAOMemoria) {
            return ((ec.edu.ups.dao.impl.PreguntaSeguridadDAOMemoria) preguntaSeguridadDAO).findByQuestionText(textoPregunta);
        }
        // Si el DAO no es PreguntaSeguridadDAOImpl, hacemos una búsqueda manual
        for (PreguntaSeguridad pregunta : preguntaSeguridadDAO.findAll()) {
            if (pregunta.getPregunta().equals(textoPregunta)) {
                return pregunta;
            }
        }
        return null;
    }

    public void actualizarPregunta(int id, String nuevaPreguntaTexto) {
        PreguntaSeguridad pregunta = preguntaSeguridadDAO.read(id);
        if (pregunta != null) {
            pregunta.setPregunta(nuevaPreguntaTexto);
            preguntaSeguridadDAO.update(pregunta);
        } else {
            System.out.println("DEBUG: No se encontró la pregunta con ID " + id + " para actualizar.");
        }
    }

    public void eliminarPregunta(int id) {
        preguntaSeguridadDAO.delete(id);
    }

    public List<PreguntaSeguridad> listarPreguntas() {
        return preguntaSeguridadDAO.findAll();
    }
}