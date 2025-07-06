package ec.edu.ups.util;

import java.text.NumberFormat;
import java.util.Locale;

public class FormatoUtils {
    public static String formatearMoneda(double valor, Locale locale) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        return currencyFormatter.format(valor);
    }
}