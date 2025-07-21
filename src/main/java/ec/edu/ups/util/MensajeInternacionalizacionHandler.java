package ec.edu.ups.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Clase MensajeInternacionalizacionHandler
 *
 * Esta clase de utilidad gestiona la internacionalización (i18n) de la aplicación.
 * Se encarga de cargar y proporcionar el archivo de propiedades de idioma
 * (.properties) correcto según la configuración de lenguaje y país seleccionada.
 *
 * @author Sebastian Yupangui
 * @version 1.0
 * @since 15/07/2025
 */
public class MensajeInternacionalizacionHandler {
    private ResourceBundle mensajes;
    private Locale localeActual;

    /**
     * Constructor de MensajeInternacionalizacionHandler.
     *
     * Inicializa el manejador estableciendo el idioma por defecto a
     * Español (Ecuador) - "es", "EC".
     */
    public MensajeInternacionalizacionHandler() {
        setLenguaje("es", "EC");
    }

    /**
     * Establece el idioma y país para la aplicación.
     *
     * Carga el ResourceBundle correspondiente al nuevo locale.
     * @param language El código del idioma (ej: "en", "es", "fr").
     * @param country El código del país (ej: "US", "EC", "FR").
     */
    public void setLenguaje(String language, String country) {
        localeActual = new Locale(language, country);
        mensajes = ResourceBundle.getBundle("mensajes", localeActual);
    }

    /**
     * Obtiene el ResourceBundle actualmente cargado.
     * @return el ResourceBundle con los mensajes del idioma actual.
     */
    public ResourceBundle getMensajes() {
        return mensajes;
    }

    /**
     * Obtiene un mensaje específico a partir de su clave.
     * @param key La clave del mensaje en el archivo de propiedades.
     * @return el texto del mensaje traducido.
     */
    public String get(String key) {
        return mensajes.getString(key);
    }

    /**
     * Obtiene el código del lenguaje actual.
     * @return el código del lenguaje (ej: "es").
     */
    public String getLenguajeActual() {
        if (localeActual != null) {
            return localeActual.getLanguage();
        }
        return "es"; // Valor por defecto
    }

    /**
     * Obtiene el código del país actual.
     * @return el código del país (ej: "EC").
     */
    public String getPaisActual() {
        if (localeActual != null) {
            return localeActual.getCountry();
        }
        return "EC"; // Valor por defecto
    }

    /**
     * Obtiene el objeto Locale completo actual.
     * @return el objeto Locale que representa el idioma y país actuales.
     */
    public Locale getLocaleActual() {
        return localeActual;
    }
}
