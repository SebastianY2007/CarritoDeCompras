package ec.edu.ups.modelo;

import java.io.Serializable;

/**
 * Clase PreguntaSeguridad
 *
 * Este modelo representa una pregunta de seguridad en el sistema. Almacena
 * un ID único y la clave de la pregunta, que se utiliza para la
 * internacionalización. Es serializable para su persistencia.
 *
 * @author Sebastian Yupangui
 * @version 1.0
 * @since 15/07/2025
 */
public class PreguntaSeguridad implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String clavePregunta;

    /**
     * Constructor vacío.
     *
     * Permite la creación de una instancia sin inicializar sus atributos.
     */
    public PreguntaSeguridad() {
    }

    /**
     * Constructor con parámetros.
     *
     * @param id El identificador único de la pregunta.
     * @param clavePregunta La clave del texto de la pregunta para internacionalización.
     */
    public PreguntaSeguridad(int id, String clavePregunta) {
        this.id = id;
        this.clavePregunta = clavePregunta;
    }

    /**
     * Obtiene el ID de la pregunta.
     * @return el ID de la pregunta.
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene la clave del texto de la pregunta.
     * @return la clave de la pregunta.
     */
    public String getPregunta() {
        return clavePregunta;
    }

    /**
     * Establece el ID de la pregunta.
     * @param id El nuevo ID para la pregunta.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Establece la clave del texto de la pregunta.
     * @param clavePregunta La nueva clave para la pregunta.
     */
    public void setPregunta(String clavePregunta) {
        this.clavePregunta = clavePregunta;
    }

    /**
     * Representación en String del objeto.
     * @return una cadena con los detalles de la pregunta.
     */
    @Override
    public String toString() {
        return "PreguntaSeguridad{" +
                "id=" + id +
                ", pregunta='" + clavePregunta + '\'' +
                '}';
    }

    /**
     * Compara dos objetos PreguntaSeguridad por su ID.
     * @param o El objeto a comparar.
     * @return true si los IDs son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PreguntaSeguridad that = (PreguntaSeguridad) o;
        return id == that.id;
    }

    /**
     * Genera el código hash basado en el ID.
     * @return el código hash del objeto.
     */
    @Override
    public int hashCode() {
        return id;
    }
}
