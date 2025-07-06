package ec.edu.ups.modelo;

public class PreguntaSeguridad {
    private int id;
    private String clavePregunta;

    public PreguntaSeguridad() {
    }

    public PreguntaSeguridad(int id, String clavePregunta) {
        this.id = id;
        this.clavePregunta = clavePregunta;
    }

    public int getId() {
        return id;
    }

    public String getPregunta() {
        return clavePregunta;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPregunta(String clavePregunta) {
        this.clavePregunta = clavePregunta;
    }

    @Override
    public String toString() {
        return "PreguntaSeguridad{" +
                "id=" + id +
                ", pregunta='" + clavePregunta + '\'' +
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