package ec.edu.ups.excepciones;

public class UsuarioYaExistenteException extends Exception {

    public UsuarioYaExistenteException(String message) {
        super(message);
    }
}
