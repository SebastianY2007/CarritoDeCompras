package ec.edu.ups.modelo;

public class PreguntaSeguridad {
    private int id;
    private String pregunta;

    public PreguntaSeguridad() {
    }

    public PreguntaSeguridad(int id, String pregunta) {
        this.id = id;
        this.pregunta = pregunta;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getPregunta() {
        return pregunta;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    // Opcional: Para facilitar la depuración
    @Override
    public String toString() {
        return "PreguntaSeguridad{" +
                "id=" + id +
                ", pregunta='" + pregunta + '\'' +
                '}';
    }

    // Opcional: Para comparar objetos por ID si los pones en colecciones que lo necesiten
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PreguntaSeguridad that = (PreguntaSeguridad) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}