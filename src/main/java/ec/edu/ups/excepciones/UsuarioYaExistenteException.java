package ec.edu.ups.excepciones;

/**
 * Clase UsuarioYaExistenteException
 *
 * Esta excepción personalizada se lanza cuando se intenta crear un usuario
 * que ya existe en el sistema, típicamente identificado por una cédula duplicada.
 * Hereda de la clase base Exception.
 *
 * @author Sebastian Yupangui
 * @version 1.0
 * @since 15/07/2025
 */
public class UsuarioYaExistenteException extends Exception {

    /**
     * Constructor de UsuarioYaExistenteException.
     *
     * Crea una nueva instancia de la excepción con un mensaje de error específico.
     * @param message El mensaje que detalla la razón del error.
     */
    public UsuarioYaExistenteException(String message) {
        super(message);
    }
}
