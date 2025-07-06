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

    @Override
    public void create(PreguntaSeguridad pregunta) {
        if (pregunta.getId() == 0) {
            pregunta.setId(nextId++);
        }
        preguntas.put(pregunta.getId(), pregunta);
        System.out.println("DEBUG: Pregunta de seguridad creada: " + pregunta.getPregunta());
    }

    @Override
    public PreguntaSeguridad read(int id) {
        return preguntas.get(id);
    }

    @Override
    public void update(PreguntaSeguridad pregunta) {
        if (preguntas.containsKey(pregunta.getId())) {
            preguntas.put(pregunta.getId(), pregunta);
            System.out.println("DEBUG: Pregunta de seguridad actualizada: " + pregunta.getPregunta());
        } else {
            System.out.println("DEBUG: No se encontró la pregunta con ID " + pregunta.getId() + " para actualizar.");
        }
    }

    @Override
    public void delete(int id) {
        PreguntaSeguridad removed = preguntas.remove(id);
        if (removed != null) {
            System.out.println("DEBUG: Pregunta de seguridad eliminada: " + removed.getPregunta());
        } else {
            System.out.println("DEBUG: No se encontró la pregunta con ID " + id + " para eliminar.");
        }
    }

    @Override
    public List<PreguntaSeguridad> findAll() {
        return new ArrayList<>(preguntas.values());
    }

    public PreguntaSeguridad findByQuestionText(String questionText) {
        for (PreguntaSeguridad pregunta : preguntas.values()) {
            if (pregunta.getPregunta().equals(questionText)) {
                return pregunta;
            }
        }
        return null;
    }
}