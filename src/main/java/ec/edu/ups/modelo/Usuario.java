package ec.edu.ups.modelo;

import java.io.Serializable;

/**
 * Clase Usuario
 *
 * Esta clase representa el modelo de un usuario en el sistema. Contiene
 * toda la información personal, credenciales, rol y preguntas de seguridad.
 * Es serializable para permitir su persistencia en archivos.
 *
 * @author Sebastian Yupangui
 * @version 1.0
 * @since 15/07/2025
 */
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    private String cedula;
    private String nombre;
    private String contrasena;
    private String correoElectronico;
    private String telefono;
    private int diaNacimiento;
    private int mesNacimiento;
    private int anioNacimiento;
    private Rol rol;

    private String preguntaSeguridad1;
    private String respuestaSeguridad1;
    private String preguntaSeguridad2;
    private String respuestaSeguridad2;
    private String preguntaSeguridad3;
    private String respuestaSeguridad3;

    /**
     * Constructor vacío de Usuario.
     * Permite la creación de una instancia de Usuario sin inicializar sus atributos.
     */
    public Usuario() {
    }

    /**
     * Constructor completo de Usuario.
     *
     * Se utiliza para crear una nueva instancia de Usuario con todos sus
     * atributos, incluyendo la información personal y de seguridad.
     *
     * @param cedula La cédula del usuario (actúa como identificador único).
     * @param nombre El nombre completo del usuario.
     * @param contrasena La contraseña del usuario.
     * @param correoElectronico El correo electrónico del usuario.
     * @param telefono El número de teléfono del usuario.
     * @param diaNacimiento El día de nacimiento.
     * @param mesNacimiento El mes de nacimiento.
     * @param anioNacimiento El año de nacimiento.
     * @param rol El rol del usuario en el sistema (USUARIO o ADMINISTRADOR).
     * @param preguntaSeguridad1 La primera pregunta de seguridad.
     * @param respuestaSeguridad1 La respuesta a la primera pregunta.
     * @param preguntaSeguridad2 La segunda pregunta de seguridad.
     * @param respuestaSeguridad2 La respuesta a la segunda pregunta.
     * @param preguntaSeguridad3 La tercera pregunta de seguridad.
     * @param respuestaSeguridad3 La respuesta a la tercera pregunta.
     */
    public Usuario(String cedula, String nombre, String contrasena, String correoElectronico,
                   String telefono, int diaNacimiento, int mesNacimiento, int anioNacimiento, Rol rol,
                   String preguntaSeguridad1, String respuestaSeguridad1,
                   String preguntaSeguridad2, String respuestaSeguridad2,
                   String preguntaSeguridad3, String respuestaSeguridad3) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.correoElectronico = correoElectronico;
        this.telefono = telefono;
        this.diaNacimiento = diaNacimiento;
        this.mesNacimiento = mesNacimiento;
        this.anioNacimiento = anioNacimiento;
        this.rol = rol;
        this.preguntaSeguridad1 = preguntaSeguridad1;
        this.respuestaSeguridad1 = respuestaSeguridad1;
        this.preguntaSeguridad2 = preguntaSeguridad2;
        this.respuestaSeguridad2 = respuestaSeguridad2;
        this.preguntaSeguridad3 = preguntaSeguridad3;
        this.respuestaSeguridad3 = respuestaSeguridad3;
    }

    // --- Getters y Setters ---

    /**
     * Obtiene la cédula del usuario.
     * @return la cédula del usuario.
     */
    public String getCedula() { return cedula; }

    /**
     * Establece la cédula del usuario.
     * @param cedula la nueva cédula del usuario.
     */
    public void setCedula(String cedula) { this.cedula = cedula; }

    /**
     * Obtiene el nombre completo del usuario.
     * @return el nombre del usuario.
     */
    public String getNombre() { return nombre; }

    /**
     * Establece el nombre completo del usuario.
     * @param nombre el nuevo nombre del usuario.
     */
    public void setNombre(String nombre) { this.nombre = nombre; }

    /**
     * Obtiene la contraseña del usuario.
     * @return la contraseña del usuario.
     */
    public String getContrasena() { return contrasena; }

    /**
     * Establece la contraseña del usuario.
     * @param contrasena la nueva contraseña del usuario.
     */
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    /**
     * Obtiene el correo electrónico del usuario.
     * @return el correo electrónico del usuario.
     */
    public String getCorreoElectronico() { return correoElectronico; }

    /**
     * Establece el correo electrónico del usuario.
     * @param correoElectronico el nuevo correo electrónico del usuario.
     */
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }

    /**
     * Obtiene el teléfono del usuario.
     * @return el teléfono del usuario.
     */
    public String getTelefono() { return telefono; }

    /**
     * Establece el teléfono del usuario.
     * @param telefono el nuevo teléfono del usuario.
     */
    public void setTelefono(String telefono) { this.telefono = telefono; }

    /**
     * Obtiene el día de nacimiento del usuario.
     * @return el día de nacimiento.
     */
    public int getDiaNacimiento() { return diaNacimiento; }

    /**
     * Establece el día de nacimiento del usuario.
     * @param diaNacimiento el nuevo día de nacimiento.
     */
    public void setDiaNacimiento(int diaNacimiento) { this.diaNacimiento = diaNacimiento; }

    /**
     * Obtiene el mes de nacimiento del usuario.
     * @return el mes de nacimiento.
     */
    public int getMesNacimiento() { return mesNacimiento; }

    /**
     * Establece el mes de nacimiento del usuario.
     * @param mesNacimiento el nuevo mes de nacimiento.
     */
    public void setMesNacimiento(int mesNacimiento) { this.mesNacimiento = mesNacimiento; }

    /**
     * Obtiene el año de nacimiento del usuario.
     * @return el año de nacimiento.
     */
    public int getAnioNacimiento() { return anioNacimiento; }

    /**
     * Establece el año de nacimiento del usuario.
     * @param anioNacimiento el nuevo año de nacimiento.
     */
    public void setAnioNacimiento(int anioNacimiento) { this.anioNacimiento = anioNacimiento; }

    /**
     * Obtiene el rol del usuario.
     * @return el rol del usuario (USUARIO o ADMINISTRADOR).
     */
    public Rol getRol() { return rol; }

    /**
     * Establece el rol del usuario.
     * @param rol el nuevo rol para el usuario.
     */
    public void setRol(Rol rol) { this.rol = rol; }

    /**
     * Obtiene la primera pregunta de seguridad.
     * @return la primera pregunta de seguridad.
     */
    public String getPreguntaSeguridad1() { return preguntaSeguridad1; }

    /**
     * Establece la primera pregunta de seguridad.
     * @param preguntaSeguridad1 la nueva primera pregunta.
     */
    public void setPreguntaSeguridad1(String preguntaSeguridad1) { this.preguntaSeguridad1 = preguntaSeguridad1; }

    /**
     * Obtiene la respuesta a la primera pregunta de seguridad.
     * @return la respuesta a la primera pregunta.
     */
    public String getRespuestaSeguridad1() { return respuestaSeguridad1; }

    /**
     * Establece la respuesta a la primera pregunta de seguridad.
     * @param respuestaSeguridad1 la nueva respuesta a la primera pregunta.
     */
    public void setRespuestaSeguridad1(String respuestaSeguridad1) { this.respuestaSeguridad1 = respuestaSeguridad1; }

    /**
     * Obtiene la segunda pregunta de seguridad.
     * @return la segunda pregunta de seguridad.
     */
    public String getPreguntaSeguridad2() { return preguntaSeguridad2; }

    /**
     * Establece la segunda pregunta de seguridad.
     * @param preguntaSeguridad2 la nueva segunda pregunta.
     */
    public void setPreguntaSeguridad2(String preguntaSeguridad2) { this.preguntaSeguridad2 = preguntaSeguridad2; }

    /**
     * Obtiene la respuesta a la segunda pregunta de seguridad.
     * @return la respuesta a la segunda pregunta.
     */
    public String getRespuestaSeguridad2() { return respuestaSeguridad2; }

    /**
     * Establece la respuesta a la segunda pregunta de seguridad.
     * @param respuestaSeguridad2 la nueva respuesta a la segunda pregunta.
     */
    public void setRespuestaSeguridad2(String respuestaSeguridad2) { this.respuestaSeguridad2 = respuestaSeguridad2; }

    /**
     * Obtiene la tercera pregunta de seguridad.
     * @return la tercera pregunta de seguridad.
     */
    public String getPreguntaSeguridad3() { return preguntaSeguridad3; }

    /**
     * Establece la tercera pregunta de seguridad.
     * @param preguntaSeguridad3 la nueva tercera pregunta.
     */
    public void setPreguntaSeguridad3(String preguntaSeguridad3) { this.preguntaSeguridad3 = preguntaSeguridad3; }

    /**
     * Obtiene la respuesta a la tercera pregunta de seguridad.
     * @return la respuesta a la tercera pregunta.
     */
    public String getRespuestaSeguridad3() { return respuestaSeguridad3; }

    /**
     * Establece la respuesta a la tercera pregunta de seguridad.
     * @param respuestaSeguridad3 la nueva respuesta a la tercera pregunta.
     */
    public void setRespuestaSeguridad3(String respuestaSeguridad3) { this.respuestaSeguridad3 = respuestaSeguridad3; }

    /**
     * Devuelve una representación en String del objeto Usuario.
     * Por seguridad, no se muestra la contraseña.
     * @return una cadena con los detalles del usuario.
     */
    @Override
    public String toString() {
        return "Usuario{" +
                "cedula='" + cedula + '\'' +
                ", nombre='" + nombre + '\'' +
                ", contrasena='" + "********" + '\'' +
                ", correoElectronico='" + correoElectronico + '\'' +
                ", telefono='" + telefono + '\'' +
                ", rol=" + rol +
                '}';
    }
}
