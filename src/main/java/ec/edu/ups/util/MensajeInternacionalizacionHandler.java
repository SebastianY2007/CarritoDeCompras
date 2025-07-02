package ec.edu.ups.util;

import java.util.Locale;
import java.util.ResourceBundle;
// No se necesita importar MissingResourceException ya que no se usa try-catch

public class MensajeInternacionalizacionHandler {
    private ResourceBundle mensajes;
    private Locale localeActual;

    public MensajeInternacionalizacionHandler() {
        setLenguaje("es", "EC"); // Establece el idioma por defecto al inicializar
    }

    public void setLenguaje(String language, String country) {
        localeActual = new Locale(language, country);
        // Sin try-catch. Si el bundle no existe, MissingResourceException se propagará.
        mensajes = ResourceBundle.getBundle("mensajes", localeActual);
    }

    public ResourceBundle getMensajes() {
        return mensajes;
    }

    public String get(String key) {
        // Sin try-catch. Si la clave no existe, MissingResourceException se propagará.
        return mensajes.getString(key);
    }

    public String getLenguajeActual() {
        if (localeActual != null) {
            return localeActual.getLanguage();
        }
        return "es";
    }

    public String getPaisActual() {
        if (localeActual != null) {
            return localeActual.getCountry();
        }
        return "EC";
    }

    public Locale getLocaleActual() {
        return localeActual;
    }
}