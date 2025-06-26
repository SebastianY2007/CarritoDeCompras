package ec.edu.ups.modelo;

public class Usuario {
    private String username;
    private String contrasena;
    private Rol rol;

    public Usuario(){
    }

    public Usuario(String username, String contrasena, Rol rol) {
        this.username = username;
        this.contrasena = contrasena;
        this.rol = rol;
    }

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

    @Override
    public String toString() {
        return "Usuario:\n" +
                "Nombre de Usuario: " + username + '\n' +
                "Contraseña: " + contrasena + '\n' +
                "Rol: " + rol;
    }
}
