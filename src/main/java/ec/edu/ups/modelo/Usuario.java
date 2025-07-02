package ec.edu.ups.modelo;

public class Usuario {
    private String username;
    private String contrasena;
    private Rol rol;

    // Nuevos atributos para el registro
    private String nombreCompleto;
    private String correoElectronico;
    private String telefono; // NUEVO: Atributo para el teléfono
    private int diaNacimiento;
    private int mesNacimiento; // Número del mes (1-12)
    private int anioNacimiento;

    private String preguntaSeguridad1;
    private String respuestaSeguridad1;
    private String preguntaSeguridad2;
    private String respuestaSeguridad2;
    private String preguntaSeguridad3;
    private String respuestaSeguridad3;

    public Usuario(){
    }

    // Constructor existente (sin preguntas de seguridad y datos extendidos)
    public Usuario(String username, String contrasena, Rol rol) {
        this.username = username;
        this.contrasena = contrasena;
        this.rol = rol;
    }

    // Constructor existente (con preguntas de seguridad pero sin datos extendidos como nombre completo, etc.)
    public Usuario(String username, String contrasena, Rol rol,
                   String preguntaSeguridad1, String respuestaSeguridad1,
                   String preguntaSeguridad2, String respuestaSeguridad2,
                   String preguntaSeguridad3, String respuestaSeguridad3) {
        this.username = username;
        this.contrasena = contrasena;
        this.rol = rol;
        this.preguntaSeguridad1 = preguntaSeguridad1;
        this.respuestaSeguridad1 = respuestaSeguridad1;
        this.preguntaSeguridad2 = preguntaSeguridad2;
        this.respuestaSeguridad2 = respuestaSeguridad2;
        this.preguntaSeguridad3 = preguntaSeguridad3;
        this.respuestaSeguridad3 = respuestaSeguridad3;
    }

    // --- NUEVO CONSTRUCTOR COMPLETO para incluir todos los datos del RegistroView ---
    public Usuario(String username, String contrasena, Rol rol,
                   String nombreCompleto, String correoElectronico, String telefono, // AÑADIDO 'telefono'
                   int diaNacimiento, int mesNacimiento, int anioNacimiento,
                   String preguntaSeguridad1, String respuestaSeguridad1,
                   String preguntaSeguridad2, String respuestaSeguridad2,
                   String preguntaSeguridad3, String respuestaSeguridad3) {
        this.username = username;
        this.contrasena = contrasena;
        this.rol = rol;
        this.nombreCompleto = nombreCompleto;
        this.correoElectronico = correoElectronico;
        this.telefono = telefono; // Asignar el nuevo atributo
        this.diaNacimiento = diaNacimiento;
        this.mesNacimiento = mesNacimiento;
        this.anioNacimiento = anioNacimiento;
        this.preguntaSeguridad1 = preguntaSeguridad1;
        this.respuestaSeguridad1 = respuestaSeguridad1;
        this.preguntaSeguridad2 = preguntaSeguridad2;
        this.respuestaSeguridad2 = respuestaSeguridad2;
        this.preguntaSeguridad3 = preguntaSeguridad3;
        this.respuestaSeguridad3 = respuestaSeguridad3;
    }

    // --- GETTERS y SETTERS existentes ---
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

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    // GETTERS y SETTERS para las preguntas y respuestas de seguridad
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

    // --- NUEVOS GETTERS y SETTERS para Nombre Completo, Correo Electrónico y Fecha de Nacimiento ---
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    // NUEVO: Getter y Setter para teléfono
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

    @Override
    public String toString() {
        return "Usuario:\n" +
                "Nombre de Usuario: " + username + '\n' +
                "Contraseña: " + contrasena + '\n' +
                "Rol: " + rol + '\n' +
                "Nombre Completo: " + nombreCompleto + '\n' +
                "Correo Electrónico: " + correoElectronico + '\n' +
                "Teléfono: " + telefono + '\n' + // Incluido en toString
                "Fecha de Nacimiento: " + diaNacimiento + "/" + mesNacimiento + "/" + anioNacimiento + '\n' +
                "Pregunta 1: " + preguntaSeguridad1 + '\n' +
                "Respuesta 1: " + respuestaSeguridad1 + '\n' +
                "Pregunta 2: " + preguntaSeguridad2 + '\n' +
                "Respuesta 2: " + respuestaSeguridad2 + '\n' +
                "Pregunta 3: " + preguntaSeguridad3 + '\n' +
                "Respuesta 3: " + respuestaSeguridad3;
    }
}