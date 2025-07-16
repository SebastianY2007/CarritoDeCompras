package ec.edu.ups.util;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Clase FormatoUtils
 *
 * Proporciona métodos estáticos para formatear datos como monedas, fechas y
 * números, respetando la configuración regional (Locale) para la
 * internacionalización de la aplicación.
 *
 * @author Sebastian Yupangui
 * @version 1.1
 * @since 16/07/2025
 */
public class FormatoUtils {

    /**
     * Formatea un valor numérico como una cadena de moneda según la región.
     *
     * Automáticamente utiliza el símbolo correcto (ej: $ para Ecuador/EEUU, € para Francia)
     * y los separadores de miles y decimales apropiados.
     *
     * @param valor El valor monetario a formatear.
     * @param locale El objeto Locale que define la región (ej: es_EC, en_US, fr_FR).
     * @return una cadena de texto con el valor formateado como moneda.
     */
    public static String formatearMoneda(double valor, Locale locale) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        return currencyFormatter.format(valor);
    }

    /**
     * Formatea un objeto Date como una cadena de fecha corta.
     *
     * El orden (dd/MM/yyyy vs MM/dd/yy) y el separador dependen del Locale.
     *
     * @param fecha El objeto Date a formatear.
     * @param locale El objeto Locale que define la región.
     * @return una cadena con la fecha en formato corto.
     */
    public static String formatearFechaCorta(Date fecha, Locale locale) {
        // Usamos getDateInstance para que Java elija el formato corto estándar
        DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.SHORT, locale);
        return dateFormatter.format(fecha);
    }

    /**
     * Formatea un número decimal con los separadores correctos para la región.
     *
     * Este método es útil para mostrar números que no son moneda, pero que
     * necesitan los separadores de miles y decimales correctos (ej: 1.234,56 vs 1,234.56).
     *
     * @param numero El número a formatear.
     * @param locale El objeto Locale que define la región.
     * @return una cadena con el número formateado.
     */
    public static String formatearNumero(double numero, Locale locale) {
        NumberFormat numberFormatter = NumberFormat.getNumberInstance(locale);
        // Opcional: definir un número máximo de decimales
        numberFormatter.setMaximumFractionDigits(2);
        return numberFormatter.format(numero);
    }
}