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

    public int getId() {
        return id;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    @Override
    public String toString() {
        return "PreguntaSeguridad{" +
                "id=" + id +
                ", pregunta='" + pregunta + '\'' +
                '}';
    }

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