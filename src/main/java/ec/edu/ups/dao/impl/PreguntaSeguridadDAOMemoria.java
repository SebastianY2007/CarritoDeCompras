package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.PreguntaSeguridadDAO;
import ec.edu.ups.modelo.PreguntaSeguridad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PreguntaSeguridadDAOMemoria implements PreguntaSeguridadDAO {
    private Map<Integer, PreguntaSeguridad> preguntas = new HashMap<>();
    private int nextId = 1;

    // Precargar las preguntas actualizadas
    public PreguntaSeguridadDAOMemoria() {
        create(new PreguntaSeguridad(nextId++, "¿Cuál era el nombre de tu primera mascota de la infancia?"));
        create(new PreguntaSeguridad(nextId++, "¿Cuál es el segundo nombre de tu madre?"));
        create(new PreguntaSeguridad(nextId++, "¿En qué ciudad conociste a tu mejor amigo/a?"));
        create(new PreguntaSeguridad(nextId++, "¿Cómo se llamaba tu escuela primaria?"));
        create(new PreguntaSeguridad(nextId++, "¿Cuál fue el primer concierto al que asististe?"));
        create(new PreguntaSeguridad(nextId++, "¿En qué ciudad se conocieron tus padres?"));
        create(new PreguntaSeguridad(nextId++, "¿Cuál es tu película favorita de todos los tiempos?"));
        create(new PreguntaSeguridad(nextId++, "Si pudieras tener un superpoder, ¿cuál sería?"));
        create(new PreguntaSeguridad(nextId++, "¿Qué canción te sabes de memoria?"));
        create(new PreguntaSeguridad(nextId++, "¿Cuál es el mejor consejo que te han dado?"));
    }

    @Override
    public void create(PreguntaSeguridad pregunta) {
        if (pregunta.getId() == 0) { // Si el ID no está establecido, asigna uno nuevo
            pregunta.setId(nextId++);
        }
        preguntas.put(pregunta.getId(), pregunta);
        System.out.println("DEBUG: Pregunta de seguridad creada: " + pregunta.getPregunta()); // Mensaje para depuración
    }

    @Override
    public PreguntaSeguridad read(int id) {
        return preguntas.get(id);
    }

    @Override
    public void update(PreguntaSeguridad pregunta) {
        if (preguntas.containsKey(pregunta.getId())) {
            preguntas.put(pregunta.getId(), pregunta);
            System.out.println("DEBUG: Pregunta de seguridad actualizada: " + pregunta.getPregunta()); // Mensaje para depuración
        } else {
            System.out.println("DEBUG: No se encontró la pregunta con ID " + pregunta.getId() + " para actualizar.");
        }
    }

    @Override
    public void delete(int id) {
        PreguntaSeguridad removed = preguntas.remove(id);
        if (removed != null) {
            System.out.println("DEBUG: Pregunta de seguridad eliminada: " + removed.getPregunta()); // Mensaje para depuración
        } else {
            System.out.println("DEBUG: No se encontró la pregunta con ID " + id + " para eliminar.");
        }
    }

    @Override
    public List<PreguntaSeguridad> findAll() {
        return new ArrayList<>(preguntas.values());
    }

    // Método adicional para buscar por texto de pregunta, útil para la UI
    public PreguntaSeguridad findByQuestionText(String questionText) {
        for (PreguntaSeguridad pregunta : preguntas.values()) {
            if (pregunta.getPregunta().equals(questionText)) {
                return pregunta;
            }
        }
        return null; // No encontrada
    }
}