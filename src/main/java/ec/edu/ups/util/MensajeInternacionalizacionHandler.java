package ec.edu.ups.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class MensajeInternacionalizacionHandler {
    private ResourceBundle mensajes;
    private Locale localeActual;

    public MensajeInternacionalizacionHandler() {
        setLenguaje("es", "EC");
    }

    public void setLenguaje(String language, String country) {
        localeActual = new Locale(language, country);
        mensajes = ResourceBundle.getBundle("mensajes", localeActual);
    }

    public ResourceBundle getMensajes() {
        return mensajes;
    }

    public String get(String key) {
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