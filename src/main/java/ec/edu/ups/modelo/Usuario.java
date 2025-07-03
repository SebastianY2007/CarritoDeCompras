package ec.edu.ups.modelo;

// Asegúrate de que el paquete sea el correcto
// import ec.edu.ups.modelo.Rol; // Asegúrate de tener el enum Rol

public class Usuario {
    private String username;
    private String contrasena;
    private String correoElectronico;
    private String nombre; // Cambiado de nombreCompleto a nombre
    private String telefono;
    private int diaNacimiento;
    private int mesNacimiento; // Número del mes (1-12)
    private int anioNacimiento;
    private Rol rol; // Enum Rol

    private String preguntaSeguridad1;
    private String respuestaSeguridad1;
    private String preguntaSeguridad2;
    private String respuestaSeguridad2;
    private String preguntaSeguridad3;
    private String respuestaSeguridad3;

    public Usuario(){
        // Constructor vacío necesario para algunos frameworks o si se construyen objetos paso a paso
    }

    // Constructor básico para autenticación o creación simple (usado en DAOMemoria para inicializar)
    // Se recomienda asignar un rol por defecto si no se especifica
    public Usuario(String username, String contrasena, String correoElectronico, String nombre) {
        this.username = username;
        this.contrasena = contrasena;
        this.correoElectronico = correoElectronico;
        this.nombre = nombre;
        this.rol = Rol.USUARIO; // Rol por defecto, se puede cambiar luego con setRol()
    }


    // Constructor completo para el registro (incluye todos los datos que pusiste en RegistroView)
    public Usuario(String username, String contrasena, String correoElectronico, String nombre, String apellido,
                   String telefono, int diaNacimiento, int mesNacimiento, int anioNacimiento, Rol rol,
                   String preguntaSeguridad1, String respuestaSeguridad1,
                   String preguntaSeguridad2, String respuestaSeguridad2,
                   String preguntaSeguridad3, String respuestaSeguridad3) {
        this.username = username;
        this.contrasena = contrasena;
        this.correoElectronico = correoElectronico;
        this.nombre = nombre;
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

    public Usuario(String username, String contrasena, String correoElectronico, String nombre, String telefono, Integer dia, int numeroMes, Integer anio, Rol rol, String pregunta1, String respuesta1, String pregunta2, String respuesta2, String pregunta3, String respuesta3) {
    }

    // --- GETTERS y SETTERS ---
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getNombre() { // Nuevo getter
        return nombre;
    }

    public void setNombre(String nombre) { // Nuevo setter
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getDiaNacimiento() {
        return diaNacimiento;
    }

    public void setDiaNacimiento(int diaNacimiento) {
        this.diaNacimiento = diaNacimiento;
    }

    public int getMesNacimiento() {
        return mesNacimiento;
    }

    public void setMesNacimiento(int mesNacimiento) {
        this.mesNacimiento = mesNacimiento;
    }

    public int getAnioNacimiento() {
        return anioNacimiento;
    }

    public void setAnioNacimiento(int anioNacimiento) {
        this.anioNacimiento = anioNacimiento;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getPreguntaSeguridad1() {
        return preguntaSeguridad1;
    }

    public void setPreguntaSeguridad1(String preguntaSeguridad1) {
        this.preguntaSeguridad1 = preguntaSeguridad1;
    }

    public String getRespuestaSeguridad1() {
        return respuestaSeguridad1;
    }

    public void setRespuestaSeguridad1(String respuestaSeguridad1) {
        this.respuestaSeguridad1 = respuestaSeguridad1;
    }

    public String getPreguntaSeguridad2() {
        return preguntaSeguridad2;
    }

    public void setPreguntaSeguridad2(String preguntaSeguridad2) {
        this.preguntaSeguridad2 = preguntaSeguridad2;
    }

    public String getRespuestaSeguridad2() {
        return respuestaSeguridad2;
    }

    public void setRespuestaSeguridad2(String respuestaSeguridad2) {
        this.respuestaSeguridad2 = respuestaSeguridad2;
    }

    public String getPreguntaSeguridad3() {
        return preguntaSeguridad3;
    }

    public void setPreguntaSeguridad3(String preguntaSeguridad3) {
        this.preguntaSeguridad3 = preguntaSeguridad3;
    }

    public String getRespuestaSeguridad3() {
        return respuestaSeguridad3;
    }

    public void setRespuestaSeguridad3(String respuestaSeguridad3) {
        this.respuestaSeguridad3 = respuestaSeguridad3;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "username='" + username + '\'' +
                ", contrasena='" + contrasena + '\'' +
                ", correoElectronico='" + correoElectronico + '\'' +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", diaNacimiento=" + diaNacimiento +
                ", mesNacimiento=" + mesNacimiento +
                ", anioNacimiento=" + anioNacimiento +
                ", rol=" + rol +
                ", preguntaSeguridad1='" + preguntaSeguridad1 + '\'' +
                ", respuestaSeguridad1='" + respuestaSeguridad1 + '\'' +
                ", preguntaSeguridad2='" + preguntaSeguridad2 + '\'' +
                ", respuestaSeguridad2='" + respuestaSeguridad2 + '\'' +
                ", preguntaSeguridad3='" + preguntaSeguridad3 + '\'' +
                ", respuestaSeguridad3='" + respuestaSeguridad3 + '\'' +
                '}';
    }
}