package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.PreguntaSeguridadDAO;
import ec.edu.ups.modelo.PreguntaSeguridad;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PreguntaSeguridadDAOArchivoTexto implements PreguntaSeguridadDAO {

    private String rutaBase;
    private int nextId = 1;

    public PreguntaSeguridadDAOArchivoTexto(String rutaBase) {
        this.rutaBase = rutaBase;
        new File(this.rutaBase).mkdirs();

        // NUEVA LÓGICA: Comprobar si la carpeta está vacía para crear las preguntas por defecto.
        File directorio = new File(rutaBase);
        if (directorio.list().length == 0) {
            crearPreguntasPorDefecto();
        }

        this.nextId = obtenerSiguienteId();
    }

    /**
     * NUEVO MÉTODO: Crea el conjunto inicial de preguntas si no existen.
     */
    private void crearPreguntasPorDefecto() {
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
        try (PrintWriter writer = new PrintWriter(new FileWriter(rutaBase + File.separator + pregunta.getId() + ".txt"))) {
            writer.println(pregunta.getId());
            writer.println(pregunta.getPregunta());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PreguntaSeguridad read(int id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(rutaBase + File.separator + id + ".txt"))) {
            int preguntaId = Integer.parseInt(reader.readLine());
            String clavePregunta = reader.readLine();
            return new PreguntaSeguridad(preguntaId, clavePregunta);
        } catch (IOException | NumberFormatException e) {
            return null;
        }
    }

    @Override
    public void update(PreguntaSeguridad pregunta) {
        create(pregunta);
    }

    @Override
    public void delete(int id) {
        new File(rutaBase + File.separator + id + ".txt").delete();
    }

    @Override
    public List<PreguntaSeguridad> findAll() {
        List<PreguntaSeguridad> preguntas = new ArrayList<>();
        File[] archivos = new File(rutaBase).listFiles((dir, name) -> name.endsWith(".txt"));
        if (archivos != null) {
            for (File archivo : archivos) {
                try {
                    int id = Integer.parseInt(archivo.getName().replace(".txt", ""));
                    PreguntaSeguridad pregunta = read(id);
                    if (pregunta != null) {
                        preguntas.add(pregunta);
                    }
                } catch (NumberFormatException e) {
                    // Ignorar
                }
            }
        }
        return preguntas;
    }

    private int obtenerSiguienteId() {
        File dir = new File(rutaBase);
        File[] files = dir.listFiles();
        int maxId = 0;
        if (files != null) {
            for (File file : files) {
                try {
                    int id = Integer.parseInt(file.getName().replace(".txt", ""));
                    if (id > maxId) maxId = id;
                } catch (NumberFormatException e) { /* Ignorar */ }
            }
        }
        return maxId + 1;
    }
}
