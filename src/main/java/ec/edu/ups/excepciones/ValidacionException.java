package ec.edu.ups.excepciones;

/**
 * Clase ValidacionException
 *
 * Excepción personalizada utilizada para agrupar todos los errores relacionados
 * con la validación de datos de entrada en los formularios, como campos vacíos,
 * formatos incorrectos o contraseñas que no cumplen los requisitos.
 *
 * @author Sebastian Yupangui
 * @version 1.0
 * @since 15/07/2025
 */
public class ValidacionException extends Exception {

    /**
     * Constructor de ValidacionException.
     *
     * Crea una nueva instancia de la excepción con un mensaje de error específico.
     * @param message El mensaje que describe el error de validación.
     */
    public ValidacionException(String message) {
        super(message);
    }
}
