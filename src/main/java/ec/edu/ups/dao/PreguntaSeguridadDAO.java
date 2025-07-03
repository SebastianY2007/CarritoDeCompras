package ec.edu.ups.dao;

import ec.edu.ups.modelo.PreguntaSeguridad;
import java.util.List;

public interface PreguntaSeguridadDAO {
    void create(PreguntaSeguridad pregunta);
    PreguntaSeguridad read(int id);
    void update(PreguntaSeguridad pregunta);
    void delete(int id);
    List<PreguntaSeguridad> findAll();
}